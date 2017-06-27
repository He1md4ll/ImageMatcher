package edu.hsbremen.cloud.facade.impl;

import autovalue.shaded.com.google.common.common.collect.Lists;
import edu.hsbremen.cloud.comparison.ComparisonStrategyContext;
import edu.hsbremen.cloud.comparison.strats.IComparisonStrategy;
import edu.hsbremen.cloud.dto.ComparsionDto;
import edu.hsbremen.cloud.dto.ImageDto;
import edu.hsbremen.cloud.facade.IComparisonFacade;
import edu.hsbremen.cloud.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComparisonFacade implements IComparisonFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparisonFacade.class.getSimpleName());

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
                final Double strategyScore = context.compare(ImageUtil.loadImage(referenceImage.getUrl()),
                        ImageUtil.loadImage(comparedImage.getUrl()));
                score += strategyScore * comparisonStrategy.getScoreWeight();
                LOGGER.info("Comparison result from {} to {} with strategy {}:{} (weight is {})",
                        referenceImage.getName(), comparedImage.getName(), comparisonStrategy.getClass().getSimpleName(),
                        strategyScore, comparisonStrategy.getScoreWeight());
            }

            result.add(new ComparsionDto(referenceImage, comparedImage,score));
        }
        return result;
    }
}