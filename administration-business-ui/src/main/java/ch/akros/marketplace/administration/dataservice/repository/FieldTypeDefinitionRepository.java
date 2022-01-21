
package ch.akros.marketplace.administration.dataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.akros.marketplace.administration.dataservice.entity.FieldTypeDefinition;

@Repository
public interface FieldTypeDefinitionRepository extends JpaRepository<FieldTypeDefinition, Integer> {}
