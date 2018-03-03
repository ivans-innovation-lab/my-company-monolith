package com.idugalic.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;

@EntityScan
public class CloudConfiguration extends AbstractCloudConfig {

	@Bean
	public DataSource dataSource() {
		return connectionFactory().dataSource();
	}
}
