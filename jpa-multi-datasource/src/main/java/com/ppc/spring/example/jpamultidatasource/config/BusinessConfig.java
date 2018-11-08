package com.ppc.spring.example.jpamultidatasource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 多数据源--业务
 *
 * @author Shallow
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryBusiness",
        transactionManagerRef = "transactionManagerBusiness",
        //设置repository所在位置
        basePackages = {"com.ppc.spring.example.jpamultidatasource.repository.business"})
public class BusinessConfig {

    @Autowired
    @Qualifier("businessDataSource")
    private DataSource businessDataSource;
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean(name = "entityManagerBusiness")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryBusiness(builder).getObject().createEntityManager();
    }

    @Bean(name = "entityManagerFactoryBusiness")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBusiness(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(businessDataSource)
                .properties(getVendorProperties())
                //设置实体类所在位置
                .packages("com.ppc.spring.example.jpamultidatasource.entity.business")
                .persistenceUnit("businessPersistenceUnit")
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "transactionManagerBusiness")
    PlatformTransactionManager transactionManagerBusiness(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryBusiness(builder).getObject());
    }

}