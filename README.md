# ATrDW

This project is a back-end framework for semantic trajectory. The framework is an Analytical Processing for Multiple Aspect Trajectory, which handles raw data and the aspects of trajectories and loads trajectory data from a dataset into a single Aspect Trajectory Data Warehouse (ATrDW) schema.

# Prerequisites

# Run analytic query test
```sh
mvn compile exec:java -Dexec.mainClass="mobhsap.foursquare.analytics.test.QueryATrDWMainTest"
```


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
