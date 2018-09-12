package com.tousie.securities.model.message;

import javax.annotation.Nullable;

/**
 * @author sunqian
 */
public interface BiRequestMessage {

    @Nullable
    String getId();

    @Nullable
    String getUrl();

    @Nullable
    Object getData();
}
