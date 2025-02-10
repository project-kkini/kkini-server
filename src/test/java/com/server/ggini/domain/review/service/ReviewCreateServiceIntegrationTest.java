package com.server.ggini.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.review.domain.Review;
import com.server.ggini.domain.review.dto.request.ReviewPostRequest;
import com.server.ggini.domain.review.repository.ReviewRepository;
import com.server.ggini.global.error.exception.InvalidValueException;
import com.server.ggini.global.error.exception.NotFoundException;
import com.server.ggini.global.security.utils.TestMemberGenerator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@Sql({"/auth-test-data.sql", "/menuCategory-test-data.sql", "/needsTag-test-data.sql", "/priceTag-test-data.sql", "/restaurant-test-data.sql", "/review-test-data.sql"})
@SpringBootTest
class ReviewCreateServiceIntegrationTest {

    @Autowired
    private ReviewCreateService reviewCreateService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Member testMember;
    private ReviewPostRequest reviewPostRequest;

    @BeforeEach
    void setUp() {
        testMember = TestMemberGenerator.createTestMember(1000L, "testUser", "test@example.com", "profile.png");

        reviewPostRequest = new ReviewPostRequest(
                "음식이 정말 맛있어요!",
                List.of(1L, 2L),
                List.of(1L, 2L, 3L),
                List.of("https://reviewimage1.com", "https://reviewimage2.com")
        );
    }

    @Test
    void createReview_성공적으로_저장되는지_확인() {
        // Given
        Long restaurantId = 1L;

        // When
        Long savedRestaurantId = reviewCreateService.createReview(restaurantId, reviewPostRequest, testMember);

        // Then
        Restaurant savedRestaurant = restaurantRepository.findById(savedRestaurantId).orElseThrow();
        assertThat(savedRestaurant.getId()).isEqualTo(restaurantId);

        List<Review> savedReviews = reviewRepository.findAllByRestaurantId(restaurantId);
        assertThat(savedReviews).hasSize(5);

        Review savedReview = savedReviews.get(4);
        assertThat(savedReview.getContent()).isEqualTo("음식이 정말 맛있어요!");
        assertThat(savedReview.getRestaurantId()).isEqualTo(restaurantId);
        assertThat(savedReview.getReviewer().getId()).isEqualTo(testMember.getId());

        assertThat(savedReview.getImageUrls()).hasSize(2);
        assertThat(savedReview.getImageUrls().get(0).getUrl()).isEqualTo("https://reviewimage1.com");
        assertThat(savedReview.getImageUrls().get(1).getUrl()).isEqualTo("https://reviewimage2.com");

        assertThat(savedReview.getNeedsTagIds()).containsExactlyInAnyOrder(1L, 2L);
        assertThat(savedReview.getPriceTagIds()).containsExactlyInAnyOrder(1L, 2L, 3L);

        assertThat(savedReview.isFirstReview()).isFalse();

    }

    @Nested
    class 실패_케이스_테스트 {

        @Test
        void 존재하지_않는_restaurantId를_입력하면_예외발생() {
            Long invalidRestaurantId = 9999L;

            assertThatThrownBy(() -> reviewCreateService.createReview(invalidRestaurantId, reviewPostRequest, testMember))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 존재하지_않는_needsTagIds를_입력하면_예외발생() {
            ReviewPostRequest invalidRequest = new ReviewPostRequest(
                    "음식이 정말 맛있어요!",
                    List.of(999L, 1000L),
                    List.of(1L, 2L, 3L),
                    List.of("https://reviewimage1.com", "https://reviewimage2.com")
            );

            assertThatThrownBy(() -> reviewCreateService.createReview(1L, invalidRequest, testMember))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 존재하지_않는_priceTagIds를_입력하면_예외발생() {
            ReviewPostRequest invalidRequest = new ReviewPostRequest(
                    "음식이 정말 맛있어요!",
                    List.of(1L, 2L),
                    List.of(999L, 1000L),
                    List.of("https://reviewimage1.com", "https://reviewimage2.com")
            );

            assertThatThrownBy(() -> reviewCreateService.createReview(1L, invalidRequest, testMember))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 리뷰_내용이_100자를_넘으면_예외발생() {
            String longContent = "이 리뷰는 100자를 초과합니다. ".repeat(10);

            ReviewPostRequest invalidRequest = new ReviewPostRequest(
                    longContent,
                    List.of(1L, 2L),
                    List.of(1L, 2L, 3L),
                    List.of("https://reviewimage1.com", "https://reviewimage2.com")
            );

            assertThatThrownBy(() -> reviewCreateService.createReview(1L, invalidRequest, testMember))
                    .isInstanceOf(InvalidValueException.class);
        }

        @Test
        void 이미_리뷰를_남긴_사용자가_또_리뷰를_남기면_예외발생() {
            Long restaurantId = 1L;

            // Given: 첫 번째 리뷰 정상 생성
            reviewCreateService.createReview(restaurantId, reviewPostRequest, testMember);

            // When & Then: 동일한 사용자가 같은 restaurant에 리뷰를 또 남기면 예외 발생
            assertThatThrownBy(() -> reviewCreateService.createReview(restaurantId, reviewPostRequest, testMember))
                    .isInstanceOf(InvalidValueException.class);
        }
    }
}
