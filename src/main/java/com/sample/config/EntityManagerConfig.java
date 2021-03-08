package com.sample.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@ComponentScan("com.sample")
@PropertySource("file:./database.properties")
@EnableJpaRepositories(
        basePackages = "com.sample",
        entityManagerFactoryRef = "mainEntityManager",
        transactionManagerRef = "mainTransactionManager")
public class EntityManagerConfig {
	
    @Value("${main.db.driver}")
    private String maindriver;
    @Value("${main.db.url}")
    private String mainurl;
    @Value("${main.db.username}")
    private String mainusername;
    @Value("${main.db.password}")
    private String mainpassword;
    @Value("${second.db.driver}")
    private String secdriver;
    @Value("${second.db.url}")
    private String securl;
    @Value("${second.db.username}")
    private String secusername;
    @Value("${second.db.password}")
    private String secpassword;
    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.show_sql}")
    private boolean showSQL;
    @Value("${hibernate.format_sql}")
    private boolean formatSQL;
    @Value("${entitymanager.packages.to.scan}")
    private String packageScan;
    @Value("${connection.release_mode}")
    private String releaseMode;
	
	
	@Bean
    public DataSource diyDatasource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource tax2021Datasource = mainDataSource();
        DataSource tax2022Datasource = secondDataSource();
        targetDataSources.put(DIYDatabase.TAX_2021, 
          tax2021Datasource);
        targetDataSources.put(DIYDatabase.TAX_2022, 
          tax2022Datasource);

        DataSourceRouter routingDatasource 
          = new DataSourceRouter();
        routingDatasource.setTargetDataSources(targetDataSources);
        routingDatasource.setDefaultTargetDataSource(tax2021Datasource);
        return routingDatasource;
    }
	
    @Bean(name = "mainDataSource")
    @Primary
    public DataSource mainDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(maindriver);
        dataSource.setUrl(mainurl);
        dataSource.setUsername(mainusername);
        dataSource.setPassword(mainpassword);
        return dataSource;
    }
    
    @Bean(name = "secondDataSource")
    public DataSource secondDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(secdriver);
        dataSource.setUrl(securl);
        dataSource.setUsername(secusername);
        dataSource.setPassword(secpassword);
        return dataSource;
    }
    
    @Bean(name = "mainEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean mainEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(diyDatasource());
        em.setPackagesToScan(new String[] { packageScan });
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean(name = "mainTransactionManager")
    @Primary
    public PlatformTransactionManager mainTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(diyDatasource());
        return transactionManager;
    }

    @Bean(name = "mainSessionFactory")
    @Primary
    public LocalSessionFactoryBean mainSessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(mainDataSource());
        sessionFactoryBean.setPackagesToScan(packageScan);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", false);
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.put("hibernate.show_sql",showSQL);
        properties.put("hibernate.format_sql",formatSQL);
        properties.put("entitymanager.packages.to.scan",packageScan);
        properties.put("connection.release_mode",releaseMode);
        return properties;
    }

}
