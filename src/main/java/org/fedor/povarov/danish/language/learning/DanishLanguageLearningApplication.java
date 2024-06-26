package org.fedor.povarov.danish.language.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
@EntityScan
@ComponentScan(basePackages = {"org.fedor.povarov.danish.language.learning.*"})
public class DanishLanguageLearningApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DanishLanguageLearningApplication.class, args);
    }
}
