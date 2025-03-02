package com.server.ggini.domain.restaurant.service;

import com.server.ggini.domain.restaurant.domain.Category;
import com.server.ggini.domain.restaurant.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElse(categoryRepository.save(new Category(name)));
    }
}
