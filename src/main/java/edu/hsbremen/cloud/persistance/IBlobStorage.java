package edu.hsbremen.cloud.persistance;

import edu.hsbremen.cloud.dto.ImageHolder;

public interface IBlobStorage {
    String saveImage(ImageHolder imageHolder);
}