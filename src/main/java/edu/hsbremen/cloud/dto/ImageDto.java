package edu.hsbremen.cloud.dto;

import edu.hsbremen.cloud.persistance.domain.ImageEntity;

public class ImageDto {
    private String name;
    private String url;
    private String thumbnailUrl;

    public ImageDto(String name, String url, String thumbnailUrl) {
        this.name = name;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static ImageDto fromImageEntity(ImageEntity imageEntity) {
        return new ImageDto(imageEntity.getName(), imageEntity.getImageBlob().getPublicLink(), imageEntity.getThumbnailBlob().getPublicLink());
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}