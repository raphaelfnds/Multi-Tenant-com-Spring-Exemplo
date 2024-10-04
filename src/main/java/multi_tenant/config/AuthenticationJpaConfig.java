package multi_tenant.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
    basePackages = "multi_tenant.auth.repository", // Aponta para o pacote de repositórios de autenticação
    entityManagerFactoryRef = "authEntityManagerFactory",
    transactionManagerRef = "authTransactionManager"
)
@EntityScan(basePackages = "multi_tenant.auth.entity") // Aponta para o pacote de entidades de autenticação
public class AuthenticationJpaConfig {

    @Value("${spring.auth.datasource.url}")
    private String url;

    @Value("${spring.auth.datasource.username}")
    private String username;

    @Value("${spring.auth.datasource.password}")
    private String password;

    @Bean(name = "authDataSource")
    public DataSource authDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Bean(name = "authEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean authEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(authDataSource());
        emf.setPackagesToScan("multi_tenant.auth.entity");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        emf.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "update");
        return emf;
    }

    @Bean(name = "authTransactionManager")
    public PlatformTransactionManager authTransactionManager(@Autowired EntityManagerFactory authEntityManagerFactory) {
        return new JpaTransactionManager(authEntityManagerFactory);
    }
}
