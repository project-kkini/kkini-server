package com.server.ggini.domain.restaurant.repository;

import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.server.ggini.domain.restaurant.domain.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class RestaurantQueryRepositoryImpl implements RestaurantQueryRepository {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<Restaurant> searchRestaurants(
        List<Long> menuCategoryIds,
        List<Long> priceTagIds,
        List<Long> needsTagIds,
        String cursor,
        int size
    ) {
        return queryFactory
            .selectFrom(restaurant)
            .leftJoin(restaurant.menuCategory).fetchJoin()
            .where(
                menuCategoryIdIn(menuCategoryIds),
                priceTagIdIn(priceTagIds),
                needsTagIdIn(needsTagIds),
                cursorId(decodeCursor(cursor))
            )
            .orderBy(restaurant.id.desc())
            .limit(size)
            .fetch();
    }
    
    @Override
    public Long countSearchResults(
        List<Long> menuCategoryIds,
        List<Long> priceTagIds,
        List<Long> needsTagIds
    ) {
        return queryFactory
            .select(restaurant.count())
            .from(restaurant)
            .where(
                menuCategoryIdIn(menuCategoryIds),
                priceTagIdIn(priceTagIds),
                needsTagIdIn(needsTagIds)
            )
            .fetchOne();
    }
    
    private BooleanExpression menuCategoryIdIn(List<Long> menuCategoryIds) {
        return menuCategoryIds != null && !menuCategoryIds.isEmpty() 
            ? restaurant.menuCategory.id.in(menuCategoryIds)
            : null;
    }
    
    private BooleanExpression priceTagIdIn(List<Long> priceTagIds) {
        return priceTagIds != null && !priceTagIds.isEmpty()
            ? restaurant.priceTagIds.any().in(priceTagIds)
            : null;
    }
    
    private BooleanExpression needsTagIdIn(List<Long> needsTagIds) {
        return needsTagIds != null && !needsTagIds.isEmpty()
            ? restaurant.needsTagIds.any().in(needsTagIds)
            : null;
    }
    
    private BooleanExpression cursorId(Long cursorId) {
        return cursorId != null ? restaurant.id.lt(cursorId) : null;
    }
    
    private Long decodeCursor(String cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            String decoded = new String(Base64.getDecoder().decode(cursor));
            return Long.parseLong(decoded);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
} 