package ch.akros.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ch.akros.marketplace.entity.FieldType;

@Repository
public interface FieldTypeRepository extends JpaRepository<FieldType, Long> {
	@Query("select ft from fieldType ft where ft.theme.themeId = :themeId and ft.searchable=true order by sortNumber")
	List<FieldType> listFieldTypesOfTheme(Long themeId);
}
