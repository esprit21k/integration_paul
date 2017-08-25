package com.trumpia.dynamics.schema.data;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.trumpia.dynamics.schema.model", "com.trumpia.dynamics.model", "com.trumpia.model."})
@EnableJpaRepositories(basePackages = {"com.trumpia.dynamics.schema.data", "com.trumpia.dynamics.data", "com.trumpia.data"})
@EnableTransactionManagement
public class DynamicsSchemaRepositoryConfig {

}
