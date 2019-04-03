package com.waes.assignment.waesapp;

import java.util.UUID;

public final class UUIDUtil {

    public static String getUUIDString(){
        return  UUID.randomUUID().toString().replace("-", "");
    }
}
