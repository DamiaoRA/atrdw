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
  CREATE DATABASE atrdw TEMPLATE template_postgis;;
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
Here is an example how to execute this class:

```sh
mvn compile exec:java -Dexec.mainClass="atrdw.foursquare.analytics.test.QueryATrDWMainTest"
```

# Run analytic query test


# Scripts

The database scripts are in atrdw\scripts

* Tripbuilder dataset
```sh
atrdw/scripts/scriptsTripbuilder.zip
```

* Foursquare dataset (sample)
```sh
atrdw/scripts/sampl_input_foursquare.zip
```

* DDL Datawarehouse
```sh
atrdw/scripts/DW/DW.sql
```

* Regexlookbehind function
```sh
atrdw/scripts/DW/regexlookbehind_function.sql
```

## How load dataset
Config the input.properties file. Example:
```sh
input_class=atrdw.foursquare.FoursquareInput
aspectDao_class=atrdw.foursquare.FoursquareAspectDAO
separator=;
```
* Run the Main_Input class
```sh
mvn compile exec:java -Dexec.mainClass="atrdw.Main_Input"
```

# Run Foursquare analytic query test
```sh
mvn compile exec:java -Dexec.mainClass="atrdw.foursquare.analytics.test.QueryATrDWMainTest"
```

# Run Tripbuilder analytic query test
```sh
mvn compile exec:java -Dexec.mainClass="atrdw.tripbuilder.analytics.test.QueryATrDWMainTest"
```
