package com.tousie.securities.model.message;

import javax.annotation.Nullable;

/**
 * @author sunqian
 */
public interface BiResponseMessage extends ResponseMessage{

    @Nullable
    String getRespondedId();
}
