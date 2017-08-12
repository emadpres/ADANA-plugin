package com.reveal.asia;

import com.intellij.openapi.editor.markup.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by emadpres on 8/12/17.
 */
public class EditorHighlightHelper
{
    static public void highlightAllDiscoveredCodeSnippet(MarkupModel documentyMarkupModel, ArrayList<StronglyRelatedPsiElements> listOfStronglyRelatedPsiElements, boolean clearExisting)
    {
        if(clearExisting)
            clearAllHighlightRange(documentyMarkupModel);
        //boolean b = true;
        if(listOfStronglyRelatedPsiElements==null)
            return;

        for(int g=0;g<listOfStronglyRelatedPsiElements.size();g++)
        {
            StronglyRelatedPsiElements s = listOfStronglyRelatedPsiElements.get(g);
            s.setColor(getColor(s.getNestedLevel(), true));
            s.performHighlighting(documentyMarkupModel);
            //b=!b;
        }
    }

    static public void highlightAllDiscoveredCodeSnippetIfHasComment(MarkupModel documentMarkupModel, ArrayList<StronglyRelatedPsiElements> listOfStronglyRelatedPsiElements, boolean clearExisting)
    {
        if(clearExisting)
            clearAllHighlightRange(documentMarkupModel);

        if(listOfStronglyRelatedPsiElements==null)
            return;
        for(int g=0;g<listOfStronglyRelatedPsiElements.size();g++)
        {
            StronglyRelatedPsiElements s = listOfStronglyRelatedPsiElements.get(g);
            if(s.newlyAddedComments.size()==0)
                continue;
            s.setColor(getColor(s.getNestedLevel(), true));
            s.performHighlighting(documentMarkupModel);
            //b=!b;
        }
    }


    static public void clearAllHighlightRange(MarkupModel documentMarkupModel)
    {
        documentMarkupModel.removeAllHighlighters();
    }

    static public Color getColor(int nestedLevel, boolean b)
    {

        b=false;
        //Color[] colors = new Color[]{Color.CYAN, Color.green, Color.RED, Color.YELLOW, Color.ORANGE, Color.MAGENTA, Color.LIGHT_GRAY};
        final Color LIGHT_YELLOW = new Color(255,255,180);
        final Color DARK_ORANGE = new Color(255, 150, 0);
        final Color[] colors_g1 = new Color[]{Color.YELLOW, DARK_ORANGE, Color.RED};
        final Color[] colors_g2 = new Color[]{LIGHT_YELLOW, Color.ORANGE, Color.PINK};
        /////
        if(nestedLevel<0)
            return Color.BLACK;
        nestedLevel = nestedLevel%colors_g1.length;
        //////
        if(b)
            return colors_g1[nestedLevel];
        else
            return colors_g2[nestedLevel];
    }

    /*private void highlightRange(int startOffset, int endOffset)
    {
        Color color = getColor(0, true);
        highlightRange(startOffset, endOffset, color);

    }*/

    static public void highlightRange(MarkupModel documentMarkupModel, int startOffset, int endOffset, Color c)
    {
        TextAttributes myTextAtt = new TextAttributes(Color.BLACK, c, Color.BLACK, EffectType.ROUNDED_BOX, Font.ITALIC);
        RangeHighlighter rh = documentMarkupModel.addRangeHighlighter(startOffset, endOffset, 6000, myTextAtt, HighlighterTargetArea.EXACT_RANGE);
    }

    /*private void highlightRange(TextRange r,  int nestedLevel, boolean b, int highlightZ)
    {
        highlightRange(r.getStartOffset(), r.getEndOffset(), nestedLevel, b, highlightZ);
    }*/


}
