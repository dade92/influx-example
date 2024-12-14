#!/bin/bash

#docker run --name some-redis --rm -p 6379:6379 -d redis

docker run --name my-memcache --rm -p 11211:11211 -d memcached