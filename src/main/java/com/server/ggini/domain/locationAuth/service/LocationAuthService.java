package com.server.ggini.domain.locationAuth.service;

import com.server.ggini.domain.locationAuth.dto.IsSupportedAndSubwayStationDto;
import com.server.ggini.domain.locationAuth.dto.SubwayStationDto;
import com.server.ggini.domain.locationAuth.dto.request.LocationAuthRequest;
import com.server.ggini.domain.locationAuth.dto.response.LocationAuthResponse;
import com.server.ggini.domain.locationAuth.repository.SubwayStationRepository;
import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.repository.MemberRepository;
import com.server.ggini.global.error.exception.ErrorCode;
import com.server.ggini.global.error.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationAuthService {
    private final SubwayStationRepository subwayStationRepository;
    private final MemberRepository memberRepository;
    public static final Long NOT_SUPPORTED_AREA_STATION_ID = -1L; // 클래스 수준의 상수

    @Transactional
    public LocationAuthResponse authenticateLocation(Member member, LocationAuthRequest locationAuthRequest) {
        // 지원하는 지역인지, 맞다면 가까운역 찾기
        SubwayStationDto nearestSubwayStation = findSubwayStationByLocation(locationAuthRequest.latitude(), locationAuthRequest.longitude());

        // 회원 위치 정보 업데이트
        member.updateCompanyLocation(locationAuthRequest.latitude(), locationAuthRequest.longitude(), nearestSubwayStation.subwayStationId());
        memberRepository.save(member);

        return new LocationAuthResponse(nearestSubwayStation.subwayStationName());
    }

    private SubwayStationDto findSubwayStationByLocation(double latitude, double longitude) {
        // 강남, 서초지역인지 확인
        boolean isSupportedArea = GeoJsonUtil.checkLocation(latitude, longitude);

        if (isSupportedArea) {
            // 강남/서초 지역이라면 가장 가까운 역 조회
            return subwayStationRepository.findNearestStation(latitude, longitude)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.SUBWAY_STATION_NOT_FOUND));
        }

        return new SubwayStationDto(NOT_SUPPORTED_AREA_STATION_ID, "지원하지 않는 지역입니다.");
    }

}
