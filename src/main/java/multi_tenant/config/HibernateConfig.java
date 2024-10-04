package multi_tenant.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "multi_tenant.repository", // Aponta para o pacote de repositórios multitenant
    entityManagerFactoryRef = "multiTenantEntityManagerFactory",
    transactionManagerRef = "multiTenantTransactionManager"
)
public class HibernateConfig {

    @Autowired
    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @Autowired
    private CurrentTenantIdentifierResolverImpl tenantIdentifierResolver;

    @Bean
    public LocalContainerEntityManagerFactoryBean multiTenantEntityManagerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
        properties.put("hibernate.tenant_identifier_resolver", tenantIdentifierResolver);
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("multi_tenant.entity"); // Aponta para o pacote de entidades multitenant
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(properties);
        return emf;
    }

    @Bean
    public PlatformTransactionManager multiTenantTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(multiTenantEntityManagerFactory().getObject());
        return transactionManager;
    }
}
