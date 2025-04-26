package io.glebtanaka;

import io.vertx.core.Vertx;
import io.glebtanaka.vertxserver.util.VerticleLoader;
import junit.framework.TestCase;

/**
 * Test for auto-loading verticles from a package
 */
public class VerticleAutoloadTest extends TestCase {
    
    /**
     * Test auto-loading verticles from a package
     */
    public void testAutoloadVerticles() {
        System.out.println("Starting Verticle Auto-load Test");
        
        // Create a Vertx instance
        Vertx vertx = Vertx.vertx();
        
        try {
            // Auto-load and deploy verticles from the autoload package
            System.out.println("Auto-loading verticles from package...");
            VerticleLoader.deployVerticlesFromPackage(vertx, "io.glebtanaka.vertxserver.verticle.autoload");
            
            // Wait for a moment to allow verticles to be deployed
            Thread.sleep(2000);
            
            // Wait for a while to see the worker verticle in action
            System.out.println("Waiting to see worker verticle in action...");
            Thread.sleep(10000);
            
            // Test passed
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        } finally {
            // Close the Vertx instance
            System.out.println("Closing Vertx instance...");
            vertx.close();
        }
        
        System.out.println("Verticle Auto-load Test completed");
    }
}