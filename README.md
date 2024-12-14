# SPRING EXAMPLE WITH REDIS

POC with Redis (and memcached) as a cache and Spring Boot. Application contains two APIs, one to store 
and the other to read objects from the cache.

## How to setup

You can set as cache Redis or Memcached using the attribute `enabledCache` inside the yml file and set it as
`redis` or `memcached` based on the type of cache you want to use. At the moment, you have to manually comment/uncomment
the run script to change the docker image.

## How to use

Application contains two endpoint, one to write data in the cache and the other one to retrieve data from the cache.

Write in the cache:

```http request
POST http://localhost:8080/user/add

{
  "name": "Davide"
}
```

Read from the cache:

```http request
GET http://localhost:8080/user
```

## Run the entire application

Run the script `run-local-environment.sh`: it will download and run  the local cache instance using Docker.
Then run the application. You can stop the image running `stop-local-environment.sh` script.