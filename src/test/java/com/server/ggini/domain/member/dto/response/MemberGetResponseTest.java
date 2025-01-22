package com.server.ggini.domain.member.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.domain.OauthInfo;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class MemberGetResponseTest {

    @Test
    void 회원이_OauthInfo를_가질_경우_응답이_정상적으로_생성된다() throws Exception {
        // given
        OauthInfo oauthInfo = OauthInfo.builder()
                .oauthId("test-oauth-id")
                .oauthProvider("kakao")
                .oauthEmail("oauth@example.com")
                .name("Oauth User")
                .build();

        Member member = Member.builder()
                .nickname("testUser")
                .email("default@example.com")
                .oauthInfo(oauthInfo)
                .build();

        // Reflection으로 id 설정
        Field idField = Member.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(member, 1L);

        // when
        MemberGetResponse response = MemberGetResponse.of(member);

        // then
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("testUser", response.nickname());
        assertEquals("oauth@example.com", response.email());
    }

    @Test
    void 회원이_OauthInfo를_가지지_않을_경우_응답이_정상적으로_생성된다() throws Exception {
        // given
        Member member = Member.builder()
                .nickname("testUserNoOauth")
                .email("default@example.com")
                .build();

        // Reflection으로 id 설정
        Field idField = Member.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(member, 2L);

        // when
        MemberGetResponse response = MemberGetResponse.of(member);

        // then
        assertNotNull(response);
        assertEquals(2L, response.id());
        assertEquals("testUserNoOauth", response.nickname());
        assertEquals("default@example.com", response.email());
    }

}