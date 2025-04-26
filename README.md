# Vert.x Auto-loading Verticles Example

This project demonstrates how to auto-load and deploy multiple Vert.x verticles from a package.

## Project Structure

- `App.java`: Main application entry point that creates a Vert.x instance and demonstrates auto-loading verticles
- `HttpServerVerticle.java`: A basic HTTP server verticle
- `autoload` package: Contains verticles that are auto-loaded
  - `MainVerticle.java`: A simple verticle that simulates initialization work
  - `WorkerVerticle.java`: A worker verticle that simulates background processing
- `VerticleLoader.java`: Utility class that provides functionality to auto-load and deploy verticles from a package

## How It Works

The `VerticleLoader` utility class:
1. Scans a specified package for classes that extend `AbstractVerticle`
2. Instantiates each found verticle
3. Determines if it should be deployed as a worker verticle based on naming convention (contains "Worker" in the class name)
4. Deploys each verticle with appropriate options
5. Logs the deployment status

## Usage

To auto-load and deploy verticles from a package:

```java
// Create a Vertx instance
Vertx vertx = Vertx.vertx();

// Auto-load and deploy verticles from a package
VerticleLoader.deployVerticlesFromPackage(vertx, "io.glebtanaka.vertxserver.verticle.autoload");
```

## Creating Your Own Verticles

1. Create a new verticle class that extends `AbstractVerticle`
2. Place it in the package you want to auto-load from
3. If it's a worker verticle, include "Worker" in the class name

Example:

```java
package io.glebtanaka.vertxserver.verticle.autoload;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MyVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        System.out.println("MyVerticle started");
        startPromise.complete();
    }
}
```

## Running the Example

Run the `App` class to see the auto-loading in action:

```
java -cp target/vertx-server-1.0-SNAPSHOT.jar io.glebtanaka.App
```

Or run the test:

```
mvn test -Dtest=VerticleAutoloadTest
```