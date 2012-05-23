package com.bank.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.bank")
@EnableTransactionManagement
@ImportResource("classpath:com/bank/config/app-config.xml")
public class AppConfig {

	@Autowired
	DataSource dataSource;

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(this.dataSource);
	}

}
