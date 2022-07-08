package com.gut.tools.lsfs.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.io.OutputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseCrypto {

    public static InputStream encode(InputStream fileStream, String key) {
        //TODO: implement encode logic here
        return null;
    }

    public static String encode(String string) {
        //TODO: implement encode logic here
        return null;
    }

    public static OutputStream decode(InputStream fileStream, String key) {
        //TODO: implement decode logic here
        return null;
    }

    public static String decode(String String) {
        //TODO: implement decode logic here
        return null;
    }
}
