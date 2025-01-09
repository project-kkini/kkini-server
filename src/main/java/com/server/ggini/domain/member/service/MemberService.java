package com.server.ggini.domain.member.service;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.domain.OauthInfo;
import com.server.ggini.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member getMemberByOAuthInfo(OauthInfo oauthInfo) {
        return memberRepository.findByOauthInfo(oauthInfo)
                .orElseGet(() -> createMember(oauthInfo));
    }

    private Member createMember(OauthInfo oauthInfo) {
        String nickname;
        do {
            nickname = Member.createNickname();
        } while (memberRepository.findByNickname(nickname).isPresent());

        Member member = Member.createDefaultMember(nickname, oauthInfo);
        return memberRepository.save(member);
    }

}
