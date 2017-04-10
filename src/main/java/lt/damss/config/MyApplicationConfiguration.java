package lt.damss.config;

import org.springframework.context.annotation.Configuration;


/**
 * Created by paulius on 17.3.11.
 */
@Configuration
public class MyApplicationConfiguration {

//    @Bean
//    @Autowired
//    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        Properties jpaProperties = new Properties();
//        jpaProperties.setProperty("hibernate.show_sql", showsql);
//        jpaProperties.setProperty("hibernate.dialect", dialect);
//
//        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
//        localContainerEntityManagerFactoryBean.setPackagesToScan("your packages....");
//        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
//        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
//        localContainerEntityManagerFactoryBean.afterPropertiesSet();
//
//        return localContainerEntityManagerFactoryBean.getObject();
//    }



}
