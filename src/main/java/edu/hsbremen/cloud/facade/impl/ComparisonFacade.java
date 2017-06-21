package edu.hsbremen.cloud.facade.impl;

import autovalue.shaded.com.google.common.common.collect.Lists;
import edu.hsbremen.cloud.comparison.ComparisonStrategyContext;
import edu.hsbremen.cloud.comparison.strats.IComparisonStrategy;
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

    @Autowired
    private List<IComparisonStrategy> comparisonStrategyList;

    @Override
    public List<ComparsionDto> compareImages(final ImageDto referenceImage, final List<ImageDto> comparedImageList) {
        List<ComparsionDto> result = Lists.newArrayList();

        for (final ImageDto comparedImage : comparedImageList) {
            double score = 0.0d;
            for (IComparisonStrategy comparisonStrategy : comparisonStrategyList) {
                context.setComparisonStrategy(comparisonStrategy);
                score += context.compare(ImageUtil.loadImage(referenceImage.getUrl()),
                        ImageUtil.loadImage(comparedImage.getUrl())) * comparisonStrategy.getScoreWeight();
            }

            result.add(new ComparsionDto(referenceImage, comparedImage,score));
        }
        return result;
    }
}