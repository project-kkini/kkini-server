package com.server.ggini.domain.restaurant.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @Column(name = "restaurant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Embedded
    private Coordinate coordinate;

    private Long seoulRestaurantId;

    @OneToOne
    @JoinColumn(name = "menu_category_id")
    private Category menuCategory;

    @ElementCollection
    @CollectionTable(name = "restaurant_needs_tag", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Long> needsTagIds;

    @ElementCollection
    @CollectionTable(name = "restaurant_price_tag", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Long> priceTagIds;

}
