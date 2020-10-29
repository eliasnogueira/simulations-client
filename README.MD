# Simulations client
This test project belongs to a set of projects listed bellow:

* [simulations-test](https://github.com/eliasnogueira/simulations-test)
* [test-commons](https://github.com/eliasnogueira/test-commons)

## What does this project do?
This is the client project for the [credit-simulator-api](https://github.com/eliasnogueira/credit-simulator-api), 
and the [simulations-test](https://github.com/eliasnogueira/simulations-test)
You need to run locally the API service to be able to run the tests.

## Project structure

### client
The package `src/main/java/client` has the [SimulationsClient](https://github.com/eliasnogueira/simulations-client/blob/master/src/main/java/client/SimulationsClient.java) class 
where contains all the methods to interact with the API expecting the regular user scenarios and the exceptions.

### exception
Custom exceptions to validate the correct return placed in the API documentation.

### model
The model class and its builder.