package edu.hsbremen.cloud.persistance;

import edu.hsbremen.cloud.dto.BlobDto;
import edu.hsbremen.cloud.dto.ImageHolder;

public interface IBlobStorage {
    BlobDto saveImage(ImageHolder imageHolder);
}