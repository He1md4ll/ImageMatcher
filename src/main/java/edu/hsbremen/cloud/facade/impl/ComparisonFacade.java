package edu.hsbremen.cloud.facade.impl;

import edu.hsbremen.cloud.dto.ComparsionDto;
import edu.hsbremen.cloud.dto.ImageDto;
import edu.hsbremen.cloud.facade.IComparisonFacade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComparisonFacade implements IComparisonFacade {

    @Override
    public List<ComparsionDto> compareImages(ImageDto referenceImage, List<ImageDto> comparedImageList) {
        // TODO: Image Comparison Strategy (strategy pattern)
        return null;
    }
}