package com.server.ggini.global.error;

import com.server.ggini.global.error.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    @Schema(description = "에러 메시지", example = "서버 오류, 관리자에게 문의하세요")
    private String message;
    @Schema(example = "500")
    private HttpStatus status;

    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
    }

    public static ErrorResponse from(final ErrorCode code) {
        return new ErrorResponse(code);
    }

}
