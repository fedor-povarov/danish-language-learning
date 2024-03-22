#!/usr/bin/env bash

docker-compose down
docker rm $(docker ps --filter status=exited -q) || true