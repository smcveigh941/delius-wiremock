package uk.gov.justice.digital.hmpps.deliusNomisWiremock;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dao.entity.OffenderEntity;
import uk.gov.justice.digital.hmpps.deliusNomisWiremock.dao.repository.OffenderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class DataLoader implements ApplicationRunner {

    @Value("#{'${dataset.size}'}")
    private Integer numberOfOffenders;

    private final OffenderRepository offenderRepository;

    @Autowired
    public DataLoader(OffenderRepository offenderRepository) {
        this.offenderRepository = offenderRepository;
    }

    public void run(ApplicationArguments args) {
        Faker faker = new Faker();
        List<OffenderEntity> offenders = new ArrayList<>();

        for (int i = 0; i < numberOfOffenders; i++) {
            OffenderEntity offender = new OffenderEntity();

            offender.setNomsNumber(faker.regexify("[A-Z][0-9]{4}[A-Z]{2}"));
            offender.setFirstName(faker.name().firstName());
            offender.setLastName(faker.name().lastName());
            offender.setCrnNumber(faker.regexify("[A-Z][0-9]{6}"));
            offender.setConditionalReleaseDate(faker.date().future(365, TimeUnit.DAYS));

            offenders.add(offender);
        }

        offenderRepository.saveAllAndFlush(offenders);
    }
}
