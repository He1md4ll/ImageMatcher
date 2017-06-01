package edu.hsbremen.cloud.dto;

import com.google.cloud.storage.Blob;

public class BlobDto {

    private static final String PUBLIC_URL_PATTERN = "https://storage.googleapis.com/%s/%s";
    private String name;
    private String bucket;
    private String mediaLink;

    public BlobDto(String name, String bucket, String mediaLink) {
        this.name = name;
        this.bucket = bucket;
        this.mediaLink = mediaLink;
    }

    public static BlobDto fromBlob(Blob blob) {
        return new BlobDto(blob.getName(), blob.getBucket(), blob.getMediaLink());
    }

    public String getName() {
        return name;
    }

    public String getBucket() {
        return bucket;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public String getPublicLink() {
        return String.format(PUBLIC_URL_PATTERN, bucket, name);
    }
}