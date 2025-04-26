package io.glebtanaka.vertxserver.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

/**
 * HTTP Server Verticle
 */
public class HttpServerVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        // Create a Router
        Router router = Router.router(vertx);

        // Handle requests to the root path
        router.get("/").handler(ctx -> {
            ctx.response()
                .putHeader("content-type", "text/html")
                .end("<h1>Hello from HttpServerVerticle!</h1>");
        });

        // Handle requests to the /hello path
        router.get("/hello").handler(ctx -> {
            ctx.response()
                .putHeader("content-type", "text/html")
                .end("<h1>Hello World!</h1>");
        });

        // Create the HTTP server
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080, result -> {
                if (result.succeeded()) {
                    System.out.println("Server is now listening on port 8080");
                    startPromise.complete();
                } else {
                    System.out.println("Failed to start server: " + result.cause());
                    startPromise.fail(result.cause());
                }
            });
    }
}