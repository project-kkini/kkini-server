package com.server.ggini.domain.restaurant.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeoulConfirmRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String managementNumber;
    private int statusCode;
    private Long districtCode;
    private String address;
    private String roadAddress;
    private String name;
    private String category;
    private Double latitude;
    private Double longitude;
}
