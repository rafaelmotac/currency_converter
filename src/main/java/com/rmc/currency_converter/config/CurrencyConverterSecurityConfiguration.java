package com.rmc.currency_converter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class CurrencyConverterSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainApp(HttpSecurity http) throws Exception {

        http.securityMatcher("/api/**");
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                .contentSecurityPolicy(contentSecurityPolicyConfig -> contentSecurityPolicyConfig
                        .policyDirectives("default-src 'self'; "
                                + "connect-src 'self'; "
                                + "frame-src 'self' data:; "
                                + "script-src 'self' 'unsafe-inline' 'unsafe-eval'; "
                                + "style-src 'self'; "
                                + "form-action 'self' data:; "
                                + "frame-ancestors 'self'; "
                                + "img-src 'self' data:; "
                                + "font-src 'self' data:")
                )
                .referrerPolicy(referrerPolicyConfig -> referrerPolicyConfig
                        .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                )
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                .permissionsPolicy(permissionsPolicyConfig -> permissionsPolicyConfig
                        .policy("geolocation=(none); "
                                + "midi=(none); "
                                + "sync-xhr=(none); "
                                + "microphone=(none); "
                                + "camera=(none); "
                                + "magnetometer=(none); "
                                + "gyroscope=(none); "
                                + "fullscreen=(self); "
                                + "payment=(none)"
                        )
                )
        );
        http.sessionManagement(sessionManagementConfig -> sessionManagementConfig
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/api/v1/currency-converter/")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/currency-converter/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).authenticated()
                .requestMatchers(new AntPathRequestMatcher("/api/**")).denyAll()
        );

        return http.build();
    }

}
