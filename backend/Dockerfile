FROM openjdk:17-jdk-alpine AS base

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

FROM base AS api-gateway
COPY api-gateway api-gateway
CMD ["/run.dev.sh", "api-gateway"]

FROM base AS service-registry
COPY service-registry service-registry
CMD ["/run.dev.sh", "service-registry"]

FROM base AS auth
COPY library library
COPY auth auth
CMD ["/run.dev.sh", "auth"]

FROM base AS delivery
COPY library library
COPY delivery delivery
CMD ["/run.dev.sh", "delivery"]


FROM api-gateway AS api-gateway-prod
# RUN ./gradlew api-gateway:assemble
CMD ["./gradlew", "api-gateway:bootRun"]

FROM service-registry AS service-registry-prod
# RUN ./gradlew service-registry:assemble
CMD ["./gradlew", "service-registry:bootRun"]

FROM auth AS auth-prod
# RUN ./gradlew auth:assemble
CMD ["./gradlew", "auth:bootRun"]

FROM delivery AS delivery-prod
# RUN ./gradlew delivery:assemble
CMD ["./gradlew", "delivery:bootRun"]
