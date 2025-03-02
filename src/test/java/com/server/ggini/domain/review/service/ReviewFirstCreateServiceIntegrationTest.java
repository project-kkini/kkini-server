package com.server.ggini.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.review.domain.Review;
import com.server.ggini.domain.review.dto.request.ReviewPostRequest;
import com.server.ggini.domain.review.repository.ReviewRepository;
import com.server.ggini.global.error.exception.InvalidValueException;
import com.server.ggini.global.error.exception.NotFoundException;
import com.server.ggini.global.security.utils.TestMemberGenerator;
import jakarta.persistence.EntityNotFoundException;
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
@Sql({"/auth-test-data.sql","/menuCategory-test-data.sql", "/needsTag-test-data.sql", "/priceTag-test-data.sql", "/restaurant-test-data.sql", "/review-test-data.sql"})
@SpringBootTest
class ReviewFirstCreateServiceIntegrationTest {

    @Autowired
    private ReviewFirstCreateService reviewFirstCreateService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Member testMember;
    private ReviewPostRequest reviewPostRequest;

    @BeforeEach
    void setUp() {

        testMember = TestMemberGenerator.createTestMember(1L, "testUser", "test@example.com", "profile.png");
        reviewPostRequest = new ReviewPostRequest(
                "음식이 정말 맛있어요!",
                List.of(1L, 2L),
                List.of(1L, 2L, 3L),
                List.of("https://reviewimage1.com", "https://reviewimage2.com")
        );
    }

    @Test
    void createReviewFirst_성공적으로_저장되는지_확인() {
        // Given: 테스트 데이터가 @Sql을 통해 DB에 미리 삽입됨

        // When: 리뷰를 생성
        Long restaurantId = reviewFirstCreateService.createReviewFirst(1011L, reviewPostRequest, testMember);

        // Then: Restaurant이 정상적으로 저장되었는지 확인
        Restaurant savedRestaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        assertThat(savedRestaurant.getName()).isEqualTo("새로 생김"); // restaurant-test-data.sql에 저장된 값과 일치해야 함
        assertThat(savedRestaurant.getSeoulRestaurantId()).isEqualTo(1011L);

        // Then: Review가 정상적으로 저장되었는지 확인
        List<Review> savedReviews = reviewRepository.findAllByRestaurantId(restaurantId);
        assertThat(savedReviews).hasSize(1); // 기존 4개 리뷰 + 새로 추가한 1개

        Review savedReview = savedReviews.get(0);
        assertThat(savedReview.getContent()).isEqualTo("음식이 정말 맛있어요!");
        assertThat(savedReview.getRestaurantId()).isEqualTo(restaurantId);
        assertThat(savedReview.getReviewer().getId()).isEqualTo(testMember.getId());

        assertThat(savedReview.getImageUrls()).hasSize(2);
        assertThat(savedReview.getImageUrls().get(0).getUrl()).isEqualTo("https://reviewimage1.com");
        assertThat(savedReview.getImageUrls().get(1).getUrl()).isEqualTo("https://reviewimage2.com");

        assertThat(savedReview.getNeedsTagIds()).containsExactlyInAnyOrder(1L, 2L);
        assertThat(savedReview.getPriceTagIds()).containsExactlyInAnyOrder(1L, 2L, 3L);

        assertThat(savedReview.isFirstReview()).isTrue();
    }

    @Nested
    class 실패_케이스_테스트 {

        @Test
        void 존재하지_않는_seoulRestaurantId를_입력하면_예외발생() {
            Long invalidSeoulRestaurantId = 9999L; // 존재하지 않는 ID

            assertThatThrownBy(
                    () -> reviewFirstCreateService.createReviewFirst(invalidSeoulRestaurantId, reviewPostRequest,
                            testMember))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 존재하지_않는_needsTagIds를_입력하면_예외발생() {
            ReviewPostRequest invalidRequest = new ReviewPostRequest(
                    "음식이 정말 맛있어요!",
                    List.of(999L, 1000L), // 존재하지 않는 NeedsTag ID
                    List.of(1L, 2L, 3L),
                    List.of("https://reviewimage1.com", "https://reviewimage2.com")
            );

            assertThatThrownBy(() -> reviewFirstCreateService.createReviewFirst(1011L, invalidRequest, testMember))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 존재하지_않는_priceTagIds를_입력하면_예외발생() {
            ReviewPostRequest invalidRequest = new ReviewPostRequest(
                    "음식이 정말 맛있어요!",
                    List.of(1L, 2L),
                    List.of(999L, 1000L), // 존재하지 않는 PriceTag ID
                    List.of("https://reviewimage1.com", "https://reviewimage2.com")
            );

            assertThatThrownBy(() -> reviewFirstCreateService.createReviewFirst(1011L, invalidRequest, testMember))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 리뷰_내용이_100자를_넘으면_예외발생() {
            String longContent = "이 리뷰는 100자를 초과합니다.".repeat(10);

            ReviewPostRequest invalidRequest = new ReviewPostRequest(
                    longContent,
                    List.of(1L, 2L),
                    List.of(1L, 2L, 3L),
                    List.of("https://reviewimage1.com", "https://reviewimage2.com")
            );

            assertThatThrownBy(() -> reviewFirstCreateService.createReviewFirst(1011L, invalidRequest, testMember))
                    .isInstanceOf(InvalidValueException.class);
        }
    }
}