package com.treeleaf.restapi.dtos;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
public class Image {
    public static String screenshotId;

    public static Byte[] uploadImage(MultipartFile screenshot) throws IOException {
        screenshotId = UUID.randomUUID().toString();
        byte[] screenshotData = screenshot.getBytes();
        Byte[] screenshotBytes = new Byte[screenshotData.length];
        for (int i = 0; i < screenshotData.length; i++) {
            screenshotBytes[i] = screenshotData[i];
        }
        return screenshotBytes;
    }

    public static byte[] getImage(Byte[] screenshotBytes) {
        byte[] screenshotData = new byte[screenshotBytes.length];
        for (int i = 0; i < screenshotBytes.length; i++) {
            screenshotData[i] = screenshotBytes[i];
        }
        return screenshotData;
    }

}
