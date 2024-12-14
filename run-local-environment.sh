#!/bin/bash

mkdir local-env
cd local-env

docker run -d -p 8086:8086 \
  -v "./data:/var/lib/influxdb2" \
  -v "./config:/etc/influxdb2" \
  -e DOCKER_INFLUXDB_INIT_MODE=setup \
  -e DOCKER_INFLUXDB_INIT_USERNAME=admin \
  -e DOCKER_INFLUXDB_INIT_PASSWORD=password \
  -e DOCKER_INFLUXDB_INIT_ORG=example \
  -e DOCKER_INFLUXDB_INIT_BUCKET=test \
  --name influxdb \
  --rm \
  influxdb:2

