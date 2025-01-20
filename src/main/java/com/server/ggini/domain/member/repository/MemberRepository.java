package com.server.ggini.domain.member.repository;

import com.server.ggini.domain.member.domain.Member;
import com.server.ggini.domain.member.domain.OauthInfo;
import com.server.ggini.global.error.exception.ErrorCode;
import com.server.ggini.global.error.exception.NotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthInfo(OauthInfo oauthInfo);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);


    default Member findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    default Member findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
