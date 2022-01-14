package ch.akros.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.akros.marketplace.entity.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
