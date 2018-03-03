package com.idugalic.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.config.java.AbstractCloudConfig;

@EntityScan
public class CloudConfiguration extends AbstractCloudConfig {

//	@Bean
//	public DataSource dataSource() {
//		return connectionFactory().dataSource();
//	}
}
