package com.tousie.securities.exception;

import com.tousie.securities.common.status.StatusEnum;

/**
 * @author sunqian
 */
public class ValidationException extends BusinessException {

    public ValidationException(String desc) {
        super(StatusEnum.PARAM_ERROR.getCode(), desc);
    }
}
