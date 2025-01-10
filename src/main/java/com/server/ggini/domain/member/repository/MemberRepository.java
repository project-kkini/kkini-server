package com.server.ggini.domain.member.repository;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.domain.OauthInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthInfo(OauthInfo oauthInfo);
    Optional<Member> findByNickname(String nickname);

}
