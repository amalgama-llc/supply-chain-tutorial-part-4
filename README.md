# Part 4: Supply Chain Web Service

## About
This repository contains the source code for the [Part 4 of the Supply Chain tutorial](https://platform.amalgamasimulation.com/amalgama/SupplyChainTutorial/part4/sc_tutorial_part_4.html).

The web service uses the supply chain simulation logic implemented in
[Part 3 of the Supply Chain tutorial](https://platform.amalgamasimulation.com/amalgama/SupplyChainTutorial/part3/sc_tutorial_part_3.html)
to model a delivery network of warehouses and stores.

It provides a synchronous HTTP endpoint to submit a scenario (initial data for the model) and return simulation results.

## How to build and run locally
1. Download and install JDK-17 and Maven 3.8.6+.
2. Download the source code of the desktop application for Part 3 of the Supply Chain tutorial: https://github.com/amalgama-llc/supply-chain-tutorial-part-3.
3. In the Part 3 source code folder, run `mvn clean package` console command . After the compilation is finished, a new folder `releng/com.company.tutorial3.product/target/repository` will be created. Its `plugins` subfolder contains the jar files with the simulation logic that we need: 
   - `com.company.tutorial3.datamodel_1.0.0.jar` and 
   - `com.company.tutorial3.simulation_1.0.0.jar`.
4. In that `plugins` subfolder, run the following two commands to install the simulation logic libraries into the local Maven repository:

```
mvn install:install-file -Dfile=com.company.tutorial3.datamodel_1.0.0.jar -DgroupId=com.company.tutorial3 -DartifactId=datamodel -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=com.company.tutorial3.simulation_1.0.0.jar -DgroupId=com.company.tutorial3 -DartifactId=simulation -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
```

5. In the Part 4 source code folder, run `mvn spring-boot:run` console command.

After the web service is started, open this link in your browser:

http://localhost:8080/hello

This should show 'Hello, World', meaning that the web service has started successfully.

## How to use

**To run a simulation with a custom scenario**, use an API tool of your choice (Postman, Curl, etc.) to send the following POST request to the web service:
- URL: http://localhost:8080/runExperiment
- Request type: POST
- Request body type: raw, JSON
- Request body: <paste here the contents of any scenario file from the 'src/main/resources/scenarios' folder>

Here is a sample of what you should get in response:

```
{
    "scenarioName": "scenario",
    "trucksCount": 1,
    "serviceLevel": 0.5714285714285714,
    "expenses": 420.0,
    "expensesToServiceLevel": 7.3500000000000005
}
```

This response contains the general statistics collected after the simulation is finished:
the service level (between 0 and 1), total transportation expenses, and the expenses-to-SL ratio that shows how much money is spent per one percent of the service level.

Try other scenarios from the 'src/main/resources/scenarios' folder, or create your own scenarios using the sample ones.

You can set up your own modeling conditions and see how it affects the simulation results: 
- the road network (nodes and arcs), 
- the set of warehouses and stores, 
- the set of trucks,
- average interval between the generated transportation requests,
- and maximum delivery time (belated deliveries are not cancelled, but they reduce the service level).
