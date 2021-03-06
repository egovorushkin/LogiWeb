package com.egovorushkin.logiweb.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:datasource.properties", "classpath:security-persistence" +
        ".properties"})
public class PersistenceJPAConfig {

    private static final Logger LOGGER = Logger.getLogger(PersistenceJPAConfig.class);
    private final Environment env;

    public PersistenceJPAConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(securityDataSource());
        em.setPackagesToScan("com.egovorushkin.logiweb.entities");

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource securityDataSource() {

        ComboPooledDataSource securityDataSource
                = new ComboPooledDataSource();

        try {
            securityDataSource.setDriverClass(env.getProperty("security.jdbc.driver"));
        } catch (PropertyVetoException exc) {
            LOGGER.error("Unacceptable value");
        }

        securityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
        securityDataSource.setUser(env.getProperty("security.jdbc.user"));
        securityDataSource.setPassword(env.getProperty("security.jdbc.password"));
        securityDataSource.setInitialPoolSize(
                getIntProperty("security.connection.pool.initialPoolSize"));
        securityDataSource.setMinPoolSize(
                getIntProperty("security.connection.pool.minPoolSize"));
        securityDataSource.setMaxPoolSize(
                getIntProperty("security.connection.pool.maxPoolSize"));
        securityDataSource.setMaxIdleTime(
                getIntProperty("security.connection.pool.maxIdleTime"));

        return securityDataSource;
    }


    private int getIntProperty(String propName) {
        String propVal = env.getProperty(propName);
        assert propVal != null;
        return Integer.parseInt(propVal);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty(
                "hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty(
                "hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate" +
                ".dialect"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache",
                "false");

        return hibernateProperties;
    }

}
