package com.cargobackend.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonConstants {

    public static enum Status {
        SUCCESS,
        PENDING,
        FAILURE;

        private Status() {
        }
    }
    
    public static final int DB_PROC_SUCCESS_RESPONSE = 1;
}
