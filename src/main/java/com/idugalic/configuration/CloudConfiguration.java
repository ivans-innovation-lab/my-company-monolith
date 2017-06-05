package com.idugalic.configuration;


import javax.sql.DataSource;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

public class CloudConfiguration extends AbstractCloudConfig{
    
    @Bean
    public DataSource dataSource() {
        return connectionFactory().dataSource();
    }
}
