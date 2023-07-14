# ATrDW

This project is a back-end framework for semantic trajectory. The framework is an Analytical Processing for Multiple Aspect Trajectory, which handles raw data and the aspects of trajectories and loads trajectory data from a dataset into a single Aspect Trajectory Data Warehouse (ATrDW) schema.

# Installation
## Prerequisites

The system was compiled from the execution environment `JavaSE-1.8`.
All data was stored in `PostgreSQL 14/Postgis 3.3.3`.
We used `Apache Maven 3.6.3` to run the experiments.

## Quick Start

* Create a database to store the simulated input data. For example:
```sh
CREATE DATABASE trajectory_input;
```

* Run the script `tb_input.sql` to load the simulated trajectory data into the database. For example:
 ```sh
  psql - U postgres -W -h localhost -f ~\atrdw\scripts\tb_input.sql -d trajectory_input
  ```

* Create a database warehouse containing Postgis spatial functions. For example:
 ```sh
 CREATE DATABASE atrdw TEMPLATE template_postgis;
 ```
  
* Run the script `DW.sql` for create the ATrDW. Example:
 ```sh
 psql - U postgres -W -h localhost -f ~\atrdw\scripts\DW.sql -d atrdw
 ```

* Run the command below to start the ETL process and feed the ATrDW.
```sh
mvn compile exec:java -Dexec.mainClass="atrdw.Main_Input"
```
Finally, the ATrDW is read to run analytic queries. Some examples can be found in the class `QueryATrDWMainTest.java`.
Here is an example of how to execute this class:

## Run analytic query test
```sh
mvn compile exec:java -Dexec.mainClass="atrdw.foursquare.analytics.test.QueryATrDWMainTest"
```

# Development
## How load another trajectory data
If you want to expand the framework to load another trajectory data, you will need to follow the steps:

1. to implement the `InputMessageIF.java` interface;
2. to implement the `AspectDAOIF.java` interface for informs how the aspects will be saved in the ATrDW;
3. config the `~\atrdw\resources\input.properties` file, informing the:
   - input class name created at step one;
   - the class name that implements the `AspectDAOIF.java`;
   - The character that will be used to separate the aspects in the ATrDW.
     - Example:
        ```sh
        input_class=atrdw.foursquare.FoursquareInput
        aspectDao_class=atrdw.foursquare.FoursquareAspectDAO
        separator=;
       ```
