package io.glebtanaka.vertxserver.verticle.autoload;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.DeploymentOptions;

/**
 * Worker Verticle for auto-loading example
 */
public class WorkerVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        System.out.println("WorkerVerticle started");
        
        // Simulate some background work
        vertx.setPeriodic(5000, id -> {
            System.out.println("WorkerVerticle performing background task...");
            
            // Simulate CPU-intensive work
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println("WorkerVerticle background task completed");
        });
        
        startPromise.complete();
    }
    
    @Override
    public void stop(Promise<Void> stopPromise) {
        System.out.println("WorkerVerticle stopped");
        stopPromise.complete();
    }
}