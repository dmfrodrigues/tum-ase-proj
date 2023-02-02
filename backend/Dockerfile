FROM openjdk:17-jdk-alpine

RUN apk add inotify-tools

RUN mkdir -p /app
WORKDIR /app

COPY run.dev.sh watch_changes.sh /
RUN chmod 755 /*.sh

# Copy
COPY . .
RUN chmod +x gradlew

CMD ["./gradlew", "bootRun", "--nodaemon"]
