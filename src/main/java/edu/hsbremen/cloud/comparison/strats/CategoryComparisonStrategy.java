package edu.hsbremen.cloud.comparison.strats;

import boofcv.abst.scene.ImageClassifier;
import com.google.common.collect.TreeMultimap;
import edu.hsbremen.cloud.comparison.calc.CategoryCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryComparisonStrategy implements IComparisonStrategy {

    private CategoryCalculator categoryCalculator;

    @Autowired
    public CategoryComparisonStrategy(CategoryCalculator categoryCalculator) {
        this.categoryCalculator = categoryCalculator;
    }

    @Override
    public Double compare(byte[] imageReference, byte[] imageCompared) {
        final List<ImageClassifier.Score> imageReferenceResults = categoryCalculator.calculate(imageReference);
        final List<ImageClassifier.Score> imageComparedResults = categoryCalculator.calculate(imageReference);
        final TreeMultimap<Integer, Double> multimap = TreeMultimap.create();
        for (ImageClassifier.Score score : imageReferenceResults) {
            multimap.put(score.category, score.score);
        }
        for (ImageClassifier.Score score : imageComparedResults) {
            multimap.put(score.category, score.score);
        }
        return 0.0d;
    }

    @Override
    public Double getScoreWeight() {
        return 0.0d;
    }
}