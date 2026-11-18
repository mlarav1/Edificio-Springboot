package com.jcaa.usersmanagement.infrastructure.config;

import com.jcaa.usersmanagement.infrastructure.adapter.persistence.config.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class DataSourceSpringConfig {

  private static final String PROP_DB_HOST = "${db.host:localhost}";
  private static final String PROP_DB_PORT = "${db.port:3306}";
  private static final String PROP_DB_NAME = "${db.name:crud_edificios}";
  private static final String PROP_DB_USERNAME = "${db.username:root}";
  private static final String PROP_DB_PASSWORD = "${db.password:}";
  private static final String PROP_DB_SSL_MODE = "${db.ssl-mode:DISABLED}";

  private static final String LOG_DATASOURCE_INIT =
      "[DataSourceSpringConfig] DataSource inicializado. host={} port={}";

  @Value(PROP_DB_HOST)
  private String dbHost;

  @Value(PROP_DB_PORT)
  private String dbPort;

  @Value(PROP_DB_NAME)
  private String dbName;

  @Value(PROP_DB_USERNAME)
  private String dbUsername;

  @Value(PROP_DB_PASSWORD)
  private String dbPassword;

  @Value(PROP_DB_SSL_MODE)
  private String dbSslMode;

  @Bean
  public DataSource dataSource() {
    final String resolvedHost = (dbHost == null || dbHost.isBlank()) ? "localhost" : dbHost.trim();
    final int resolvedPort = parsePort(dbPort, 3306);
    final String resolvedName =
        (dbName == null || dbName.isBlank()) ? "crud_edificios" : dbName.trim();
    final String resolvedUser =
        (dbUsername == null || dbUsername.isBlank()) ? "root" : dbUsername.trim();
    final String resolvedSsl =
        (dbSslMode == null || dbSslMode.isBlank()) ? "DISABLED" : dbSslMode.trim();

    final DatabaseConfig config =
        new DatabaseConfig(
            resolvedHost, resolvedPort, resolvedName, resolvedUser, dbPassword, resolvedSsl);

    final HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(config.buildJdbcUrl());
    hikariConfig.setUsername(config.username());
    hikariConfig.setPassword(config.password());
    hikariConfig.setMaximumPoolSize(10);
    hikariConfig.setMinimumIdle(0);
    hikariConfig.setInitializationFailTimeout(-1);
    hikariConfig.setConnectionTimeout(30_000);

    log.info(LOG_DATASOURCE_INIT, resolvedHost, resolvedPort);
    return new HikariDataSource(hikariConfig);
  }

  private static int parsePort(final String rawPort, final int defaultPort) {
    if (rawPort == null || rawPort.isBlank()) {
      return defaultPort;
    }
    try {
      return Integer.parseInt(rawPort.trim());
    } catch (final NumberFormatException ignored) {
      return defaultPort;
    }
  }
}
