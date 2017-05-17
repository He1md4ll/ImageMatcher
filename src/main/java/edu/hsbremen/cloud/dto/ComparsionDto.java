package edu.hsbremen.cloud.dto;

public class ComparsionDto {
    private ImageDto referenceImage;
    private ImageDto comparedImage;
    private Integer score;

    public ComparsionDto(ImageDto referenceImage, ImageDto comparedImage, Integer score) {
        this.referenceImage = referenceImage;
        this.comparedImage = comparedImage;
        this.score = score;
    }

    public ImageDto getReferenceImage() {
        return referenceImage;
    }

    public ImageDto getComparedImage() {
        return comparedImage;
    }

    public Integer getScore() {
        return score;
    }
}