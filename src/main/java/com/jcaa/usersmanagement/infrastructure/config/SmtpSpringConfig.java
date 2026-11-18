package com.jcaa.usersmanagement.infrastructure.config;

import com.jcaa.usersmanagement.infrastructure.adapter.email.SmtpConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmtpSpringConfig {

  private static final String PROP_SMTP_HOST = "${smtp.host:smtp.gmail.com}";
  private static final String PROP_SMTP_PORT = "${smtp.port:587}";
  private static final String PROP_SMTP_USERNAME = "${smtp.username:}";
  private static final String PROP_SMTP_PASSWORD = "${smtp.password:}";
  private static final String PROP_SMTP_FROM = "${smtp.from.address:}";
  private static final String PROP_SMTP_FROM_NAME = "${smtp.from.name:Gestion de Edificios}";

  @Value(PROP_SMTP_HOST)
  private String smtpHost;

  @Value(PROP_SMTP_PORT)
  private String smtpPort;

  @Value(PROP_SMTP_USERNAME)
  private String smtpUsername;

  @Value(PROP_SMTP_PASSWORD)
  private String smtpPassword;

  @Value(PROP_SMTP_FROM)
  private String smtpFromAddress;

  @Value(PROP_SMTP_FROM_NAME)
  private String smtpFromName;

  @Bean
  public SmtpConfig smtpConfig() {
    final int resolvedPort = parsePort(smtpPort, 587);
    return new SmtpConfig(
        smtpHost, resolvedPort, smtpUsername, smtpPassword, smtpFromAddress, smtpFromName);
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
