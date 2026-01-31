package com.reveal.asia;



import com.intellij.openapi.util.Pair;

import java.util.ArrayList;


public class DescriptionRanker
{
    String codeSnippet;
    ArrayList<DescriptionInfo> descriptions = new ArrayList<>();

    public DescriptionRanker(String _codeSnippet, ArrayList<Pair<String, Double>> _descriptions)
    {
        this.codeSnippet = _codeSnippet;
        for(int i=0; i<_descriptions.size(); i++)
        {
            String descriptionText = _descriptions.get(i).getFirst();
            Double cloneSimilarity = _descriptions.get(i).getSecond();

            DescriptionInfo d = new DescriptionInfo(descriptionText, codeSnippet, cloneSimilarity);
            descriptions.add(d);
        }
    }

    public String getBestDescription()
    {
        Double maxScore=0.0;
        int maxScoreIndex = -1;
        for(int i=0; i<descriptions.size(); i++)
        {
            descriptions.get(i).calculateAllMetrics();
            Double score = descriptions.get(i).getOverallQualityScore();
            if(score>maxScore)
            {
                maxScore = score;
                maxScoreIndex = i;
            }
        }
        //////////
        String bestDescription = descriptions.get(maxScoreIndex).getDescriptionText();
        return bestDescription;
    }


    private class DescriptionInfo
    {
        String commentedCode, descriptionText;
        Double asia_metric=0., consistency_metric=0., readability_metric=0.; // all between 0 to 1

        public DescriptionInfo(String _descriptionText, String _codeSnippet, Double _asia_similarity)
        {
            descriptionText = _descriptionText;
            commentedCode = "//"+_descriptionText+"\n"+_codeSnippet;
            asia_metric = _asia_similarity;
        }

        private void calculateAllMetrics()
        {
            double[] metrics = GetCommentsQualityMetrics.getCommentsQualityIndicators(commentedCode);
            readability_metric =  metrics[0]/100.0;
            consistency_metric = metrics[1];
        }

        public Double getOverallQualityScore()
        {
            Double avgScore = (asia_metric+consistency_metric+readability_metric)/(3.0);
            return avgScore;
        }

        public String getDescriptionText()
        {
            return descriptionText;
        }
    }
}
