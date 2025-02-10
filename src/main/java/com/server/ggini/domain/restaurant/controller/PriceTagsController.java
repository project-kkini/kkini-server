package com.server.ggini.domain.restaurant.controller;

import com.server.ggini.domain.restaurant.dto.response.PriceTagGetResponse;
import com.server.ggini.domain.restaurant.repository.PriceTagsRepository;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/priceTags")
@RequiredArgsConstructor
public class PriceTagsController {

    private final PriceTagsRepository priceTagsRepository;

    @GetMapping("")
    @Operation(summary = "가격 태그 목록 조회")
    public ResponseEntity<List<PriceTagGetResponse>> getPriceTagAll() {
        List<PriceTagGetResponse> responses = priceTagsRepository.findAll().stream()
                .map(PriceTagGetResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
