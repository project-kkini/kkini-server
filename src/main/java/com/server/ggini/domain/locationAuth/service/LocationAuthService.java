package com.server.ggini.domain.locationAuth.service;

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
        // 주어진 위도, 경도가 강남/서초인지 확인
        boolean isSupportedArea = GeoJsonUtil.checkLocation(locationAuthRequest.latitude(), locationAuthRequest.longitude());

        SubwayStationDto nearestStation;
        if (isSupportedArea) {
            // case1) 강남, 서초라면
            // 가장 가까운 역 찾기
            nearestStation = subwayStationRepository.findNearestStation(
                            locationAuthRequest.latitude(), locationAuthRequest.longitude())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.SUBWAY_STATION_NOT_FOUND));
        }else{
            // case2) 강남, 서초가 아니라면
            nearestStation = new SubwayStationDto(NOT_SUPPORTED_AREA_STATION_ID, "지원하지 않는 지역입니다.");
        }

        // 위치 정보 업데이트
        member.updateCompanyLocation(locationAuthRequest.latitude(), locationAuthRequest.longitude(), nearestStation.subwayStationId());
        memberRepository.save(member); // TODO: 우리가 커스텀 어노테이션으로 받아오는 Member는 영속성 컨텍스트가 아니죠?

        // 응답 생성
        return new LocationAuthResponse(true, nearestStation.subwayStationName());
    }
}
