package ch.akros.marketplace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ch.akros.marketplace.api.DeleteTopicApi;
import ch.akros.marketplace.service.TopicService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class DeleteTopicController implements DeleteTopicApi {
	@Autowired
	private TopicService topicService;

	@Override
	public ResponseEntity<Void> deleteTopicTopicIdGet(Long topicId) {
		try {
			log.debug("SaveTopicController.saveTopicPost() called");

			topicService.deleteTopic(topicId);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
