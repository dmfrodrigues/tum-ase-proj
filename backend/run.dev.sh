#!/bin/sh

chmod 755 ./gradlew

./watch_changes.sh ./gradlew $1:bootJar --build-cache &

./gradlew $1:bootRun
