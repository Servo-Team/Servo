package com.servo.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;


public final class Hash {
    private Hash (){};

//    @RequiresApi(api = Build.VERSION_CODES.O)
    public final static String decodeBase64(String text) {
        return new String(Base64.getDecoder().decode(text.getBytes()));
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
    public final static String encodeBase64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

}
