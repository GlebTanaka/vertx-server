package io.glebtanaka.vertxserver.verticle.autoload;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * Main Verticle for auto-loading example
 */
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        System.out.println("MainVerticle started");
        
        // Simulate some initialization work
        vertx.setTimer(1000, id -> {
            System.out.println("MainVerticle initialization completed");
            startPromise.complete();
        });
    }
    
    @Override
    public void stop(Promise<Void> stopPromise) {
        System.out.println("MainVerticle stopped");
        stopPromise.complete();
    }
}