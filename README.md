# Distributed Tracing using Spring Boot 3, OpenTelemetry and Zipkin.

This code base is an attempt to learn how Distributed tracing works with Spring Boot and the open telemetry backend, [Zipkin](https://zipkin.io/).  We use 2 unsophisticated microservices, both built using Java 21 and Spring Boot 3.3.5 - _SportsClient_ and _PlayerRanker_. The _SportsClient_ calls the _PlayerRanker_ via HTTP passing the player's name to fetch the ATP ranking. The _PlayerRanker_ returns the ranking of the player.


# Steps

## Prerequisites
We need JDK 21, Spring Boot 3.3.5, Maven and the Docker Desktop to build and run the services.

##  Installation
Unzip the files to a folder and build them using Maven.
```bash
tmurali@fedora:~/javaprojects/sportsclient$ mvn clean package
tmurali@fedora:~/javaprojects/player-ranker-app$ mvn clean package
```
## Execution
Start the 2 microservices in 2 separate shells
```bash
tmurali@fedora:~/javaprojects/sportsclient$ java -jar target/sportsclient.jar
```
```bash
tmurali@fedora:~/javaprojects/player-ranker-app$ java -jar target/player-ranker-app.jar
```

Launch the Zipkin agent in the 3rd shell.
```bash
tmurali@fedora:~/tools$ docker run -d -p 9411:9411 openzipkin/zipkin
```
Invoke the SportsClient endpoint in the 4th shell using cURL. The output of cURL is piped to jq which a JSON formatter. jq is optional.

```bash
tmurali@fedora:~/temp$ curl http://localhost:8090/players | jq
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   198    0   198    0     0    357      0 --:--:-- --:--:-- --:--:--   357
[
  {
    "player": "Jannik Sinner",
    "ranking": 1
  },
  {
    "player": "Alex Zverev",
    "ranking": 2
  },
  {
    "player": "Carlos Alcaraz",
    "ranking": 3
  },
  {
    "player": "Daniil Medvedev",
    "ranking": 4
  },
  {
    "player": "Novak Djikovic",
    "ranking": 5
  }
]
tmurali@fedora:~/temp$ 
```
## TRACES VIEW

Access the Zipkin Viewer at http://localhost:9411/zipkin/

The 2 services illustrated here obviously don't have any performance issues. But in the real world, when there are issues, one can dig deeper into the invocation stack
by reviewing the [traces and spans](https://docs.lightstep.com/docs/understand-distributed-tracing).

## TODO

Need to make this work with [Jaegar](https://www.jaegertracing.io/docs/1.62/getting-started/).