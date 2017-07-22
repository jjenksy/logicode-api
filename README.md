
# coding-exercise-iHeart
Spring Boot web app for API design

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
This is project uses the Maven build tool assume maven intalled.
```

### Installing

Clone or Fork this repo and open the project

```
git clone https://github.com/jjenksy/logicode-api.git
```

Build

```
maven clean package
```
Excecute

```
java -jar target/logicode-api-0.0.1-SNAPSHOT.jar
```

## Actuator
Access the actuator through postman or curl at:
```
localhost:8090/mappings
```
To view the metrics navigate to:

```
localhost:8090/metrics
```
To view the info.
```
localhost:8090/info
```
## Running the tests
Tests are ran by executing the command:
```
maven test
```
A jacoco test coverage report by running the command:
```
maven jacocoTestReport
```

To view the report navigate to build/report/jacoco/test/html/index.html

## Testing the API endpoints
The endpoints are easily viewed and tested using Swagger-UI to run the UI use the command
```
maven spring-boot:run
```
and navigate to http://localhost:8080/swagger-ui.html each of the endpoints are exposed through the UI and data can be 
passed to the controller here.


## Authors

* **John Jenkins** - (https://github.com/jjenksy)
