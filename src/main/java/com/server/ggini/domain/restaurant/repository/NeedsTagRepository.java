package com.server.ggini.domain.restaurant.repository;

import com.server.ggini.domain.restaurant.domain.NeedsTag;
import com.server.ggini.global.error.exception.NotFoundException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NeedsTagRepository extends JpaRepository<NeedsTag, Long> {

    List<NeedsTag> findAllByIdIn(List<Long> ids);


    @Query("SELECT COUNT(n) FROM NeedsTag n WHERE n.id IN :ids")
    long countByIdIn(@Param("ids") List<Long> ids);

    default void existsByIdsElseThrow(List<Long> needsTagIds) {
        long count = countByIdIn(needsTagIds);

        if (count != needsTagIds.size()) {
            throw new NotFoundException("일부 NeedsTag가 존재하지 않습니다.");
        }
    }
}
