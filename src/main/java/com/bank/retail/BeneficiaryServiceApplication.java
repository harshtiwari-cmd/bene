package com.bank.retail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.*" })
// @EnableJpaRepositories("com.bank.retail.persistence.repository")
@EnableJpaRepositories(basePackages = { "com.*"})
// @EntityScan("com.bank.retail.persistence.entity")
@EntityScan(basePackages = { "com.*" })
public class BeneficiaryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeneficiaryServiceApplication.class, args);
    }

}
