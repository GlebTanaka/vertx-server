package io.glebtanaka;

import io.vertx.core.Vertx;
import io.glebtanaka.vertxserver.verticle.HttpServerVerticle;

/**
 * Vert.x HTTP Server Example
 */
public class App 
{
    public static void main( String[] args )
    {
        // Create a Vertx instance
        Vertx vertx = Vertx.vertx();

        // Deploy the HttpServerVerticle
        vertx.deployVerticle(new HttpServerVerticle(), res -> {
            if (res.succeeded()) {
                System.out.println("HttpServerVerticle deployed successfully with ID: " + res.result());
            } else {
                System.out.println("Failed to deploy HttpServerVerticle: " + res.cause());
            }
        });
    }
}
