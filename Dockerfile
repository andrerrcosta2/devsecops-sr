FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /usr/src/app
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-alpine
ENV APP_HOME /usr/app
ENV TZ=America/Sao_Paulo
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
WORKDIR ${APP_HOME}
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && \
    apk update && \
    apk add --no-cache tzdata && \
    chgrp -R 0 ${APP_HOME} && \
    chmod -R g=u ${APP_HOME} /etc/passwd \
    && pwd  # Print current working directory
EXPOSE 8080
COPY --from=build /usr/src/app/target/devsecops-service.jar devsecops-service.jar

# Copy the private key file
COPY /src/main/resources/certs/private-key.pem /usr/app/src/main/resources/certs/private-key.pem

RUN chmod g+r /usr/app/src/main/resources/certs

ENTRYPOINT ["java", "-jar", "devsecops-service.jar"]
USER 1001
