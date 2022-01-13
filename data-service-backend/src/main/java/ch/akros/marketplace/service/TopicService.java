package ch.akros.marketplace.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.akros.marketplace.api.model.FieldTypeChooseDTO;
import ch.akros.marketplace.api.model.FieldTypeResponseDTO;
import ch.akros.marketplace.api.model.TopicStoreDTO;
import ch.akros.marketplace.api.model.TopicValueStoreDTO;
import ch.akros.marketplace.entity.FieldType;
import ch.akros.marketplace.entity.FieldTypeChoose;
import ch.akros.marketplace.entity.Theme;
import ch.akros.marketplace.entity.Topic;
import ch.akros.marketplace.entity.TopicValue;
import ch.akros.marketplace.repository.AdvertiserRepository;
import ch.akros.marketplace.repository.FieldTypeRepository;
import ch.akros.marketplace.repository.ThemeRepository;
import ch.akros.marketplace.repository.TopicRepository;

@Service
public class TopicService {
	@Autowired
	private FieldTypeRepository fieldTypeRepository;

	@Autowired
	private ThemeRepository themeRepository;

	@Autowired
	private AdvertiserRepository advertiserRepository;

	@Autowired
	private TopicRepository topicRepository;

	public List<FieldTypeResponseDTO> listTopicFieldTypes(Long themeId, Boolean search) {
		return fieldTypeRepository.listTopicSearchFieldTypes(themeId, search).stream().map(this::toFieldTypeResponseDTO)
				.collect(Collectors.toList());
	}

	// TODO : mapper
	private FieldTypeResponseDTO toFieldTypeResponseDTO(FieldType fieldType) {
		FieldTypeResponseDTO result = new FieldTypeResponseDTO();
		result.setFieldTypeId(fieldType.getFieldTypeId());
		result.setFieldTypeDefinitionId(fieldType.getFieldTypeDefinition().getFieldTypeDefinitionId());
		result.setFieldTypeDefinitionDescription(fieldType.getFieldTypeDefinition().getDescription());
		result.setDescription(fieldType.getDescription());
		result.setShortDescription(fieldType.getShortDescription());
		result.setMinValue(fieldType.getMinValue());
		result.setMaxValue(fieldType.getMaxValue());
		result.setSearch(fieldType.isSearch());
		result.setOffer(fieldType.isOffer());
		result.setFieldTypeChooses(
				fieldType.getFieldTypeChooses().stream().sorted((e1, e2) -> e1.getSortNumber() - e2.getSortNumber())
						.map(this::toFieldTypeChoosesDTO).collect(Collectors.toList()));
		return result;
	}

	private FieldTypeChooseDTO toFieldTypeChoosesDTO(FieldTypeChoose fieldTypeChoose) {
		FieldTypeChooseDTO result = new FieldTypeChooseDTO();
		result.setFieldTypeChooseId(fieldTypeChoose.getFieldTypeChooseId());
		result.setDescription(fieldTypeChoose.getDescription());
		result.setSortNumber(fieldTypeChoose.getSortNumber());
		return result;
	}

	public void storeTopic(TopicStoreDTO topicStoreDTO) {
		Topic topic = new Topic();

		topic.setTopicId(topicStoreDTO.getTopicId());
		final Theme theme = themeRepository.getById(topicStoreDTO.getThemeId());
		topic.setTheme(theme);

		topic.setValidFrom(LocalDate.now());
		topic.setValidTo(LocalDate.now().plusDays(365));
		topic.setSearchOrOffer(topicStoreDTO.getSearchOrOffer());

		topic.setAdvertiser(advertiserRepository.getById(1L));

		topic.setTopicValues(topicStoreDTO.getTopicValueStores().stream().map(e -> toTopicValue(theme, topic, e))
				.collect(Collectors.toList()));

		topicRepository.save(topic);
	}

	private TopicValue toTopicValue(Theme theme, Topic topic, TopicValueStoreDTO topicValueStoreDTO) {
		TopicValue result = new TopicValue();
		// result.setTopicValueId(topicValueStoreDTO.);
		result.setFieldType(fieldTypeRepository.getById(topicValueStoreDTO.getFieldTypeId()));
		result.setTheme(theme);
		result.setTopic(topic);
		result.setValueNum(topicValueStoreDTO.getValueNum());
		result.setValueVarchar(topicValueStoreDTO.getValueVarchar());
		result.setValueDate(topicValueStoreDTO.getValueDate());
		return result;
	}
}
