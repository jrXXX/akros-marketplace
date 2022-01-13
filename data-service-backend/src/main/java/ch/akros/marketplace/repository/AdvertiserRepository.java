package ch.akros.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.akros.marketplace.entity.Advertiser;

@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser, Long> {
}
