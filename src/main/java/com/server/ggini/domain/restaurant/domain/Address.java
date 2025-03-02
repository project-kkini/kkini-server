package com.server.ggini.domain.restaurant.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String address;
    private String roadAddress;

    public Address(String address, String roadAddress) {
        this.address = address;
        this.roadAddress = roadAddress;
    }
}
