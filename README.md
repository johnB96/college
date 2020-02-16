# College Cost API

REST API for looking up college costs conditionally with room and board.

## Getting Started

### Prerequisites

```
Java JDK
```

### Installing

Clone the repo
```
git clone https://github.com/johnB96/college.git
```

Building using gradle from the /college directory

#### Linux
```
./gradlew build
```

#### Windows
```
gradlew.bat build
```

Run the Spring Boot application using gradle from the /college directory

#### Linux
```
./gradlew bootRun
```

#### Windows
```
gradlew.bat bootRun
```

## Examples

Note that boarding is true by default.

### HTTP

```
GET http://localhost:8080/v1/cost?college=Purdue%20University,%20West%20Lafayette
```
or
```
GET http://localhost:8080/v1/cost?college=Purdue%20University,%20West%20Lafayette&boarding=true
```

returns 200 status and results in
```
{
    "college": "Purdue University, West Lafayette",
    "cost": 20833.0,
    "boarding": true
}
```

```
GET http://localhost:8080/v1/cost?college=Purdue%20University,%20West%20Lafayette&boarding=false
```

returns 200 status and results in
```
{
    "college": "Purdue University, West Lafayette",
    "cost": 10402.0,
    "boarding": false
}
```

If college is omitted or empty

```
GET http://localhost:8080/v1/cost
```
or
```
GET http://localhost:8080/v1/cost?college=
```
returns 400 status and results in
```
Error: College name is required
```

If college is not found in our data set
```
GET http://localhost:8080/v1/cost?college=Clown%20College
```
returns 404 status and results in
```
Error: College not found
```
### Curl

```
curl --location --request GET 'http://localhost:8080/v1/cost?college=Purdue%20University,%20West%20Lafayette'
```

returns
```
{"college":"Purdue University, West Lafayette","cost":20833.0,"boarding":true}
```

```
curl --location --request GET 'http://localhost:8080/v1/cost?college=Purdue%20University,%20West%20Lafayette&boarding=false'
```
returns
```
{"college":"Purdue University, West Lafayette","cost":10402.0,"boarding":false
```

## Running the tests

Run unit tests and publish jacoco coverage report using gradle
```
./gradlew build test
```

## Built With

* [Spring Initializr](https://start.spring.io/) - Generates folders and basic conventions.
* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used.
* [Gradle](https://gradle.org/) - Dependency and build management.

## Authors

* **John Bedalov**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details