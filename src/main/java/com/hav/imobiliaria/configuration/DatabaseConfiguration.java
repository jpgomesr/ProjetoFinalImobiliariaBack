package com.hav.imobiliaria.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseConfiguration {

    @Value(("${spring.datasource.url}"))
    String url;
    @Value(("${spring.datasource.username}"))
    String username;
    @Value(("${spring.datasource.password}"))
    String password;
    @Value(("${spring.datasource.driver-class-name}"))
    String driver;

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(config);
    }

}
