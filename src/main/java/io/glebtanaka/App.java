package io.glebtanaka;

import io.vertx.core.Vertx;
import io.glebtanaka.vertxserver.verticle.HttpServerVerticle;
import io.glebtanaka.vertxserver.util.VerticleLoader;

/**
 * Vert.x HTTP Server Example with Auto-loading Verticles
 */
public class App 
{
    public static void main( String[] args )
    {
        // Create a Vertx instance
        Vertx vertx = Vertx.vertx();

        // Deploy the HttpServerVerticle manually
        vertx.deployVerticle(new HttpServerVerticle(), res -> {
            if (res.succeeded()) {
                System.out.println("HttpServerVerticle deployed successfully with ID: " + res.result());

                // Auto-load and deploy verticles from the autoload package
                System.out.println("Auto-loading verticles from package...");
                VerticleLoader.deployVerticlesFromPackage(vertx, "io.glebtanaka.vertxserver.verticle.autoload");
            } else {
                System.out.println("Failed to deploy HttpServerVerticle: " + res.cause());
            }
        });
    }
}
