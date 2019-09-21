# Quick start

## Basic commands
To start in dev mode
```
sbt run
```

To start with different port
```
sbt "run 8080"
```

To run all tests
```
sbt test
```

To run specific test
```
sbt "testOnly BookControllerSpec"
```

To run all tests with coverage
```
sbt clean coverage test coverageReport
```

## Settings

### MongoDB
Connection and database are configured under `conf/application.conf/`. The default value can be overwritten by setting the environment variable `DB_URI`. (Especially useful for docker)
