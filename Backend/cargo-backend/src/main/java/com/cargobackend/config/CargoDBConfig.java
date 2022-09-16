package com.cargobackend.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargoDBConfig {
	
    @Bean
    @ConfigurationProperties(prefix = "cargo.datasource")
    public DataSource cargoDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean
//    @ConfigurationProperties(prefix = "test.datasource")
//    public DataSource vendorDataSource() {
//        return DataSourceBuilder.create().build();
//    }
}
