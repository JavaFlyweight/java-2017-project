package com.example.java.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.java.*" })
@EnableMongoRepositories(basePackages = { "com.example.java.repository" })
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableAutoConfiguration(exclude = { MongoAutoConfiguration.class })
public class BudgetWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetWebappApplication.class, args);
    }
}
