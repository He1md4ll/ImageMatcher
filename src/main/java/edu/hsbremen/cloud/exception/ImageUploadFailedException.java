package edu.hsbremen.cloud.exception;

import java.io.IOException;

public class ImageUploadFailedException extends IOException {
    private static final String MESSAGE = "File upload failed.";

    public ImageUploadFailedException(Throwable cause) {
        super(MESSAGE, cause);
    }
}