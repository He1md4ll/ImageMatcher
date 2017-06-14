package edu.hsbremen.cloud.dto;

import com.google.common.base.Preconditions;

import javax.validation.constraints.NotNull;

public class ImageHolder {
    private String imageName;
    private byte[] imageBytes;

    public ImageHolder(@NotNull String imageName,@NotNull byte[] imageBytes) {
        Preconditions.checkNotNull(imageName);
        Preconditions.checkNotNull(imageBytes);
        Preconditions.checkArgument(imageBytes.length > 0, "Could not load image!");
        this.imageName = imageName;
        this.imageBytes = imageBytes;
    }

    public String getImageName() {
        return imageName;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}
