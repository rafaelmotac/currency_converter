FROM amazoncorretto:17-alpine
ARG JAR_FILE=build/libs/currency_converter-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} currency_converter-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/currency_converter-0.0.1-SNAPSHOT.jar"]