
package ch.akros.marketplace.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import ch.akros.marketplace.AkrosMarketplacedataServiceApplication;
import ch.akros.marketplace.entity.VWTheme;

@SpringBootTest
@ContextConfiguration(classes = AkrosMarketplacedataServiceApplication.class)
@TestPropertySource("classpath:postgres_db_url.properties")
@Transactional
public class VWThemeRepositoryTest {
  @Autowired
  private VWThemeRepository vwThemeRepository;

  @Test
  public void allEntriesExistsInFieldTypeDefinition() throws IOException {
    List<VWTheme> vwThemeList = vwThemeRepository.findAll();
    assertTrue(vwThemeList.size() > 0);
  }
}