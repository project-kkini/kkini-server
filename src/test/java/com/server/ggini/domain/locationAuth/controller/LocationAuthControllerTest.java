//package com.server.ggini.domain.locationAuth.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.server.ggini.domain.locationAuth.dto.request.LocationAuthRequest;
//import com.server.ggini.domain.locationAuth.dto.response.LocationAuthResponse;
//import com.server.ggini.domain.locationAuth.service.LocationAuthService;
//import com.server.ggini.domain.member.domain.CompanyLocation;
//import com.server.ggini.domain.member.domain.Member;
//import com.server.ggini.domain.member.domain.MemberRole;
//import com.server.ggini.domain.member.domain.OauthInfo;
//import com.server.ggini.domain.member.repository.MemberRepository;
//import com.server.ggini.global.annotation.AuthenticationArgumentResolver;
//import com.server.ggini.global.error.GlobalExceptionHandler;
//import com.server.ggini.global.properties.jwt.JwtProperties;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import java.security.Key;
//import java.util.Date;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import java.util.Optional;
//import org.springframework.web.context.WebApplicationContext;
//
//@Transactional
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@ExtendWith(MockitoExtension.class)
//class LocationAuthControllerTest {
//
//    private ObjectMapper objectMapper;
//
//    @Mock
//    private LocationAuthService locationAuthService;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @InjectMocks
//    private LocationAuthController locationAuthController;
//
//    @Autowired
//    private JwtProperties jwtProperties;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private String validToken;
//    private Key key;
//
//    @BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity()).build();
//
//        key = Keys.hmacShaKeyFor(jwtProperties.accessTokenSecret().getBytes());
//        Date issuedAt = new Date();
//        Date expiredAt = new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());
//        validToken = Jwts.builder()
//                .setIssuer(jwtProperties.issuer())
//                .setSubject("1")
//                .claim("role", "ROLE_USER")
//                .setIssuedAt(issuedAt)
//                .setExpiration(expiredAt)
//                .signWith(key)
//                .compact();
//    }
//
//    @Test
//    @DisplayName("지역 인증을 성공적으로 수행한다.")
//    void authenticateLocation_InSupportedArea_Success() throws Exception {
//        // given
//        double latitude = 37.4979;  // 강남 지역 위도
//        double longitude = 127.0276; // 강남 지역 경도
//        LocationAuthRequest request = new LocationAuthRequest(latitude, longitude);
//        LocationAuthResponse response = new LocationAuthResponse(1L, "강남역");
//
//
//        // when
//        given(locationAuthService.authenticateLocation(any(Member.class), any(LocationAuthRequest.class)))
//                .willReturn(response);
//
//        // then
//        mockMvc.perform(post("/api/v1/locationAuth")
//                        .header("Authorization", "Bearer " + validToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.subwayStationId").value(1L))
//                .andExpect(jsonPath("$.subwayStationName").value("강남역"));
//    }
//
//    @Test
//    @DisplayName("유효하지 않은 요청 데이터는 400 에러를 반환한다")
//    void authenticateLocation_InvalidRequest() throws Exception {
//        // given - null 값을 포함한 요청 데이터
//        String invalidRequest = "{\"latitude\": null, \"longitude\": 127.0276}";
//
//        // then
//        mockMvc.perform(post("/api/v1/locationAuth")
//                        .header("Authorization", "Bearer " + validToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidRequest))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//}