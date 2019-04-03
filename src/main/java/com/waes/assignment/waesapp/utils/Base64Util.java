package com.waes.assignment.waesapp.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64Util {

    public byte[] decode(byte[] input){
        return Base64.getDecoder().decode(input);
    }
}
