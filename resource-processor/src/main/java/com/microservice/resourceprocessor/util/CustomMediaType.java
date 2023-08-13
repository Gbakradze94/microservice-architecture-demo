package com.microservice.resourceprocessor.util;


import java.util.Arrays;

public enum CustomMediaType {
    MP3("mp3", "audio/mpeg");
    private final String extension;
    private final String mimeType;

    CustomMediaType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static CustomMediaType getMediaTypeByExtension(String extension) {
        return Arrays.stream(CustomMediaType.values())
                .filter(mediaType -> mediaType.extension.equals(extension))
                .findFirst()
                .orElse(null);
    }

    public static CustomMediaType getMediaTypeByMimeType(String mimeType) {
        return Arrays.stream(CustomMediaType.values())
                .filter(mediaType -> mediaType.mimeType.equals(mimeType))
                .findFirst()
                .orElse(null);
    }
}