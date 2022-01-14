
package ch.akros.marketplace.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import ch.akros.marketplace.api.model.FieldTypeChooseResponseDTO;
import ch.akros.marketplace.api.model.FieldTypeResponseDTO;
import ch.akros.marketplace.api.model.TopicLoadResponseDTO;
import ch.akros.marketplace.api.model.TopicSearchColumnHeaderResponseDTO;
import ch.akros.marketplace.api.model.TopicSearchFieldTypeValuesRequestDTO;
import ch.akros.marketplace.api.model.TopicSearchListResponseDTO;
import ch.akros.marketplace.api.model.TopicSearchResponseDTO;
import ch.akros.marketplace.api.model.TopicSearchValueResponseDTO;
import ch.akros.marketplace.api.model.TopicStoreRequestDTO;
import ch.akros.marketplace.api.model.TopicValueLoadResponseDTO;
import ch.akros.marketplace.api.model.TopicValueStoreRequestDTO;
import ch.akros.marketplace.constants.EFieldTypeDefinition;
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
  private FieldTypeRepository  fieldTypeRepository;

  @Autowired
  private ThemeRepository      themeRepository;

  @Autowired
  private AdvertiserRepository advertiserRepository;

  @Autowired
  private TopicRepository      topicRepository;

  @Autowired
  private JdbcTemplate         jdbcTemplate;

  @Autowired
  private ThemeService         themeService;

  public List<FieldTypeResponseDTO> listTopicFieldTypes(Long themeId, String search) {
    return fieldTypeRepository.listTopicSearchFieldTypes(themeId, "SEARCH".equalsIgnoreCase(search))
                              .stream()
                              .map(this::toFieldTypeResponseDTO)
                              .collect(Collectors.toList());
  }

  private FieldTypeResponseDTO toFieldTypeResponseDTO(FieldType fieldType) {
    FieldTypeResponseDTO result = new FieldTypeResponseDTO();
    result.setThemeId(fieldType.getTheme().getThemeId());
    result.setFieldTypeId(fieldType.getFieldTypeId());
    result.setFieldTypeDefinitionId(fieldType.getFieldTypeDefinition().getFieldTypeDefinitionId());
    result.setFieldTypeDefinitionDescription(fieldType.getFieldTypeDefinition().getDescription());
    result.setDescription(fieldType.getDescription());
    result.setShortDescription(fieldType.getShortDescription());
    result.setMinValue(fieldType.getMinValue());
    result.setMaxValue(fieldType.getMaxValue());
    result.setSearch(fieldType.isSearch());
    result.setOffer(fieldType.isOffer());
    result.setFieldTypeChooses(fieldType.getFieldTypeChooses()
                                        .stream()
                                        .sorted((e1, e2) -> e1.getSortNumber() - e2.getSortNumber())
                                        .map(this::toFieldTypeChoosesResponseDTO)
                                        .collect(Collectors.toList()));
    return result;
  }

  private FieldTypeChooseResponseDTO toFieldTypeChoosesResponseDTO(FieldTypeChoose fieldTypeChoose) {
    FieldTypeChooseResponseDTO result = new FieldTypeChooseResponseDTO();
    result.setFieldTypeChooseId(fieldTypeChoose.getFieldTypeChooseId());
    result.setDescription(fieldTypeChoose.getDescription());
    result.setSortNumber(fieldTypeChoose.getSortNumber());
    return result;
  }

  public void storeTopic(TopicStoreRequestDTO topicStoreResponseDTO) {
    Topic topic = new Topic();

    topic.setTopicId(topicStoreResponseDTO.getTopicId());
    final Theme theme = themeRepository.getById(topicStoreResponseDTO.getThemeId());
    topic.setTheme(theme);

    topic.setValidFrom(LocalDate.now());
    topic.setValidTo(LocalDate.now().plusDays(365));
    topic.setSearchOrOffer(topicStoreResponseDTO.getSearchOrOffer());

    topic.setAdvertiser(advertiserRepository.getById(1L));

    topic.setTopicValues(topicStoreResponseDTO.getTopicValueStores()
                                              .stream()
                                              .map(e -> toTopicValue(theme, topic, e))
                                              .collect(Collectors.toList()));

    topicRepository.save(topic);
  }

  private TopicValue toTopicValue(Theme theme, Topic topic, TopicValueStoreRequestDTO topicValueStoreResponseDTO) {
    TopicValue result = new TopicValue();
    result.setFieldType(fieldTypeRepository.getById(topicValueStoreResponseDTO.getFieldTypeId()));
    result.setTheme(theme);
    result.setTopic(topic);
    result.setValueNum(topicValueStoreResponseDTO.getValueNum());
    result.setValueVarchar(topicValueStoreResponseDTO.getValueVarchar());
    result.setValueDate(topicValueStoreResponseDTO.getValueDate());
    return result;
  }

  public TopicLoadResponseDTO loadTopic(Long topicId) {
    Topic topic = topicRepository.getById(topicId);

    TopicLoadResponseDTO result = new TopicLoadResponseDTO();
    result.setSearchOrOffer(topic.getSearchOrOffer());
    result.setThemeId(topic.getTheme().getThemeId());
    result.setTopicId(topicId);

    result.setTopicValues(topic.getTopicValues()
                               .stream()
                               .sorted((e1, e2) -> e1.getFieldType().getSortNumber()
                                                   - e2.getFieldType().getSortNumber())
                               .map(this::toTopicValueLoadResponseDTO)
                               .collect(Collectors.toList()));

    return result;
  }

  private TopicValueLoadResponseDTO toTopicValueLoadResponseDTO(TopicValue topicValue) {
    TopicValueLoadResponseDTO result = new TopicValueLoadResponseDTO();

    result.setTopicValueId(topicValue.getTopicValueId());

    // from FieldTypeDefinition
    result.setFieldTypeDefinitionId(topicValue.getFieldType().getFieldTypeDefinition().getFieldTypeDefinitionId());
    result.setFieldTypeDefinitionDescription(topicValue.getFieldType().getFieldTypeDefinition().getDescription());
    result.setFieldTypeChooses(topicValue.getFieldType()
                                         .getFieldTypeChooses()
                                         .stream()
                                         .sorted((e1, e2) -> e1.getSortNumber() - e2.getSortNumber())
                                         .map(this::toFieldTypeChoosesResponseDTO)
                                         .collect(Collectors.toList()));

    // from FieldType
    result.setFieldTypeId(topicValue.getFieldType().getFieldTypeId());
    result.setFieldTypeDescription(topicValue.getFieldType().getDescription());
    result.setFieldTypeShortDescription(topicValue.getFieldType().getShortDescription());
    result.setMaxValue(topicValue.getFieldType().getMaxValue());
    result.setMinValue(topicValue.getFieldType().getMinValue());

    // from TopicValue
    result.setValueBoolean(topicValue.getValueBoolean());
    result.setValueDate(topicValue.getValueDate());
    result.setValueNum(topicValue.getValueNum());
    result.setValueVarchar(topicValue.getValueVarchar());

    return result;
  }

  public void deleteTopic(Long topicId) {
    topicRepository.deleteById(topicId);
  }

  public TopicSearchListResponseDTO searchTopic(List<TopicSearchFieldTypeValuesRequestDTO> topicSearchFieldTypeValuesRequestDTO) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    Long themeId = null;

    StringBuilder sqlStringBuilder = new StringBuilder("select t from topic t where t.theme.themeId=:themeId");
    for (int i = 0; i < topicSearchFieldTypeValuesRequestDTO.size(); i++) {
      if (i == 0) {
        themeId = topicSearchFieldTypeValuesRequestDTO.get(0).getThemeId();
        namedParameters.addValue("themeId", themeId);
      }
      addSqlSubselect(i, topicSearchFieldTypeValuesRequestDTO.get(i), sqlStringBuilder, namedParameters);
    }

    NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    List<Topic> topics = namedParameterJdbcTemplate.queryForList(sqlStringBuilder.toString(),
                                                                 namedParameters,
                                                                 Topic.class);

    TopicSearchListResponseDTO result = new TopicSearchListResponseDTO();

    result.setColumnHeader(themeService.listThemeSearchFieldTypes(themeId)
                                       .stream()
                                       .map(this::toTopicSearchColumnHeaderResponseDTO)
                                       .collect(Collectors.toList()));

    result.setTopics(topics.stream().map(this::toTopicSearchResponseDTO).collect(Collectors.toList()));

    return result;
  }

  private void addSqlSubselect(int i,
                               TopicSearchFieldTypeValuesRequestDTO topicSearchFieldTypeValuesRequestDTO,
                               StringBuilder sqlStringBuilder,
                               MapSqlParameterSource namedParameters)
  {
    Long fieldTypeDefinitionId = fieldTypeRepository.getById(topicSearchFieldTypeValuesRequestDTO.getFieldTypeId())
                                                    .getFieldTypeDefinition()
                                                    .getFieldTypeDefinitionId();
    EFieldTypeDefinition eFieldTypeDefinition = EFieldTypeDefinition.values()[fieldTypeDefinitionId.intValue()];

    switch (eFieldTypeDefinition) {
      case PRICE:
      case NUMBER: {
        if (topicSearchFieldTypeValuesRequestDTO.getValueNumFrom() != null
            && topicSearchFieldTypeValuesRequestDTO.getValueNumFrom() > 0)
        {
          if (topicSearchFieldTypeValuesRequestDTO.getValueNumTo() != null
              && topicSearchFieldTypeValuesRequestDTO.getValueNumTo() > 0)
          {
            sqlStringBuilder.append(String.format(" and t in (select tv.topic from TopicValue tv where tv.theme.themeId=:themeId and tv.fieldType.fieldTypeId=:fieldTypeId%d and tv.valueNumber between :valueNumberFrom%d and :valueNumberTo%d",
                                                  i,
                                                  i,
                                                  i));
            namedParameters.addValue(String.format("fieldTypeId%d", i),
                                     topicSearchFieldTypeValuesRequestDTO.getFieldTypeId());
            namedParameters.addValue(String.format("valueNumberFrom%d", i),
                                     topicSearchFieldTypeValuesRequestDTO.getValueNumFrom());
            namedParameters.addValue(String.format("valueNumberTo%d", i),
                                     topicSearchFieldTypeValuesRequestDTO.getValueNumTo());
          }
          else {
            sqlStringBuilder.append(String.format(" and t in (select tv.topic from TopicValue tv where tv.theme.themeId=:themeId and tv.fieldType.fieldTypeId=:fieldTypeId%d and tv.valueNumber >= :valueNumberFrom%d",
                                                  i,
                                                  i));
            namedParameters.addValue(String.format("fieldTypeId%d", i),
                                     topicSearchFieldTypeValuesRequestDTO.getFieldTypeId());
            namedParameters.addValue(String.format("valueNumberFrom%d", i),
                                     topicSearchFieldTypeValuesRequestDTO.getValueNumFrom());
          }
        }
        else if (topicSearchFieldTypeValuesRequestDTO.getValueNumTo() != null
                 && topicSearchFieldTypeValuesRequestDTO.getValueNumTo() > 0)
        {
          sqlStringBuilder.append(String.format(" and t in (select tv.topic from TopicValue tv where tv.theme.themeId=:themeId and tv.fieldType.fieldTypeId=:fieldTypeId%d and tv.valueNumber <= :valueNumberTo%d",
                                                i,
                                                i));
          namedParameters.addValue(String.format("fieldTypeId%d", i),
                                   topicSearchFieldTypeValuesRequestDTO.getFieldTypeId());
          namedParameters.addValue(String.format("valueNumberTo%d", i),
                                   topicSearchFieldTypeValuesRequestDTO.getValueNumTo());
        }

        topicSearchFieldTypeValuesRequestDTO.getValueNumTo();

        sqlStringBuilder.append(" and t in (select tv.topic from TopicValue tv where tv.theme.themeId=:themeId and tv.fieldType.fieldTypeId=:fieldTypeId%d and tv.valueNumber between :valueNumberFrom%d and :valueNumberTo%d");
        break;
      }

      case BOOLEAN: {
        sqlStringBuilder.append(String.format(" and t in (select tv.topic from TopicValue tv where tv.theme.themeId=:themeId and tv.fieldType.fieldTypeId=:fieldTypeId%d and tv.valueBoolean=:valueBoolean%d)",
                                              i,
                                              i));
        namedParameters.addValue(String.format("fieldTypeId%d", i),
                                 topicSearchFieldTypeValuesRequestDTO.getFieldTypeId());
        namedParameters.addValue(String.format("valueBoolean%d", i),
                                 topicSearchFieldTypeValuesRequestDTO.getValueBoolean());
        break;
      }

      default:
        break;
    }
  }

  private TopicSearchColumnHeaderResponseDTO toTopicSearchColumnHeaderResponseDTO(FieldTypeResponseDTO fieldTypeResponseDTO) {
    TopicSearchColumnHeaderResponseDTO result = new TopicSearchColumnHeaderResponseDTO();
    result.setFieldTypeId(fieldTypeResponseDTO.getFieldTypeId());
    result.setDescription(fieldTypeResponseDTO.getDescription());
    result.setShortDescription(fieldTypeResponseDTO.getShortDescription());
    return result;
  }

  private TopicSearchResponseDTO toTopicSearchResponseDTO(Topic topic) {
    TopicSearchResponseDTO result = new TopicSearchResponseDTO();
    result.setTopicId(topic.getTopicId());

    result.setTopicValues(loadTopic(topic.getTopicId()).getTopicValues()
                                                       .stream()
                                                       .map(e -> toTopicSearchValueResponseDTO(topic.getTopicId(), e))
                                                       .collect(Collectors.toList()));

    return result;
  }

  private TopicSearchValueResponseDTO toTopicSearchValueResponseDTO(Long topicId,
                                                                    TopicValueLoadResponseDTO topicValueLoadResponseDTO)
  {
    TopicSearchValueResponseDTO result = new TopicSearchValueResponseDTO();
    result.setFieldTypeDefinitionDescription(topicValueLoadResponseDTO.getFieldTypeDefinitionDescription());
    result.setFieldTypeDefinitionId(topicValueLoadResponseDTO.getFieldTypeDefinitionId());
    result.setFieldTypeId(topicValueLoadResponseDTO.getFieldTypeId());
    result.setTopicId(topicId);
    result.setValueBoolean(null);
    result.setValueDate(null);
    result.setValueNum(null);
    result.setValueVarchar(null);
    return result;
  }
}
