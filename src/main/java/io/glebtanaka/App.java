package io.glebtanaka;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * Vert.x HTTP Server Example
 */
public class App 
{
    public static void main( String[] args )
    {
        // Create a Vertx instance
        Vertx vertx = Vertx.vertx();

        // Create a Router
        Router router = Router.router(vertx);

        // Handle requests to the root path
        router.get("/").handler(ctx -> {
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/html");
            response.end("<h1>Hello from Vert.x!</h1>");
        });

        // Create the HTTP server
        HttpServer server = vertx.createHttpServer();

        // Handle requests using the router
        server.requestHandler(router);

        // Start the server on port 8080
        server.listen(8080, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port 8080");
            } else {
                System.out.println("Failed to start server: " + result.cause());
            }
        });
    }
}
