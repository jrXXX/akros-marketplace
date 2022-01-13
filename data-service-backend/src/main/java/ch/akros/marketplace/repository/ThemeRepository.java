package ch.akros.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ch.akros.marketplace.entity.FieldType;
import ch.akros.marketplace.entity.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
