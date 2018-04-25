package com.project.ors.config;

        import org.springframework.boot.autoconfigure.domain.EntityScan;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
        import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.project.ors.repository")
@EntityScan(basePackages = "com.project.ors.entity")
@EnableTransactionManagement
public class DBConfig {

}