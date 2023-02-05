#!/bin/bash

docker build -t gitlab.lrz.de:5005/ase-22-23/team15/tum-ase-proj/frontend:latest . --target frontend-prod
docker push gitlab.lrz.de:5005/ase-22-23/team15/tum-ase-proj/frontend:latest
