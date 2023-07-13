package com.alexandru.SpringBootStore.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ImageUtil {

    public static String getBase64Image(byte[] image) {
        return Base64.getEncoder().encodeToString(image);
    }


}
