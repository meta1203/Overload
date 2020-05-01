# Overload

### Running Overload

```
java -XX:MaxRAMPercentage=100 -jar Overload-1.0.0.jar
```

### Using Overload

REST Endpoints:

```
GET http://<ip>:8080/ping - returns a random string matching server#nnn

POST http://<ip>:8080/overload?load=0.9&memory=1024&duration=600 - starts resource consumption with given parameters

load - percent of all cpu cores to use
memory - memory to use in MiB
duration - time in seconds to run
```
