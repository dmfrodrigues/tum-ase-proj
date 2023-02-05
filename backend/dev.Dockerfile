FROM openjdk:17-jdk-alpine

RUN apk add \
    curl \
    inotify-tools

RUN mkdir -p /gradle_zip
RUN curl -L https://services.gradle.org/distributions/gradle-7.5.1-bin.zip --output /gradle_zip/gradle-7.5.1-bin.zip

RUN mkdir -p /app
WORKDIR /app

COPY run.dev.sh watch_changes.sh /
RUN chmod 755 /*.sh

COPY gradle gradle
COPY gradlew gradlew.bat run.dev.sh settings.gradle watch_changes.sh ./

RUN mv gradle/wrapper/gradle-wrapper.docker.properties \
       gradle/wrapper/gradle-wrapper.properties
RUN chmod +x gradlew

# COPY api-gateway service-registry delivery auth library ./
