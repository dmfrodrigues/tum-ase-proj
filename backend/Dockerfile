FROM openjdk:17-jdk-alpine

RUN apk add inotify-tools

RUN mkdir -p /app
WORKDIR /app

COPY run.dev.sh watch_changes.sh /
RUN chmod 755 /*.sh

ARG JAR_FILE=build/libs/app.jar
RUN mkdir -p build/libs
COPY ${JAR_FILE} build/libs/app.jar
CMD ["java","-jar","build/libs/app.jar"]
