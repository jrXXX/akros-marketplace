package ch.akros.marketplace.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ch.akros.marketplace.api.model.FieldTypeDefinitionDTO;
import ch.akros.marketplace.entity.FieldTypeDefinition;
import ch.akros.marketplace.repository.FieldTypeDefinitionRepository;

@Service
public class FieldTypeDefinitionService {
	@Autowired
	private FieldTypeDefinitionRepository fieldTypeDefinitionRepository;

	public List<FieldTypeDefinitionDTO> listFieldTypeDefinitions() {
		return fieldTypeDefinitionRepository.findAll(Sort.by(Sort.Direction.ASC, "fieldTypeDefinitionId")).stream()
				.map(this::toFieldTypeDefinitionDTO).collect(Collectors.toList());
	}

	// TODO : mapper
	private FieldTypeDefinitionDTO toFieldTypeDefinitionDTO(FieldTypeDefinition fieldTypeDefinition) {
		FieldTypeDefinitionDTO result = new FieldTypeDefinitionDTO();
		result.setFieldTypeDefinitionId(fieldTypeDefinition.getFieldTypeDefinitionId());
		result.setDescription(fieldTypeDefinition.getDescription());
		return result;
	}
}
