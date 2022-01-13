package ch.akros.marketplace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ch.akros.marketplace.api.ListThemesApi;
import ch.akros.marketplace.api.model.VWThemeResponseDTO;
import ch.akros.marketplace.service.ThemeService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ListThemesController implements ListThemesApi {
	@Autowired
	private ThemeService themeService;

	@Override
	public ResponseEntity<List<VWThemeResponseDTO>> listThemesGet() {
		try {
			log.info("listThemesGet() called");

			List<VWThemeResponseDTO> vwThemeResponseDTOList = themeService.listThemes();
			return ResponseEntity.status(HttpStatus.OK).body(vwThemeResponseDTOList);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
