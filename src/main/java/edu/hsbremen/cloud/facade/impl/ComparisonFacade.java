package edu.hsbremen.cloud.facade.impl;

import autovalue.shaded.com.google.common.common.collect.Lists;
import edu.hsbremen.cloud.comparison.ComparisonStrategyContext;
import edu.hsbremen.cloud.comparison.InterestPointsComparisonStrategy;
import edu.hsbremen.cloud.dto.ComparsionDto;
import edu.hsbremen.cloud.dto.ImageDto;
import edu.hsbremen.cloud.facade.IComparisonFacade;
import edu.hsbremen.cloud.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComparisonFacade implements IComparisonFacade {

    @Autowired
    private ComparisonStrategyContext context;

    @Override
    public List<ComparsionDto> compareImages(final ImageDto referenceImage, final List<ImageDto> comparedImageList) {
        List<ComparsionDto> result = Lists.newArrayList();
        context.setComparisonStrategy(new InterestPointsComparisonStrategy());
        for (final ImageDto comparedImage : comparedImageList) {
            result.add(new ComparsionDto(referenceImage,
                    comparedImage,
                    context.compare(ImageUtil.loadImage(referenceImage.getUrl()),
                            ImageUtil.loadImage(comparedImage.getUrl()))));
        }
        return result;
    }
}