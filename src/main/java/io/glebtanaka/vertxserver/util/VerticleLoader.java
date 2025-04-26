package io.glebtanaka.vertxserver.util;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Utility class to auto-load verticles from a package
 */
public class VerticleLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerticleLoader.class);

    /**
     * Auto-loads and deploys all verticles from the specified package
     *
     * @param vertx          The Vertx instance
     * @param packageName    The package to scan for verticles
     */
    public static void deployVerticlesFromPackage(Vertx vertx, String packageName) {
        try {
            List<Class<?>> verticleClasses = findVerticleClasses(packageName);
            
            for (Class<?> verticleClass : verticleClasses) {
                LOGGER.info("Found verticle class: " + verticleClass.getName());
                
                try {
                    // Check if it's a worker verticle by name convention
                    boolean isWorker = verticleClass.getSimpleName().contains("Worker");
                    
                    // Create deployment options
                    DeploymentOptions options = new DeploymentOptions()
                            .setWorker(isWorker);
                    
                    // Create an instance of the verticle
                    AbstractVerticle verticle = (AbstractVerticle) verticleClass.getDeclaredConstructor().newInstance();
                    
                    // Deploy the verticle
                    vertx.deployVerticle(verticle, options, res -> {
                        if (res.succeeded()) {
                            LOGGER.info("Deployed verticle " + verticleClass.getSimpleName() + 
                                       " with ID: " + res.result() + 
                                       (isWorker ? " (as worker)" : ""));
                        } else {
                            LOGGER.error("Failed to deploy verticle " + verticleClass.getSimpleName() + 
                                        ": " + res.cause());
                        }
                    });
                } catch (Exception e) {
                    LOGGER.error("Error deploying verticle " + verticleClass.getName(), e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error scanning for verticles", e);
        }
    }

    /**
     * Finds all classes in the specified package that extend AbstractVerticle
     */
    private static List<Class<?>> findVerticleClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> verticleClasses = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        
        // Convert package name to path
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            
            if (directory.exists()) {
                // Get all .class files in the directory
                File[] files = directory.listFiles((dir, name) -> name.endsWith(".class"));
                
                if (files != null) {
                    for (File file : files) {
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        Class<?> clazz = Class.forName(className);
                        
                        // Check if the class extends AbstractVerticle
                        if (AbstractVerticle.class.isAssignableFrom(clazz) && !clazz.isInterface() && !java.lang.reflect.Modifier.isAbstract(clazz.getModifiers())) {
                            verticleClasses.add(clazz);
                        }
                    }
                }
            }
        }
        
        return verticleClasses;
    }
}