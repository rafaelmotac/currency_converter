# currency_converter

Currency Converter

## About

Currency Converter is a Gradle Spring Boot application that converts currencies using the [Exchange Rates API](https://exchangeratesapi.io/).

It has two endpoints:

1. `/currency-converter/convert` which converts a given amount from a source currency to a target currency.
2. `/currency-converter/current-currency` which receives a currency code and a currency symbol list and returns the current currency value\
   for the given currency code and the list of symbols.


It uses the following libraries/framework to achieve the goal:

### Technologies

- [Java 17](https://openjdk.java.net/projects/jdk/17/) as programming language.
- [Spring Boot](https://spring.io/projects/spring-boot) to create the application.
- [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign) to consume the [Exchange Rates API](https://exchangeratesapi.io/).
- [Log4j2](https://logging.apache.org/log4j/2.x/) to log messages.
- [Lombok's](https://projectlombok.org/) library to reduce boilerplate code.
- [Jupiter](https://junit.org/junit5/) to write unit tests.
- [Mockito](https://site.mockito.org/) to mock objects in unit tests.
- [WireMock](http://wiremock.org/) to mock the [Exchange Rates API](https://exchangeratesapi.io/) in integration tests.
- [Gradle](https://gradle.org/) to build the application.
- [Spring OpenApi](https://springdoc.org/) to generate the OpenApi documentation.

## Local Environment Setup

### Prerequisites

- [Gradle](https://gradle.org/install/) (can be installed through [SDKMAN!](https://sdkman.io/install))
- Java 17 (can be also installed through [SDKMAN!](https://sdkman.io/install))

### Steps

1. Clone the repository and open it in your IDE.
2. Then go to the root folder of this project and run `./gradlew clean build` or `gradle clean build` to build the project.

## Running the `currency_converter`

1. Go to the `currency_converter` folder on your terminal and run the spring boot project with `./gradlew bootRun` or `gradle bootRun`.\
   This will start the application on port `8080` and it is ready to receive requests.
2. Once the application is running, you can access the [Swagger UI](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config) to see the documentation and test the endpoints.