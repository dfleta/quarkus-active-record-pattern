# Rest-json-quarkus Active Record pattern and testcontainers

## Ejecutar la api (modo JVM)

Prepara el quarkus jar de la api que se incluirá en la imagen JVM que se construirá con docker-compose: 

`$ mvn package -Dmaven.test.skip=true`

Ejecuta:

`$ docker-compose up`

Para finalizar la ejecución:

`docker-compose down`

En http://localhost/8080 se sirve un pequeño front.

Endpoints:

```bash
curl -w "\n" http://localhost:8080/fruits/ -H "Content-Type: application/x-www-form-urlencoded"

curl -w "\n" http://localhost:8080/fruits/ -H "Content-Type: application/json"

curl -d '{"name":"Banana", "description":"Brings a Gorilla too"}' -H "Content-Type: application/json" -X POST http://localhost:8080/fruits

curl -d '{"name":"Banana", "description":"Brings a Gorilla too"}' -H "Content-Type: application/json" -X DELETE http://localhost:8080/fruits

curl -w "\n" http://localhost:8080/fruits/Apple -v
curl -w "\n" http://localhost:8080/fruits/Pizza -v
```

## Docker mariadb

Descargar la imagen:

`docker pull mariadb:latest`

Arrancar el contenedor con la configuración de `application.properties`:

`docker run -it --rm --name maria_fresh -e MYSQL_ROOT_PASSWORD=developer -e MYSQL_USR=developer -e MYSQL_PASSWORD=developer -e MYSQL_DATABASE=fruit -p 3306:3306 -d mariadb:latest`

Parar el contenedor:

`docker container stop maria_fresh`

## Ejecutar la aplicación en dev mode y dev profile

Con el contenedor corriendo:

`./mvnw clean compile quarkus:dev -Dquarkus.profile=dev`

## Testing de la aplicación

Debes disponer de una imagen docker `mariadb:latest`. No es necesario arrancar el contenedor antes pues `testcontainers` se encarga de ello:

```bash
# All tests
./mvnw test
# Unit testing
./mvnw -Dtest="ServiceFruitTest" test
# Integration test
./mvnw -Dtest="FruitResourceTest" test
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## ¿Cómo lo has hecho?

Bucea en la coleccion de recursos que he utilizado: [apuntes](./apuntes.txt)

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/rest-json-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

[Related guide section...](https://quarkus.io/guides/rest-json#creating-your-first-json-rest-service)
