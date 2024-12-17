# SPRING EXAMPLE WITH INFLUX-DB

POC with Influx DB to write and read data. It uses a local instance of influx-db run using Docker.

## How to setup

Run the script `./run-local-environment.sh`. This will run a docker image on port 8086. The application uses a token
based authentication. To complete the setup, you need to:
- Create a new token using the influx UI or command line
- Configure spring to use the new token, injected as an environment variable named `TOKEN`

## How to use

Application contains two endpoint, one to write data and the other one to retrieve data. You can find sample requests
in the `./http/test.http` file.

## Run the entire application

### Run for testing

Run the script `run-local-environment.sh`: it will download and run  the local influxdb instance using Docker.
Then run the application. 

You can stop the image running `stop-local-environment.sh` script.
In any case, you have to generate the auth token manually after the first run of influx, and inject as env variables.

### Run in production

You can also run the entire app (webapp + influxdb) using the script `./run.sh`. This
will use docker compose to run both the app and the db mounting a docker volume too.
You have to generate the auth token manually after the first run of influx, and inject as env variables.