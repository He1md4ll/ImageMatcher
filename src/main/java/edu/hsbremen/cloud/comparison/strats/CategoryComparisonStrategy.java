package edu.hsbremen.cloud.comparison.strats;

import boofcv.abst.scene.ImageClassifier;
import com.google.common.collect.Maps;
import edu.hsbremen.cloud.comparison.calc.CategoryCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class CategoryComparisonStrategy implements IComparisonStrategy {

    private CategoryCalculator categoryCalculator;

    @Autowired
    public CategoryComparisonStrategy(CategoryCalculator categoryCalculator) {
        this.categoryCalculator = categoryCalculator;
    }

    @Override
    public Double compare(byte[] imageReference, byte[] imageCompared) {
        final Map<Integer, Double> comparedMap = Maps.newHashMap();
        final List<ImageClassifier.Score> imageReferenceResults = categoryCalculator.calculate(imageReference);
        final List<ImageClassifier.Score> imageComparedResults = categoryCalculator.calculate(imageCompared);
        imageReferenceResults.sort(new ImageCoreComperator());
        imageComparedResults.sort(new ImageCoreComperator());
        for (int i = 0; i < imageReferenceResults.size(); i++) {
            double scoreReference = imageReferenceResults.get(i).score;
            double scoreCompared = imageComparedResults.get(i).score;
            comparedMap.put(imageReferenceResults.get(i).category, Math.abs(scoreReference - scoreCompared));
        }

        final Map<Integer, Double> filteredMap = Maps.filterValues(comparedMap, scoreRatio -> scoreRatio < 0.5d);
        return (double) filteredMap.size() / (double) imageReferenceResults.size();
    }

    @Override
    public Double getScoreWeight() {
        return 0.4d;
    }

    private static class ImageCoreComperator implements Comparator<ImageClassifier.Score> {

        @Override
        public int compare(ImageClassifier.Score o1, ImageClassifier.Score o2) {
            if (o1.category < o2.category) {
                return -1;
            } else if (o1.category > o2.category) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}