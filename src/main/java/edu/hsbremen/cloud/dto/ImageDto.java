package edu.hsbremen.cloud.dto;

import edu.hsbremen.cloud.persistance.domain.ImageEntity;

public class ImageDto {
    private String name;
    private String url;

    public ImageDto(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public static ImageDto fromImageEntity(ImageEntity imageEntity) {
        return new ImageDto(imageEntity.getName(), imageEntity.getUrl());
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}