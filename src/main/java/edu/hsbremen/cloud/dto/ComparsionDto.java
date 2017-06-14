package edu.hsbremen.cloud.dto;

public class ComparsionDto {
    private ImageDto referenceImage;
    private ImageDto comparedImage;
    private Double score;

    public ComparsionDto(ImageDto referenceImage, ImageDto comparedImage, Double score) {
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

    public Double getScore() {
        return score;
    }
}