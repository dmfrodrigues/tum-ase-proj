#!/bin/bash

mongoimport --host $MONGO_HOST --db $MONGO_DB --authenticationDatabase admin --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --type json --jsonArray --collection principal --file json/principal.json
mongoimport --host $MONGO_HOST --db $MONGO_DB --authenticationDatabase admin --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --type json --jsonArray --collection token     --file json/token.json
mongoimport --host $MONGO_HOST --db $MONGO_DB --authenticationDatabase admin --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --type json --jsonArray --collection agent     --file json/agent.json
mongoimport --host $MONGO_HOST --db $MONGO_DB --authenticationDatabase admin --username $MONGO_INITDB_ROOT_USERNAME --password $MONGO_INITDB_ROOT_PASSWORD --type json --jsonArray --collection delivery  --file json/delivery.json
