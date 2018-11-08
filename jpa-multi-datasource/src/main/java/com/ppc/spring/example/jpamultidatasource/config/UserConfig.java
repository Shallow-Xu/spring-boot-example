package com.ppc.spring.example.jpamultidatasource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 多数据源--用户
 *
 * @author Shallow
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryUser",
        transactionManagerRef = "transactionManagerUser",
        //设置Repository所在位置
        basePackages = {"com.ppc.spring.example.jpamultidatasource.repository.user"})
public class UserConfig {

    @Autowired
    @Qualifier("userDataSource")
    private DataSource userDataSource;
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean(name = "entityManagerUser")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryUser(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryUser")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryUser(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(userDataSource)
                //设置entity所在位置
                .packages("com.ppc.spring.example.jpamultidatasource.entity.user")
                .persistenceUnit("userPersistenceUnit")
                .properties(getVendorProperties())
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Primary
    @Bean(name = "transactionManagerUser")
    public PlatformTransactionManager transactionManagerUser(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryUser(builder).getObject());
    }

}