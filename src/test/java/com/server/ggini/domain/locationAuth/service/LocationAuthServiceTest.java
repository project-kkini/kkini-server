package com.server.ggini.domain.locationAuth.service;

import com.server.ggini.domain.locationAuth.dto.SubwayStationDto;
import com.server.ggini.domain.locationAuth.dto.request.LocationAuthRequest;
import com.server.ggini.domain.locationAuth.dto.response.LocationAuthResponse;
import com.server.ggini.domain.locationAuth.repository.SubwayStationRepository;
import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.repository.MemberRepository;
import com.server.ggini.global.error.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationAuthServiceTest {

    @Mock
    private SubwayStationRepository subwayStationRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Member mockMember;

    @InjectMocks
    private LocationAuthService locationAuthService;

    private LocationAuthRequest supportedAreaRequest;
    private LocationAuthRequest unsupportedAreaRequest;
    private SubwayStationDto subwayStationDto;

    @BeforeEach
    void setUp() {
        // 지원 지역 내의 요청 객체 생성 (강남/서초 지역의 좌표)
        supportedAreaRequest = new LocationAuthRequest(37.4979, 127.0276);

        // 지원 지역 외의 요청 객체 생성
        unsupportedAreaRequest = new LocationAuthRequest(37.5665, 126.9780);

        // 테스트용 지하철역 DTO 생성
        subwayStationDto = new SubwayStationDto(1L, "강남역");

        // mockMember는 @Mock 어노테이션으로 이미 생성됨
    }

    @Test
    @DisplayName("지원 지역 내 위치 인증 성공 테스트")
    void authenticateLocationInSupportedAreaTest() {
        // Given
        try (MockedStatic<GeoJsonUtil> mockedGeoJsonUtil = mockStatic(GeoJsonUtil.class)) {
            // GeoJsonUtil.checkLocation이 true를 반환하도록 설정 (강남/서초 지역)
            mockedGeoJsonUtil.when(() -> GeoJsonUtil.checkLocation(anyDouble(), anyDouble())).thenReturn(true);

            // 가장 가까운 지하철역을 반환하도록 설정
            when(subwayStationRepository.findNearestStation(anyDouble(), anyDouble()))
                    .thenReturn(Optional.of(subwayStationDto));

            // When
            LocationAuthResponse response = locationAuthService.authenticateLocation(mockMember, supportedAreaRequest);

            // Then
            assertThat(response.subwayStationId()).isNotEqualTo(-1);
            assertThat(response.subwayStationName()).isEqualTo("강남역");

            // memberRepository.save()가 호출되었는지 확인
            verify(memberRepository, times(1)).save(mockMember);

            // mock Member 객체의 updateCompanyLocation 메서드가 호출되었는지 확인
            verify(mockMember).updateCompanyLocation(
                    supportedAreaRequest.latitude(),
                    supportedAreaRequest.longitude(),
                    subwayStationDto.subwayStationId()
            );
        }
    }

    @Test
    @DisplayName("지원하지 않는 지역 위치 인증 테스트")
    void authenticateLocationInUnsupportedAreaTest() {
        // Given
        try (MockedStatic<GeoJsonUtil> mockedGeoJsonUtil = mockStatic(GeoJsonUtil.class)) {
            // GeoJsonUtil.checkLocation이 false를 반환하도록 설정 (강남/서초 지역이 아님)
            mockedGeoJsonUtil.when(() -> GeoJsonUtil.checkLocation(anyDouble(), anyDouble())).thenReturn(false);

            // When
            LocationAuthResponse response = locationAuthService.authenticateLocation(mockMember, unsupportedAreaRequest);

            // Then
            assertThat(response.subwayStationId()).isEqualTo(-1);
            assertThat(response.subwayStationName()).isEqualTo("지원하지 않는 지역입니다.");

            // memberRepository.save()가 호출되었는지 확인
            verify(memberRepository, times(1)).save(mockMember);

            // mock Member 객체의 updateCompanyLocation 메서드가 호출되었는지 확인
            verify(mockMember).updateCompanyLocation(
                    unsupportedAreaRequest.latitude(),
                    unsupportedAreaRequest.longitude(),
                    LocationAuthService.NOT_SUPPORTED_AREA_STATION_ID
            );
        }
    }

    @Test
    @DisplayName("지원 지역 내에서 가까운 지하철역을 찾을 수 없을 때 예외 발생 테스트")
    void authenticateLocationWithNoNearestStationTest() {
        // Given
        try (MockedStatic<GeoJsonUtil> mockedGeoJsonUtil = mockStatic(GeoJsonUtil.class)) {
            // GeoJsonUtil.checkLocation이 true를 반환하도록 설정 (강남/서초 지역)
            mockedGeoJsonUtil.when(() -> GeoJsonUtil.checkLocation(anyDouble(), anyDouble())).thenReturn(true);

            // 가장 가까운 지하철역을 찾을 수 없도록 설정
            when(subwayStationRepository.findNearestStation(anyDouble(), anyDouble()))
                    .thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class, () ->
                    locationAuthService.authenticateLocation(mockMember, supportedAreaRequest)
            );
        }
    }

}