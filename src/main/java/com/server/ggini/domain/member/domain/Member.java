package com.server.ggini.domain.member.domain;

import com.server.ggini.domain.member.domain.nickName.Adjective;
import com.server.ggini.domain.member.domain.nickName.Animal;
import com.server.ggini.global.common.BaseEntity;
import com.server.ggini.global.common.Coordinate;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Embedded
    private OauthInfo oauthInfo;

    @Embedded
    private CompanyLocation companyLocation;

    @Builder
    public Member(String nickname, String email, String password, MemberRole role, OauthInfo oauthInfo, CompanyLocation companyLocation) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.oauthInfo = oauthInfo;
        this.companyLocation = companyLocation;
    }

    public boolean isMatchingPassword(String password) {
        return this.password.equals(password);
    }

    public static String createNickname() {

        return Adjective.getRandomName() + Animal.getRandomName();
    }

    public static Member createDefaultMember(String nickname, OauthInfo oauthInfo) {
        return Member.builder()
                .nickname(nickname)
                .role(MemberRole.USER)
                .oauthInfo(oauthInfo)
                .build();
    }

    public void updateCompanyLocation(Double latitude, Double longitude, Long stationId) {
        Coordinate coordinate = new Coordinate(latitude, longitude);
        this.companyLocation = new CompanyLocation(coordinate, stationId);
    }
}
