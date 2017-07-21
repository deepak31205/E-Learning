package com.eLearning.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/*Reading all the values from application.properties file and creating
  datasource, setting hibernate properties, creating sessionFactory,
  creating hibernate transaction manager*/

@Configuration
public class DatabaseConfig {
	
	@Value("${spring.datasource.driver-class-name}")     
	private String driverClassName;
    
    @Value("${spring.datasource.url}")                 
    private String url;
    
    @Value("${spring.datasource.data-username}")             
    private String username;
    
    @Value("${spring.datasource.data-password}")             
    private String password;
    
    @Value("${hibernate.dialect}")         
    private String hibernateDialect;
    
    @Value("${hibernate.show_sql}")     
    private String hibernateShowSql;
    
    @Value("${hibernate.hbm2ddl.auto}") 
    private String hibernateHbm2ddlAuto;
    
    
    /*To create datasource*/
    @Bean    
    public DataSource getDataSource()
    {
        DriverManagerDataSource ds = new DriverManagerDataSource();        
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);    
        return ds;
    }
    
    /*To create hibernate transaction manager*/
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
    {
        HibernateTransactionManager htm = new HibernateTransactionManager();
        htm.setSessionFactory(sessionFactory);
        return htm;
    }
    
    /*To get hibernate template object*/
    @Bean
    @Autowired
    public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory)
    {
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
        return hibernateTemplate;
    }
    
    /*To create session factory by providing datasource, hibernate properties*/
    @Bean
    public LocalSessionFactoryBean getSessionFactory()
    {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(getDataSource());
        localSessionFactoryBean.setHibernateProperties(getHibernateProperties());        
        localSessionFactoryBean.setPackagesToScan(new String[]{"com.eLearning"});
        return localSessionFactoryBean;
    }
    
    
    /*To set all the hibernate properties*/
    @Bean
    public Properties getHibernateProperties()
    {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", hibernateShowSql);
        properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        return properties;
    }
}
