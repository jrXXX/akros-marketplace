package ch.akros.marketplace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ch.akros.marketplace.api.ListFieldTypesApi;
import ch.akros.marketplace.api.model.FieldTypeResponseDTO;
import ch.akros.marketplace.service.ThemeService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ListFieldTypesController implements ListFieldTypesApi {
	@Autowired
	private ThemeService themeService;

	@Override
	public ResponseEntity<List<FieldTypeResponseDTO>> listFieldTypesThemeIdGet(Integer themeId) {
		try {
			log.info("listFieldTypesthemeIdGet() called");

			List<FieldTypeResponseDTO> fieldTypeResponseList = themeService.listFieldTypes((long) themeId);
			return ResponseEntity.status(HttpStatus.OK).body(fieldTypeResponseList);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
