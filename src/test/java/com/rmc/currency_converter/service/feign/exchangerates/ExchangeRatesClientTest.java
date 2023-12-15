package com.rmc.currency_converter.service.feign.exchangerates;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.rmc.currency_converter.service.feign.exchangerates.response.CurrencyConversionResponse;
import com.rmc.currency_converter.service.feign.exchangerates.response.ExchangeRatesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
class ExchangeRatesClientTest {

    @Autowired
    private ExchangeRatesClient exchangeRatesClient;

    @BeforeEach
    void setUp() {
        WireMock.reset();
    }

    @Test
    void shouldGetExchangeRatesSuccessfully() {
        WireMock.stubFor(WireMock.get("/latest?base=USD&symbols=EUR&access_key=MY_KEY")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"success\":true,\"timestamp\":1636497605,\"base\":\"USD\",\"date\":\"2021-11-10\",\"rates\":{\"EUR\":0.87505}}")));

        ExchangeRatesResponse response = exchangeRatesClient.getExchangeRates("USD", "EUR");

        assertTrue(response.isSuccess());
        assertEquals("USD", response.getBase());
        assertEquals(0.87505, response.getRates().get("EUR"));
    }

    @Test
    void shouldGetAmountConvertedSuccessfully() {
        WireMock.stubFor(WireMock.get("/convert?from=USD&to=EUR&amount=100.0&access_key=MY_KEY")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"success\":true,\"query\":{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":100},\"info\":{\"timestamp\":1636497605,\"rate\":0.87505},\"date\":\"2021-11-10\",\"result\":87.505}")));

        CurrencyConversionResponse response = exchangeRatesClient.getAmountConverted("USD", "EUR", 100.0);

        assertTrue(response.isSuccess());
        assertEquals(87.505, response.getResult());
    }
}