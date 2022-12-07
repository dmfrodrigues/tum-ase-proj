#!/bin/sh

chmod 755 gradlew

./watch_changes.sh ./gradlew bootJar --build-cache &

./gradlew bootRun
