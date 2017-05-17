package edu.hsbremen.cloud.facade;

import edu.hsbremen.cloud.dto.ComparsionDto;
import edu.hsbremen.cloud.dto.ImageDto;

import java.util.List;

public interface IComparisonFacade {
    List<ComparsionDto> compareImages(ImageDto referenceImage, List<ImageDto> comparedImageList);
}