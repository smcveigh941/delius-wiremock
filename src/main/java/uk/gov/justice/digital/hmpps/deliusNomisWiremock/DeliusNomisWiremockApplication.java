package uk.gov.justice.digital.hmpps.deliusNomisWiremock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DeliusNomisWiremockApplication {

  public static void main(String[] args) {
    SpringApplication.run(DeliusNomisWiremockApplication.class, args);
  }
}
