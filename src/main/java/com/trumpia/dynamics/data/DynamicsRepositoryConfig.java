package com.trumpia.dynamics.data;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.trumpia.model", "com.trumpia.dynamics.model"})
@EnableJpaRepositories(basePackages = {"com.trumpia.data", "com.trumpia.dynamics.data"})
@EnableTransactionManagement
public class DynamicsRepositoryConfig {

}
