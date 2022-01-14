package ch.akros.marketplace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ch.akros.marketplace.api.ListTopicFieldTypesApi;
import ch.akros.marketplace.api.model.FieldTypeResponseDTO;
import ch.akros.marketplace.service.TopicService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ListTopicFieldTypesController implements ListTopicFieldTypesApi {
	@Autowired
	private TopicService topicService;

	@Override
	public ResponseEntity<List<FieldTypeResponseDTO>> listTopicFieldTypesThemeIdSearchGet(Long themeId, String search) {
		try {
			log.debug("ListTopicFieldTypesController.listFieldTypesthemeIdGet() called");

			List<FieldTypeResponseDTO> fieldTypeResponseList = topicService.listTopicFieldTypes(themeId, search);
			return ResponseEntity.status(HttpStatus.OK).body(fieldTypeResponseList);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
