package com.server.ggini.domain.auth.domain;

import com.server.ggini.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RefreshToken extends BaseEntity {

    @Id
    @Column(nullable = false, name = "refresh_token_id")
    private Long memberId;
    @Column(nullable = false)
    private String token;

    @Builder
    public RefreshToken(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }
}
