package com.server.ggini.domain.restaurant.repository.converter;

import com.server.ggini.domain.restaurant.domain.RestaurantImage;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class RestaurantImageConverter implements AttributeConverter<List<RestaurantImage>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<RestaurantImage> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(RestaurantImage::getUrl)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<RestaurantImage> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(DELIMITER))
                .map(RestaurantImage::new)
                .collect(Collectors.toList());
    }
}

