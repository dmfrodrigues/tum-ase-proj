#!/bin/sh

while true; do
    inotifywait -e modify,create,delete,move -r /app/delivery/src /app/library/src
    exec $@ &
done
