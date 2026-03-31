package tsutsu.td5spingboot1.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration de la DataSource.
 * Remplace l'ancienne classe DbConnection.
 *
 * Spring Boot lit automatiquement les propriétés depuis application.properties :
 *   spring.datasource.url      = ${DB_URL}
 *   spring.datasource.username = ${DB_USER}
 *   spring.datasource.password = ${DB_PASSWORD}
 *
 * La DataSource et le JdbcTemplate sont donc créés et injectés automatiquement.
 */
@Configuration
public class DataSourceConfig {

}
