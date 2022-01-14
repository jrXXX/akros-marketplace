
package ch.akros.marketplace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ch.akros.marketplace.api.SearchTopicApi;
import ch.akros.marketplace.api.model.TopicSearchFieldTypeValuesRequestDTO;
import ch.akros.marketplace.api.model.TopicSearchListResponseDTO;
import ch.akros.marketplace.service.TopicService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class SearchTopicController implements SearchTopicApi {
  @Autowired
  private TopicService topicService;

  @Override
  public ResponseEntity<TopicSearchListResponseDTO> searchTopicPost(List<TopicSearchFieldTypeValuesRequestDTO> topicSearchFieldTypeValuesRequestDTO) {
    try {
      log.debug("SaveTopicController.saveTopicPost() called");

      TopicSearchListResponseDTO topicSearchListResponseDTO = topicService.searchTopic(topicSearchFieldTypeValuesRequestDTO);
      return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
