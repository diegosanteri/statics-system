## statistics-system

We would like to have a restful API for our statistics. The main use case for our API is to
calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is
called every time a transaction is made. It is also the sole input of this rest API. The other one
returns the statistic based of the transactions of the last 60 seconds.

## Getting Started

To run this project use the following commands

```
mvn clean install
```

After that you can start the application running 

```
mvn spring-boot:run
```

## Running only the tests

```
mvn verify
```

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

