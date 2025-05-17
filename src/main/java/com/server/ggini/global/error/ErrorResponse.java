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

    // 상세 메시지를 포함하는 생성자 추가
    private ErrorResponse(final ErrorCode code, final String detail) {
        this.message = code.getMessage() + " - " + detail;
        this.status = code.getStatus();
    }

    public static ErrorResponse from(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    // 상세 메시지를 포함하는 팩토리 메서드 추가
    public static ErrorResponse from(final ErrorCode code, final String detail) {
        return new ErrorResponse(code, detail);
    }

}
