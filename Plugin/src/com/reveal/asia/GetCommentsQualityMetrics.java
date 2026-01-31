
package com.reveal.asia;

import java.util.ArrayList;
import java.util.List;

import it.unimol.readability.metric.FeatureCalculator;
import it.unimol.readability.metric.parts.our.commentsReadabilityMetric.CommentsReadabilityWordsFeature;
import it.unimol.readability.metric.parts.our.synonymCommentedWordsMetric.SynonymCommentedWordsFeature3;

public class GetCommentsQualityMetrics {

	public static double[] getCommentsQualityIndicators(String commentedCode){
		double[] metrics = new double[2];
		
		List<FeatureCalculator> calculators = new ArrayList<>();
		calculators.add(new CommentsReadabilityWordsFeature());
		calculators.add(new SynonymCommentedWordsFeature3(SynonymCommentedWordsFeature3.KIND_AVG));
		//calculators.add(new SynonymCommentedWordsFeature(SynonymCommentedWordsFeature.KIND_MIN));
		//calculators.add(new SynonymCommentedWordsFeature(SynonymCommentedWordsFeature.KIND_MAX));
		
		for (int i=0; i<calculators.size(); i++) {
			FeatureCalculator featureCalculator = calculators.get(i);
			featureCalculator.setSource(commentedCode);
			metrics[i] = featureCalculator.calculate();
		}
		
		return metrics;
	}
	
}
