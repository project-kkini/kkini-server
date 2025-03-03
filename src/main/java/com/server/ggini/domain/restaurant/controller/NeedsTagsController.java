package com.server.ggini.domain.restaurant.controller;

import com.server.ggini.domain.restaurant.dto.response.NeedsTagGetResponse;
import com.server.ggini.domain.restaurant.repository.NeedsTagRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "식당", description = "식당 관련 API")
@RestController
@RequestMapping("/api/v1/needsTags")
@RequiredArgsConstructor
public class NeedsTagsController {

    private final NeedsTagRepository needsTagsRepository;

    @GetMapping("")
    @Operation(summary = "이런 상황에서 이 식당을 찾아요 태그 목록 조회")
    public ResponseEntity<List<NeedsTagGetResponse>> getNeedsTagsAll() {
        List<NeedsTagGetResponse> responses = needsTagsRepository.findAll().stream()
                .map(NeedsTagGetResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }
}
