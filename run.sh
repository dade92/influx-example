#!/bin/bash

cd deploy

echo "Pulling the latest images..."
docker compose pull app

docker compose up -d influxdb

docker compose up -d app

docker ps

