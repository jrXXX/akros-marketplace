package ch.akros.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.akros.marketplace.entity.VWTheme;

@Repository
public interface VWThemeRepository extends JpaRepository<VWTheme, Long> {
}
