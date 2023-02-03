#!/bin/sh

chmod 755 ./gradlew

./watch_changes.sh ./gradlew delivery:bootJar --build-cache &

./gradlew delivery:bootRun
