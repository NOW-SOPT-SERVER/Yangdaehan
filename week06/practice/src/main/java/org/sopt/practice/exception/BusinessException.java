package org.sopt.practice.exception;

import lombok.Getter;
import org.sopt.practice.common.dto.ErrorMessage;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class BusinessException extends RuntimeException {
    private ErrorMessage errorMessage;
    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
