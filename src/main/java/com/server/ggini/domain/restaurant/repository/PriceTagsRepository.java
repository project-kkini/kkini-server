package com.server.ggini.domain.restaurant.repository;

import com.server.ggini.domain.restaurant.domain.PriceTag;
import com.server.ggini.global.error.exception.NotFoundException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PriceTagsRepository extends JpaRepository<PriceTag, Long> {

    List<PriceTag> findAllByIdIn(List<Long> ids);


    @Query("SELECT COUNT(p) FROM PriceTag p WHERE p.id IN :ids")
    long countByIdIn(@Param("ids") List<Long> ids);

    default void existsByIdsElseThrow(List<Long> priceTagIds) {
        long count = countByIdIn(priceTagIds);

        if (count != priceTagIds.size()) {
            throw new NotFoundException("일부 PriceTag가 존재하지 않습니다.");
        }
    }
}
