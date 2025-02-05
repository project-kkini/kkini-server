package com.server.ggini.domain.restaurant.domain;

import jakarta.persistence.Column;
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
public class PriceTag {

    @Id
    @Column(name = "price_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
}
