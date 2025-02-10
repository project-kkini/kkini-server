package com.server.ggini.domain.review.domain;

import com.server.ggini.domain.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Reviewer {

    @Column(name = "reviewer_id")
    private Long id;
    @Column(name = "reviewer_nick_name")
    private String nickName;
    @Column(name = "reviewer_profile_image_url")
    private String profileImageUrl;

    public Reviewer(Long id, String nickName, String profileImageUrl) {
        this.id = id;
        this.nickName = nickName;
        this.profileImageUrl = profileImageUrl;
    }

    public static Reviewer from(Member member) {
        return new Reviewer(member.getId(), member.getNickname(), member.getProfileImageUrl());
    }
}
