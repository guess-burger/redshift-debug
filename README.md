# Redshift Debug Example

This example project is intended to aid debugging a peculiar bug with Redshift.

It runs 3 queries against Redshift given some connection settings.
All queries should produce the same results (a truncation of the current date). However, the latter two fail
and return `2000-01-01`.
The only difference between the queries is how a null value is passed to the `PreparedStatement`, all should be valid.

## Update
Amazon were able to identify the issue. They responsed with
> I would like to inform you that our service team has confirmed this particular issue happening on the patch P185.
> The issue has been fixed on your cluster (workaround was to set off one of the internal GUC parameters on the cluster). Changes will be reflected once you reboot the cluster.

N.B. `P185` is a patch applied to the Redshift cluster itself, not the JDBC driver. As of 2024-11-14

> Regarding the permanent fix, yes there is a plan to fix in any of the upcoming patches. However there is no ETA as of now.
> The GUC parameter is something internal to redshift and it cant be applied at your end.

This means that as it stands, you need to raise a case with Amazon to have the fix applied. 
If this applies to you then I'd strongly suggest referencing this repository as the it took a lot of back and forth to reach this point, including multiple cases of Amazon being given the main code snippet but not being able to recreate the issue.

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
