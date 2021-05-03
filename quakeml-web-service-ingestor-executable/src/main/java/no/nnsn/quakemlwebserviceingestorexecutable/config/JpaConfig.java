package no.nnsn.quakemlwebserviceingestorexecutable.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "no.nnsn.quakemlwebserviceingestorexecutable",
        entityManagerFactoryRef = "manualEntityManagerFactory",
        transactionManagerRef = "manualTransactionManager"
)
@PropertySources({
        @PropertySource({"classpath:jpa.properties" }),
        @PropertySource(value = "classpath:database.yml", factory = YamlPropertySourceFactory.class)
})
@EnableTransactionManagement
public class JpaConfig {

    private final Environment environment;

    @Autowired
    public JpaConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "DataSource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(environment.getProperty("datasource.driver-class-name"));
        dataSource.setJdbcUrl(
                environment.getProperty("datasource.url_prefix") +
                        environment.getProperty("database.address") + ":" +
                        environment.getProperty("database.port") + "/" +
                        environment.getProperty("database.name") + "?" +
                        environment.getProperty("datasource.url_properties")
        );
        dataSource.setUsername(environment.getProperty("database.username"));
        dataSource.setPassword(environment.getProperty("database.password"));

        dataSource.setMinimumIdle(Integer.valueOf(environment.getRequiredProperty("hibernate.hikari.minimumIdle")));
        dataSource.setMaximumPoolSize(Integer.valueOf(environment.getRequiredProperty("hibernate.hikari.maximumPoolSize")));
        dataSource.setConnectionTimeout(Integer.valueOf(environment.getRequiredProperty("hibernate.hikari.connectionTimeout")));
        dataSource.setIdleTimeout(Integer.valueOf(environment.getRequiredProperty("hibernate.hikari.idleTimeout")));
        dataSource.setPoolName("SpringBootJPAHikariCP");

        dataSource.addDataSourceProperty("cachePrepStmts", true);
        dataSource.addDataSourceProperty("prepStmtCacheSize", 256);
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        dataSource.addDataSourceProperty("useServerPrepStmts", true);


        return dataSource;
    }
    @Bean(name = "manualEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan(
                new String[] {
                        "no.nnsn.seisanquakemljpa.models.catalog"
                });
        final JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setJpaProperties(additionalProperties());
        return entityManager;
    }
    private Properties additionalProperties() {
        Properties properties = new Properties();

        // General hibernae properties
        properties.put(AvailableSettings.DIALECT, environment.getRequiredProperty("hibernate.dialect"));
        properties.put(AvailableSettings.HBM2DDL_AUTO, environment.getRequiredProperty("hibernate.ddl-auto"));
        properties.put(AvailableSettings.SHOW_SQL, environment.getRequiredProperty("hibernate.show_sql"));
        properties.put(AvailableSettings.FORMAT_SQL, environment.getRequiredProperty("hibernate.format_sql"));
        properties.put(AvailableSettings.GENERATE_STATISTICS, environment.getRequiredProperty("hibernate.generate_statistics"));
        properties.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, environment.getRequiredProperty("hibernate.naming.implicit-strategy"));
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, environment.getRequiredProperty("hibernate.naming.physical-strategy"));

        // Batching
        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, environment.getRequiredProperty("hibernate.jdbc.batch_size"));
        properties.put(AvailableSettings.ORDER_INSERTS, environment.getRequiredProperty("hibernate.order_inserts"));
        properties.put(AvailableSettings.ORDER_UPDATES, environment.getRequiredProperty("hibernate.order_updates"));
        properties.put(AvailableSettings.BATCH_VERSIONED_DATA, environment.getRequiredProperty("hibernate.jdbc.batch_versioned_data"));

        // Caching
        properties.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, environment.getRequiredProperty("hibernate.cache.use_second_level_cache"));
        properties.put(AvailableSettings.USE_QUERY_CACHE, environment.getRequiredProperty("hibernate.cache.use_query_cache"));
        properties.put(AvailableSettings.CACHE_REGION_FACTORY, environment.getRequiredProperty("hibernate.cache.region.factory_class"));
        properties.put(AvailableSettings.JPA_SHARED_CACHE_MODE, environment.getRequiredProperty("javax.persistence.sharedCache.mode"));
        properties.put(AvailableSettings.QUERY_PLAN_CACHE_MAX_SIZE, environment.getRequiredProperty("hibernate.query.plan_cache_max_size"));
        properties.put(AvailableSettings.QUERY_PLAN_CACHE_PARAMETER_METADATA_MAX_SIZE, environment.getRequiredProperty("hibernate.query.plan_parameter_metadata_max_size"));
        return properties;
    }
    @Bean(name = "manualTransactionManager")
    public JpaTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory().getObject());
        return transactionManager;
    }
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
