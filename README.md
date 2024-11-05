# Redshift Debug Example

This example project is intended to aid debugging a peculiar bug with Redshift.

It runs 3 queries against Redshift given some connection settings.
All queries should produce the same results (a truncation of the current date). However, the latter two fail
and return `2000-01-01`.
The only difference between the queries is how a null value is passed to the `PreparedStatement`, all should be valid.


## Build and Run - Local Java
```
redshift-debug $  mvn clean package
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------< com.example.redshift:redshift-debug >-----------------
[INFO] Building redshift-debug 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
...
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.959 s
[INFO] Finished at: 2024-11-05T13:08:00Z
[INFO] ------------------------------------------------------------------------
redshift-debug $  java -cp target/redshift-debug-1.0-SNAPSHOT.jar com.example.redshift.App "jdbc:redshift://<DB_CONNECTION_STRING>" "<USER>" "<PASSWORD>"
Attempting working query
2024-11-05
Attempting broken query
2000-01-01
Attempting another broken query
2000-01-01
Done!
redshift-debug $ 
```

## Build and Run - Docker
```
redshift-debug $ docker build -t redshift-example .
  ...
redshift-debug $ docker run redshift-example "jdbc:redshift://<DB_CONNECTION_STRING>" "<USER>" "<PASSWORD>"
Attempting working query
2024-11-05
Attempting broken query
2000-01-01
Attempting another broken query
2000-01-01
Done!
redshift-debug $ 
```
