package com.fragmanos.database;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author fragkakise on 30/11/2015.
 */
@Configuration
@EnableJpaRepositories
@EntityScan
public class DatabaseConfiguration {
}
