package com.server.ggini.domain.restaurant.domain;

import com.server.ggini.domain.restaurant.repository.converter.RestaurantImageConverter;
import com.server.ggini.global.common.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseEntity {

    @Id
    @Column(name = "restaurant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude", column = @Column(name = "latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "longitude"))
    })
    private Coordinate coordinate;

    @Embedded
    private Address address;

    @Convert(converter = RestaurantImageConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<RestaurantImage> restaurantImage;

    private Long seoulRestaurantId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_category_id")
    private Category menuCategory;

    @ElementCollection
    @CollectionTable(name = "restaurant_needs_tag", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Long> needsTagIds;

    @ElementCollection
    @CollectionTable(name = "restaurant_price_tag", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Long> priceTagIds;

    @Builder
    public Restaurant(String name, Coordinate coordinate, Address address, Long seoulRestaurantId,
                      Category menuCategory,
                      List<Long> needsTagIds, List<Long> priceTagIds) {
        this.name = name;
        this.coordinate = coordinate;
        this.address = address;
        this.seoulRestaurantId = seoulRestaurantId;
        this.menuCategory = menuCategory;
        this.needsTagIds = needsTagIds;
        this.priceTagIds = priceTagIds;
    }

    public static Restaurant of(SeoulConfirmRestaurant seoulConfirmRestaurant, Category category, List<Long> needsTagIds, List<Long> priceTagIds) {
        return Restaurant.builder()
                .name(seoulConfirmRestaurant.getName())
                .address(new Address(seoulConfirmRestaurant.getAddress(), seoulConfirmRestaurant.getRoadAddress()))
                .coordinate(new Coordinate(seoulConfirmRestaurant.getLatitude(), seoulConfirmRestaurant.getLongitude()))
                .seoulRestaurantId(seoulConfirmRestaurant.getId())
                .menuCategory(category)
                .needsTagIds(needsTagIds)
                .priceTagIds(priceTagIds)
                .build();
    }
}
