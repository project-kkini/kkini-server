package com.server.ggini.domain.restaurant.repository;

import com.server.ggini.domain.restaurant.domain.PriceTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceTagsRepository extends JpaRepository<PriceTag, Long> {
}
