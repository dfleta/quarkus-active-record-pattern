package org.pingpong.restjson;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.MariaDBContainer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.pingpong.restjson.MariaDbTestResource.Initializer;

@QuarkusTestResource(Initializer.class) //Registers the test resource
public class MariaDbTestResource {

    public static class Initializer implements QuarkusTestResourceLifecycleManager {
        // Defines the test resource interface

        // Sets MariaDB container object
        private MariaDBContainer mariaDBContainer;

        @Override
        public Map<String, String> start() {
            // Instantiate MariaDB container with required Docker image
            this.mariaDBContainer = new MariaDBContainer<>("mariadb:latest");
            // Starts the container and waits until the container is accepting connections
            this.mariaDBContainer.start();
            return getConfigurationParameters();
        }
        
        private Map<String, String> getConfigurationParameters() {
            // Overrides Quarkus’s configuration to point database connection
            // to the container one
            
            final Map<String, String> conf = new HashMap<>();
            conf.put("quarkus.datasource.jdbc.url", this.mariaDBContainer.getJdbcUrl());
            conf.put("quarkus.datasource.username", this.mariaDBContainer.getUsername());
            conf.put("quarkus.datasource.password", this.mariaDBContainer.getPassword());
            // conf.put("quarkus.datasource.driver", this.mariaDBContainer.getDriverClassName());
            return conf;
        }

        @Override
        public void stop() {
            // Stops the container
            if (this.mariaDBContainer != null) {
                this.mariaDBContainer.close();
            }
        }
    }
}
