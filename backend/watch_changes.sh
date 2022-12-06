#!/bin/sh

while true; do
    inotifywait -e modify,create,delete,move -r /app/src
    exec $@ &
done
