package com.server.ggini.domain.review.domain;

import com.server.ggini.global.error.exception.ErrorCode;
import com.server.ggini.global.error.exception.InvalidValueException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    private static final int MAX_LENGTH = 100;
    @Column(name = "content")
    private String content;


    public Content(String content) {
        validate(content);
        this.content = content;
    }

    private void validate(String content) {
        if (content.length() > MAX_LENGTH) {
            throw new InvalidValueException(ErrorCode.INVALID_LENGTH_VALUE);
        }
    }
}
