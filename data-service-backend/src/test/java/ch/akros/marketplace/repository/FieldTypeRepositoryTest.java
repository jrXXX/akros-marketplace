package ch.akros.marketplace.repository;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import ch.akros.marketplace.AkrosMarketplacedataServiceApplication;
import ch.akros.marketplace.entity.Theme;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ContextConfiguration(classes = AkrosMarketplacedataServiceApplication.class)
@TestPropertySource("classpath:postgres_db_url.properties")
@Slf4j
@Transactional
public class FieldTypeRepositoryTest {
	@Autowired
	private ThemeRepository themeRepository;

	@Test
	public void allEntriesExistsInFieldTypeDefinition() throws IOException {
		Theme theme = themeRepository.findById(1L).orElse(null);

		if (theme != null) {
			log.error(String.format("theme[id:%d/description:%s/shortDescription:%s/fieldTypeSize:%d]",
					theme.getThemeId(), theme.getDescription(), theme.getShortDescription(),
					theme.getFieldTypes().size()));

			theme.getFieldTypes().forEach(e -> log.error(String.format(
					"fieldType[id:%d/description:%s/shortDescription:%s/sortNumber:%d/minValue:%d/maxValue:%d/fieldTypeDefinitionId:%d/fieldTypeDefinitionDescription:%s]",
					e.getFieldTypeId(), e.getDescription(), e.getShortDescription(), e.getSortNumber(), e.getMinValue(),
					e.getMaxValue(), e.getFieldTypeDefinition().getFieldTypeDefinitionId(),
					e.getFieldTypeDefinition().getDescription())));
		}
	}
}
