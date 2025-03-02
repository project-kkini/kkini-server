package com.server.ggini.domain.locationAuth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.ggini.domain.locationAuth.dto.request.LocationAuthRequest;
import com.server.ggini.domain.locationAuth.dto.response.LocationAuthResponse;
import com.server.ggini.domain.locationAuth.service.LocationAuthService;
import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.global.error.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LocationAuthControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Member mockMember;

    @Mock
    private LocationAuthService locationAuthService;

    @InjectMocks
    private LocationAuthController locationAuthController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        // MockMvc 설정 - @AuthUser 어노테이션을 처리할 수 있는 ArgumentResolver 추가
        mockMvc = MockMvcBuilders.standaloneSetup(locationAuthController)
                .setControllerAdvice(new GlobalExceptionHandler()) // 전역 예외 처리기 추가
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    @DisplayName("지역 인증을 성공적으로 수행한다.")
    void authenticateLocation_InSupportedArea_Success() throws Exception {
        // given
        double latitude = 37.4979;  // 강남 지역 위도
        double longitude = 127.0276; // 강남 지역 경도
        LocationAuthRequest request = new LocationAuthRequest(latitude, longitude);
        LocationAuthResponse response = new LocationAuthResponse(true, "강남역");

        // when
        given(locationAuthService.authenticateLocation(any(Member.class), any(LocationAuthRequest.class)))
                .willReturn(response);

        // then
        mockMvc.perform(post("/api/v1/locationAuth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSupportedArea").value(true))
                .andExpect(jsonPath("$.subwayStationName").value("강남역"));
    }

    @Test
    @DisplayName("유효하지 않은 요청 데이터는 400 에러를 반환한다")
    void authenticateLocation_InvalidRequest() throws Exception {
        // given - null 값을 포함한 요청 데이터
        String invalidRequest = "{\"latitude\": null, \"longitude\": 127.0276}";

        // then
        mockMvc.perform(post("/api/v1/locationAuth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}