package com.tousie.securities.model.message;

import com.tousie.securities.common.status.StatusEnum;

import javax.annotation.Nullable;

/**
 * @author sunqian
 */
public interface ResponseMessage {

    ResponseMessage UNKNOWN_ERROR = new ResponseMessage() {
        @Override
        public String getCode() {
            return StatusEnum.UNKNOWN_ERROR.getCode();
        }

        @Nullable
        @Override
        public String getDesc() {
            return StatusEnum.UNKNOWN_ERROR.getDesc();
        }

        @Nullable
        @Override
        public Object getData() {
            return null;
        }
    };

    ResponseMessage INTERNAL_ERROR = new ResponseMessage() {
        @Override
        public String getCode() {
            return StatusEnum.INTERNAL_ERROR.getCode();
        }

        @Nullable
        @Override
        public String getDesc() {
            return StatusEnum.INTERNAL_ERROR.getDesc();
        }

        @Nullable
        @Override
        public Object getData() {
            return null;
        }
    };


    String getCode();

    @Nullable
    String getDesc();

    @Nullable
    Object getData();
}
