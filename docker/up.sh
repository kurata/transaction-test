#!/bin/sh
docker-compose --project-name transaction-service -f docker/docker-compose.yml up -d
echo "docker tansaction-service instance up ..."
sleep 4 # time up docker