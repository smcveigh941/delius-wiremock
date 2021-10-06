package uk.gov.justice.digital.hmpps.deliusWiremock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DeliusWiremockApplication {

  public static void main(String[] args) {
    SpringApplication.run(DeliusWiremockApplication.class, args);
  }
}
