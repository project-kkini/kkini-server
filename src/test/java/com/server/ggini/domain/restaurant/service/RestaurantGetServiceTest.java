package com.server.ggini.domain.restaurant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.restaurant.domain.NeedsTag;
import com.server.ggini.domain.restaurant.domain.PriceTag;
import com.server.ggini.domain.restaurant.domain.Restaurant;
import com.server.ggini.domain.restaurant.dto.response.RestaurantGetDetailsResponse;
import com.server.ggini.domain.restaurant.repository.NeedsTagRepository;
import com.server.ggini.domain.restaurant.repository.PriceTagsRepository;
import com.server.ggini.domain.restaurant.repository.RestaurantRepository;
import com.server.ggini.domain.review.domain.Review;
import com.server.ggini.domain.review.repository.ReviewRepository;
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
class RestaurantGetServiceIntegrationTest {

    @Autowired
    private RestaurantGetService restaurantGetService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private NeedsTagRepository needsTagRepository;

    @Autowired
    private PriceTagsRepository priceTagsRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Member testMember;
    private Long validRestaurantId = 1L;

    @BeforeEach
    void setUp() {
        testMember = TestMemberGenerator.createTestMember(1L, "testUser", "test@example.com", "profile.png");
    }

    @Test
    void getRestaurant_성공적으로_레스토랑_상세정보를_가져오는지_확인() {
        // Given
        Restaurant restaurant = restaurantRepository.findByIdElseThrow(validRestaurantId);
        List<NeedsTag> needsTags = needsTagRepository.findAllByIdIn(restaurant.getNeedsTagIds());
        List<PriceTag> priceTags = priceTagsRepository.findAllByIdIn(restaurant.getPriceTagIds());
        List<Review> allReviews = reviewRepository.findAllByRestaurantId(validRestaurantId);
        Review myReview = reviewRepository.findByReviewerIdAndRestaurantIdElseNull(testMember.getId(), validRestaurantId);

        // When
        RestaurantGetDetailsResponse response = restaurantGetService.getRestaurant(validRestaurantId, testMember);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo(restaurant.getName());
        assertThat(response.needsTags()).hasSameSizeAs(needsTags);
        assertThat(response.priceTags()).hasSameSizeAs(priceTags);
        assertThat(response.reviews()).hasSameSizeAs(allReviews);

        assertThat(response.myReview()).isNotNull();
        assertThat(response.myReview().id()).isEqualTo(myReview.getId());
    }

    @Nested
    class 실패_케이스_테스트 {

        @Test
        void 존재하지_않는_restaurantId를_입력하면_예외발생() {
            Long invalidRestaurantId = 9999L;

            assertThatThrownBy(() -> restaurantGetService.getRestaurant(invalidRestaurantId, testMember))
                    .isInstanceOf(NotFoundException.class);
        }

        @Test
        void 현재_사용자가_작성한_리뷰가_없을때_null이_반환되는지_확인() {
            // Given: 해당 사용자가 리뷰를 남기지 않음
            testMember = TestMemberGenerator.createTestMember(1000L, "testUser", "test@example.com", "profile.png");

            // When
            RestaurantGetDetailsResponse response = restaurantGetService.getRestaurant(validRestaurantId, testMember);

            // Then
            assertThat(response.myReview()).isNull();
        }
    }
}
