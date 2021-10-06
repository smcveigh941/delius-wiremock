package uk.gov.justice.digital.hmpps.deliusWiremock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DeliusNomisWiremockConfiguration {

  @Value("${db.driver.class.name}")
  private String dbDriverClassName;

  @Value("${db.url}")
  private String dbUrl;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName(dbDriverClassName);
    dataSource.setUrl(dbUrl);

    return dataSource;
  }
}
