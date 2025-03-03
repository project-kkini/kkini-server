package com.server.ggini.global.security.utils;

import com.server.ggini.domain.member.domain.Member;
import java.lang.reflect.Field;

public class TestMemberGenerator {

    public static Member createTestMember(Long id, String nickname, String email, String profileImageUrl) {
        Member member = Member.builder()
                .nickname(nickname)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .build();

        try {
            Field idField = Member.class.getDeclaredField("id");
            idField.setAccessible(true); // private 필드 접근 가능하도록 설정
            idField.set(member, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set id using reflection", e);
        }

        return member;
    }
}
