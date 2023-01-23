package com.api.twitter.repositories.implementation.utils;

import com.api.twitter.exceptions.message.GeneralMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class customConsumers {
    public static void generalErrorInDB(Throwable throwable) {
        log.error(GeneralMessage.GENERAL_DB.getMessage() + "[" + throwable.getMessage() + "]");
        throw new RuntimeException(GeneralMessage.GENERAL_DB.getMessage());
    }
}
