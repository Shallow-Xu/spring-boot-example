package com.ppc.spring.example.jpamultidatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源整体配置
 *
 * @author Shallow
 */
@Configuration
public class DataSourceConfig {
    @Primary
    @Bean(name = "userDataSource")
    @Qualifier("userDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.user")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "businessDataSource")
    @Qualifier("businessDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.business")
    public DataSource businessDataSource() {
        return DataSourceBuilder.create().build();
    }
}
