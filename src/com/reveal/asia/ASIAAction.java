package com.reveal.asia;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.*;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by emadpres on 7/25/17.
 */
public class ASIAAction extends AnAction
{
    public Editor editor = null;
    PsiElement lowestCommonAncestorPsiElement = null;
    PsiElement firstLowestSameLevelPsiElement=null,secondLowestSameLevelPsiElement=null;
    ///////
    ArrayList<StronglyRelatedPsiElements> listOfStronglyRelatedPsiElements = null;
    final int WHOLE_BLOCK_THRESHOLD_MAGIC_NUMBER = -1;
    /////

    //////// UI ++
    public JBPopup sliderPopup  = null;
    /////// UI --


    private boolean isAncestor(PsiElement target, PsiElement supposalAncestor)
    {
        boolean res = false;
        while((target instanceof PsiJavaFile == false) && !res)
        {
            if(target==supposalAncestor)
                res = true;
            else
                target = target.getParent();

        }
        return res;
    }

    private PsiElement findLowestCommonAncestor(PsiElement first, PsiElement second)
    {
        PsiElement supposalLCA = first;
        boolean found = false;
        while(supposalLCA instanceof PsiJavaFile == false)
        {
            PsiElement p = second;
            found = isAncestor(p, supposalLCA);

            if(found==false)
                supposalLCA = supposalLCA.getParent();
            else
                break;
        }

        return supposalLCA;
    }

    private boolean isAMinumumMeaningfullNode(PsiElement e)
    {
        if(e instanceof PsiStatement == false && e instanceof PsiMethod == false && e instanceof PsiClass == false && e instanceof PsiJavaFile==false)
            return false;
        return true;
    }

    private Pair<PsiElement, PsiElement> findLowestSameLevelStatement(PsiElement selectionStartPsiElement, PsiElement selectionEndPsiElement)
    {
        Pair<PsiElement, PsiElement> pair = findLowestSameLevelPsiElements(selectionStartPsiElement, selectionEndPsiElement);

        firstLowestSameLevelPsiElement = pair.first;
        while(firstLowestSameLevelPsiElement!=null && firstLowestSameLevelPsiElement instanceof PsiWhiteSpace)
            firstLowestSameLevelPsiElement = firstLowestSameLevelPsiElement.getNextSibling();

        secondLowestSameLevelPsiElement = pair.second;
        while(secondLowestSameLevelPsiElement!=null && secondLowestSameLevelPsiElement instanceof PsiWhiteSpace)
            secondLowestSameLevelPsiElement = secondLowestSameLevelPsiElement.getPrevSibling();

        if(isAMinumumMeaningfullNode(firstLowestSameLevelPsiElement) == false || isAMinumumMeaningfullNode(secondLowestSameLevelPsiElement) ==false)
        {
            PsiElement lowestStatement = findLowestStatement(firstLowestSameLevelPsiElement);
            return new Pair<>(lowestStatement, lowestStatement);
        }
        else
            return new Pair<>(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement);
    }

    private Pair<PsiElement, PsiElement> findLowestSameLevelPsiElements(PsiElement selectionStartPsiElement, PsiElement selectionEndPsiElement)
    {
        if(selectionStartPsiElement==selectionEndPsiElement)
        {
            return new Pair<>(selectionStartPsiElement, selectionEndPsiElement);
        }

        PsiElement lowestCommonAncestor = findLowestCommonAncestor(selectionStartPsiElement, selectionEndPsiElement);
        PsiElement firstLowestSameLevelPsiElement = selectionStartPsiElement;
        while(firstLowestSameLevelPsiElement.getParent() != lowestCommonAncestor)
            firstLowestSameLevelPsiElement = firstLowestSameLevelPsiElement.getParent();
        PsiElement secondLowestSameLevelPsiElement = selectionEndPsiElement;
        while(secondLowestSameLevelPsiElement .getParent() != lowestCommonAncestor)
            secondLowestSameLevelPsiElement  = secondLowestSameLevelPsiElement.getParent();

        return new Pair<>(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement);
    }

    private PsiElement findLowestStatement(PsiElement e)
    {
        //while(e !=null && PsiUtil.isStatement(e)==false)
        while(e!=null  && isAMinumumMeaningfullNode(e) == false) //&& e instanceof PsiExpressionStatement==false && e instanceof CompositePsiElement == false
            e = e.getParent();

        return e;
    }

    @Override
    public void actionPerformed(AnActionEvent e)
    {

        listOfStronglyRelatedPsiElements = null;

        //Get all the required data from data keys
        editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        final Document document = editor.getDocument(); //Access document, caret, and selection


        final SelectionModel selectionModel = editor.getSelectionModel();
        int selectionStartOffset = selectionModel.getSelectionStart();
        int selectionEndOffset = selectionModel.getSelectionEnd();
        editor.getSelectionModel().removeSelection();
        PsiElement selectionStartPsiElement = psiFile.findElementAt(selectionStartOffset);
        PsiElement selectionEndPsiElement = psiFile.findElementAt(selectionEndOffset);

        lowestCommonAncestorPsiElement = findLowestCommonAncestor(selectionStartPsiElement, selectionEndPsiElement);

        /*while(lowestCommonAncestorPsiElement instanceof PsiCodeBlock == false && lowestCommonAncestorPsiElement instanceof PsiMethod == false && lowestCommonAncestorPsiElement instanceof PsiJavaFile == false)
        {
            lowestCommonAncestorPsiElement = lowestCommonAncestorPsiElement.getParent();
        }


        if(lowestCommonAncestorPsiElement instanceof PsiJavaFile)
        {
            clearAllHighlightRange();
            return;
        }*/


        clearAllHighlightRange();

        //PsiUtil.getTopLevelEnclosingCodeBlock()
        //PsiUtil.getEnclosingStatement()
        Pair<PsiElement, PsiElement> pair = findLowestSameLevelStatement(selectionStartPsiElement, selectionEndPsiElement);
        firstLowestSameLevelPsiElement = pair.first;
        secondLowestSameLevelPsiElement = pair.second;

        //showStartingEndingParentOfSelection();

        ArrayList<Integer> thresholds = preProcessBreakDownWithDifferentThresholds();
        if(thresholds.size()==0)
        {
            // Not enough code is selected.
            System.out.print("Not enough code is selected.");
            return;
        }

        JPanel granularitySliderPanel = new GranularitySliderPanel(this, thresholds);
        sliderPopup = JBPopupFactory.getInstance().createComponentPopupBuilder(granularitySliderPanel, null).setAlpha(0.5f) .createPopup();
        sliderPopup.showInFocusCenter();
        sliderPopup.addListener(new JBPopupListener()
                                {
                                    @Override
                                    public void beforeShown(LightweightWindowEvent lightweightWindowEvent)
                                    {

                                    }

                                    @Override
                                    public void onClosed(LightweightWindowEvent lightweightWindowEvent)
                                    {
                                        if(listOfStronglyRelatedPsiElements==null)
                                            clearAllHighlightRange();
                                    }
                                });
    }

    private boolean showStartingEndingParentOfSelection()
    {
        if(firstLowestSameLevelPsiElement==null || secondLowestSameLevelPsiElement==null)
        {
            System.out.print("starting or ending statement is null");
            return true;
        }

        clearAllHighlightRange();
        if(firstLowestSameLevelPsiElement!=secondLowestSameLevelPsiElement)
        {
            highlightRange(firstLowestSameLevelPsiElement.getTextRange().getStartOffset(), firstLowestSameLevelPsiElement.getTextRange().getEndOffset(), Color.RED);
            highlightRange(secondLowestSameLevelPsiElement.getTextRange().getStartOffset(), secondLowestSameLevelPsiElement.getTextRange().getEndOffset(), Color.YELLOW);
        }
        else
        {
            highlightRange(secondLowestSameLevelPsiElement.getTextRange().getStartOffset(), secondLowestSameLevelPsiElement.getTextRange().getEndOffset(), Color.ORANGE);
        }

        PsiElement parentOfBoth = firstLowestSameLevelPsiElement.getParent();
        if(secondLowestSameLevelPsiElement.getParent()!=parentOfBoth)
        {
            clearAllHighlightRange();
            highlightRange(parentOfBoth.getTextRange().getStartOffset(), parentOfBoth.getTextRange().getEndOffset(), Color.BLACK);
        }
        else
            highlightRange(parentOfBoth.getTextRange().getStartOffset(), parentOfBoth.getTextRange().getEndOffset(), Color.CYAN);
        return false;
    }

    public void fetchDescriptions()
    {
        for(int g=0;g<listOfStronglyRelatedPsiElements.size();g++)
        {
            StronglyRelatedPsiElements s = listOfStronglyRelatedPsiElements.get(g);
            s.fetchDescription();
            s.addCommentPsiElement();
        }
        highlightAllDiscoveredCodeSnippet();
    }

    private boolean isEquals_TwoStronglyRelatedPsiElementsArrayList(ArrayList<StronglyRelatedPsiElements> a, ArrayList<StronglyRelatedPsiElements> b)
    {
        if(a.size()!=b.size()) return false;
        for(int i=0; i<a.size();i++)
            if(a.get(0).equals(b.get(0))==false)
                return false;
        return true;

    }

    private boolean exists_f(ArrayList<ArrayList<StronglyRelatedPsiElements>> possibleResults, ArrayList<StronglyRelatedPsiElements> newResult)
    {
        for(int i=0;i<possibleResults.size();i++)
            if(isEquals_TwoStronglyRelatedPsiElementsArrayList(possibleResults.get(i),newResult)==true)
                return true;
        return false;
    }

    private ArrayList<Integer> preProcessBreakDownWithDifferentThresholds()
    {
        ArrayList<ArrayList<StronglyRelatedPsiElements>> possibleResults = new ArrayList<>();
        ArrayList<Integer> thresholds = new ArrayList<>();

        ///////////// 1/2
        for(int value=1;value<10;value++)
        {
            tryToBreakDownSelectedCode(value);
            if(listOfStronglyRelatedPsiElements!=null && listOfStronglyRelatedPsiElements.size()!=0 && exists_f(possibleResults, listOfStronglyRelatedPsiElements)==false)
            {
                thresholds.add(value);
                possibleResults.add(listOfStronglyRelatedPsiElements);
            }
        }
        ///////////// 2/2
        tryToBreakDownSelectedCode(WHOLE_BLOCK_THRESHOLD_MAGIC_NUMBER);
        if(listOfStronglyRelatedPsiElements!=null && listOfStronglyRelatedPsiElements.size()!=0 && exists_f(possibleResults, listOfStronglyRelatedPsiElements)==false)
        {
            thresholds.add(WHOLE_BLOCK_THRESHOLD_MAGIC_NUMBER);
            possibleResults.add(listOfStronglyRelatedPsiElements);
        }


        return thresholds;
    }

    public void tryToBreakDownSelectedCode(int sliderValue)
    {
        if(firstLowestSameLevelPsiElement!=null && secondLowestSameLevelPsiElement!=null)
        {
            listOfStronglyRelatedPsiElements = breakDownPsiElementToRelatedParts(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement, 0, sliderValue);
            highlightAllDiscoveredCodeSnippet();
        }
        else
        {
            listOfStronglyRelatedPsiElements = null;
            System.out.print("Starting or Ending PsiElement is null");
        }


        /*if( ASIAAction.this.lowestCommonAncestorPsiElement instanceof PsiCodeBlock)
        {
            listOfStronglyRelatedPsiElements = breakDownPsiElementToRelatedParts((PsiCodeBlock) ASIAAction.this.lowestCommonAncestorPsiElement, 0, sliderValue);
            highlightAllDiscoveredCodeSnippet();
        }
        else
        {
            listOfStronglyRelatedPsiElements = null;
            System.out.print("Common parent is not PsiCodeBlock");
        }*/
    }

    public void highlightAllDiscoveredCodeSnippet()
    {
        clearAllHighlightRange();
        boolean b = true;
        for(int g=0;g<listOfStronglyRelatedPsiElements.size();g++)
        {
            StronglyRelatedPsiElements s = listOfStronglyRelatedPsiElements.get(g);
            s.setColor(getColor(s.getNestedLevel(), b));
            s.performHighlighting();
            //highlightRange(s.getTextRange(), s.getNestedLevel(), b, highlightingLowestLayer+s.getNestedLevel());
            b=!b;
        }
    }

    public void createCodeDescriptionPopup(StronglyRelatedPsiElements s)
    {
        CodeDescriptionPopup c = new CodeDescriptionPopup(s, this);
        c.getComponent().showInBestPositionFor(editor);
    }

    private ArrayList<StronglyRelatedPsiElements> breakDownPsiElementToRelatedParts(PsiCodeBlock _psiCodeBlock, int nestedLevel, int maxAccetableVariableDistance)
    {
        return breakDownPsiElementToRelatedParts(_psiCodeBlock.getFirstBodyElement(), _psiCodeBlock.getLastBodyElement(), nestedLevel, maxAccetableVariableDistance);
    }

    private ArrayList<StronglyRelatedPsiElements> breakDownPsiElementToRelatedParts(PsiElement startingPsiElement, PsiElement endingPsiElement, int nestedLevel, int maxAccetableVariableDistance)
    {
        //Precondition: "startingPsiElement" and "endingPsiElement" must be in same level;
        assert startingPsiElement.getParent() == endingPsiElement.getParent();
        /////////////////////////////// CONST
        int MIN_BLOCK_SIZE = 3;
        int MAX_ACCEPTABLE_NOISE = 1;
        /////////////////////////////// TO FILL
        int nStatements = -1;
        // For following arrays, index=0 stores the information regarding the 'index'th PsiElement that we are visiting
        ArrayList<PsiElement> psiElements = new ArrayList<>();
        ArrayList<ArrayList<String>> psiElementIdentifiers = new ArrayList<>();
        ArrayList<ArrayList<Integer>> directlyRelatedAdjMatrix= new ArrayList<>();
        ArrayList<ArrayList<Integer>> indirectlyRelatedAdjMatrix= new ArrayList<>();
        ///////////////////////////////
        PsiElement p = startingPsiElement;
        while(p!=endingPsiElement.getNextSibling())
        {
            //if (p instanceof PsiStatement || p instanceof CompositePsiElement)
            if(isAMinumumMeaningfullNode(p))
            {
                ArrayList<String> currentPsiElement_identifiers = getIdentifiersFromPsiElement(p);
                ///
                psiElementIdentifiers.add(currentPsiElement_identifiers);
                psiElements.add(p);
            }
            p = p.getNextSibling();
        }

        nStatements = psiElementIdentifiers.size();

        if(maxAccetableVariableDistance==WHOLE_BLOCK_THRESHOLD_MAGIC_NUMBER)
        {
            ArrayList<StronglyRelatedPsiElements> stronglyRelatedPsiElements = new ArrayList<>(); //stronglyRelatedCodeSnippet;

            if(countNMeaninfulNodeInWholeSubtree(psiElements)<MIN_BLOCK_SIZE && (psiElements.size()==0 || getDirectPsiCodeBlockIfExits(psiElements.get(0))==null))
                return stronglyRelatedPsiElements;
            stronglyRelatedPsiElements.add(new StronglyRelatedPsiElements(nestedLevel, psiElements, this));
            return stronglyRelatedPsiElements;
        }
        else
        {
            for (int currentIndex = 0; currentIndex < nStatements; currentIndex++)
            {
                ArrayList<Integer> l = new ArrayList<>();
                l.add(currentIndex);
                //////
                for (int j = 0; j < nStatements; j++)
                {
                    if (currentIndex == j || Math.abs(j - currentIndex) > maxAccetableVariableDistance)
                        continue;
                    if (hasCommon(psiElementIdentifiers.get(currentIndex), psiElementIdentifiers.get(j)))
                        l.add(j);
                }
                directlyRelatedAdjMatrix.add(l);
            }

            for (int currentIndex = 0; currentIndex < nStatements; currentIndex++)
            {
                ArrayList<Integer> indirectlyRelated = new ArrayList<>();
                //////
                Set<Integer> visited = new HashSet<>();
                Stack<Integer> s = new Stack<>();
                s.addAll(directlyRelatedAdjMatrix.get(currentIndex));
                //////
                while (!s.isEmpty())
                {
                    int pop = s.pop();
                    if (visited.contains(pop))
                        continue;
                    visited.add(pop);
                    indirectlyRelated.add(pop);
                    s.addAll(directlyRelatedAdjMatrix.get(pop));
                }
                indirectlyRelatedAdjMatrix.add(indirectlyRelated);
            }

            int currentPsiElementIndex = 0;
            ArrayList<StronglyRelatedPsiElements> stronglyRelatedPsiElements = new ArrayList<>(); //stronglyRelatedCodeSnippet



            if(countNMeaninfulNodeInWholeSubtree(psiElements)<MIN_BLOCK_SIZE && (psiElements.size()==0 || getDirectPsiCodeBlockIfExits(psiElements.get(0))==null))
                return stronglyRelatedPsiElements;

            while(currentPsiElementIndex<nStatements)
            {
                int latestContinuesIndex = findContinuesLineNumberSeries(indirectlyRelatedAdjMatrix.get(currentPsiElementIndex), currentPsiElementIndex, MAX_ACCEPTABLE_NOISE);


                ArrayList<PsiElement> currentStronglyRelatedPsiElements_array = new ArrayList<>();
                for(int i=currentPsiElementIndex; i<=latestContinuesIndex; i++)
                    currentStronglyRelatedPsiElements_array.add(psiElements.get(i));
                currentPsiElementIndex = latestContinuesIndex+1;

                PsiElement theOnlyElement = currentStronglyRelatedPsiElements_array.get(0);
                PsiCodeBlock childCodeBlock = getDirectPsiCodeBlockIfExits(theOnlyElement);

                if (currentStronglyRelatedPsiElements_array.size() == 1 && childCodeBlock!=null)
                {

                    if (childCodeBlock != null)
                    {
                        ArrayList<StronglyRelatedPsiElements> result = breakDownPsiElementToRelatedParts(childCodeBlock, nestedLevel + 1, maxAccetableVariableDistance);

                        for (int i = 0; i < result.size(); i++)
                            stronglyRelatedPsiElements.add(result.get(i));
                    }
                }
                else if(countNStatementsInPsiElement(currentStronglyRelatedPsiElements_array)>=MIN_BLOCK_SIZE)
                    stronglyRelatedPsiElements.add(new StronglyRelatedPsiElements(nestedLevel, currentStronglyRelatedPsiElements_array, this));
            }
            return stronglyRelatedPsiElements;
        }
    }


    private int countNMeaninfulNodeInWholeSubtree(ArrayList<PsiElement> es)
    {
        int nMeaningfulElements = 0;
        for(int i=0; i<es.size(); i++)
            nMeaningfulElements += countNMeaninfulNodeInWholeSubtree(es.get(i));
        return nMeaningfulElements;
    }

    private int countNMeaninfulNodeInWholeSubtree(PsiElement e)
    {
        PsiElement[] meaningfulElements = PsiTreeUtil.collectElements(e, new PsiElementFilter() {
            public boolean isAccepted(PsiElement e) {
                if(isAMinumumMeaningfullNode(e)==true)
                    return true;
                return false;
            }
        });
        return meaningfulElements.length;
    }

    private PsiCodeBlock getDirectPsiCodeBlockIfExits(PsiElement element)
    {
        PsiElement[] children = element.getChildren();
        for(int i=0; i<children.length; i++)
        {
            if(children[i] instanceof PsiBlockStatement)
                return (PsiCodeBlock)(children[i].getFirstChild());
        }
        return null;
    }


    private void clearAllHighlightRange()
    {
        editor.getMarkupModel().removeAllHighlighters();
    }


    private void highlightRange(int startOffset, int endOffset)
    {
        Color color = getColor(0, true);
        highlightRange(startOffset, endOffset, color);

    }
    private void highlightRange(int startOffset, int endOffset, Color c)
    {
        TextAttributes myTextAtt = new TextAttributes(Color.BLACK, c, Color.BLACK, EffectType.ROUNDED_BOX, Font.ITALIC);
        RangeHighlighter rh = editor.getMarkupModel().addRangeHighlighter(startOffset, endOffset, 6000, myTextAtt, HighlighterTargetArea.EXACT_RANGE);
    }

    /*private void highlightRange(TextRange r,  int nestedLevel, boolean b, int highlightZ)
    {
        highlightRange(r.getStartOffset(), r.getEndOffset(), nestedLevel, b, highlightZ);
    }*/

    private int countNStatementsInPsiElement(ArrayList<PsiElement> arrayOfPsiElement)
    {
        int nStatements = 0;
        for(int i=0; i<arrayOfPsiElement.size(); i++)
        {
            nStatements += countNStatementsInPsiElement(arrayOfPsiElement.get(i));
        }
        return nStatements;
    }

    private int countNStatementsInPsiElement(PsiElement _parentPsiElement)
    {
        //TODO : Count Leafes
        int nStatements = 0;

        if(_parentPsiElement instanceof PsiStatement)
            return 1;


        PsiElement p = _parentPsiElement.getFirstChild();
        while(p!=null)
        {
            if (p instanceof PsiStatement)
                nStatements++;
            p = p.getNextSibling();
        }

        return nStatements;
    }

    private void showSelectedAreaInfo(Editor editor)
    {
        final SelectionModel selectionModel = editor.getSelectionModel();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        String selectedText = selectionModel.getSelectedText();

        CaretModel caretModel = editor.getCaretModel();
        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
        VisualPosition visualPosition = caretModel.getVisualPosition();
        int offset = caretModel.getOffset();

        /*Messages.showInfoMessage("Selection Model Starte/End:"+start+"/"+end+"\n"+
                        "Logical Position:"+logicalPosition.toString()+"\n"+
                        "Visual Position:"+visualPosition.toString()+"\n"+
                        "Offset Position:"+offset+"\n"+
                        "String:"+selectedText, "Info");
        */
    }

    private int findContinuesLineNumberSeries(ArrayList<Integer> listOfIndirectlyRelatedLineNumbers, int startingFrom, int maxAcceptableNoise)
    {
        int BAD_VALUE = -1;
        Collections.sort(listOfIndirectlyRelatedLineNumbers);
        ArrayList<Integer> l = new ArrayList<>();
        for(int i=0;i<listOfIndirectlyRelatedLineNumbers.size(); i++)
        {
            Integer value = listOfIndirectlyRelatedLineNumbers.get(i);
            if (value >= startingFrom)
                l.add(value);
        }
        if(l.size()==0)
            return BAD_VALUE;
        /////////////
        int latestContinuesIndex = startingFrom;
        int expectedToSee = startingFrom;
        int remainingAcceptableNoiseForThisContinuesSeries = maxAcceptableNoise;

        for(int index=0;index<l.size(); index++)
        {
            while (l.get(index) != expectedToSee && remainingAcceptableNoiseForThisContinuesSeries > 0)
            {
                remainingAcceptableNoiseForThisContinuesSeries--;
                expectedToSee++;
            }

            if (l.get(index) == expectedToSee)
                latestContinuesIndex = expectedToSee;
            expectedToSee++;
        }
        return latestContinuesIndex;
    }

    private <T> void removeDuplicateFromArrayList(ArrayList<T> l)
    {
        Set<T> hs = new HashSet<>();
        hs.addAll(l);
        l.clear();
        l.addAll(hs);
    }

    private ArrayList<String> getIdentifiersFromPsiElement(PsiElement e)
    {
        PsiElement[] identifiers = PsiTreeUtil.collectElements(e, new PsiElementFilter() {
            public boolean isAccepted(PsiElement e) {
                if (e instanceof PsiIdentifier) {
                    return true;
                }
                return false;
            }
        });

        ////////////
        Set<String> hs = new HashSet<>();
        for (int i=0; i<identifiers.length; i++)
            hs.add(identifiers[i].getText());
        ////////////
        ArrayList<String> allIdentifiers = new ArrayList<>();
        allIdentifiers.addAll(hs);
        return allIdentifiers;
    }

    private boolean hasCommon(ArrayList<String> first, ArrayList<String> second)
    {
        List<String> common = new ArrayList<String>(first);
        common.retainAll(second);
        if (common.size() > 0)
            return true;
        return false;
    }

    public void generareComment(StronglyRelatedPsiElements s, String text)
    {

        PsiElement firstPsiElement ;
        if(s.psiElements.get(0) instanceof PsiCodeBlock)
            firstPsiElement = ((PsiCodeBlock) s.psiElements.get(0)).getFirstBodyElement().getNextSibling();
        else
            firstPsiElement = s.psiElements.get(0);



        PsiElement m = firstPsiElement;
        new WriteCommandAction.Simple(firstPsiElement.getProject(), firstPsiElement.getContainingFile())
        {
            @Override
            protected void run() throws Throwable
            {

                PsiComment commentFromText = JavaPsiFacade.getElementFactory(getProject()).createCommentFromText(text, firstPsiElement);
                PsiElement newlyAddedComment = firstPsiElement.addAfter(commentFromText, null);
            }

        }.execute();
    }

    private Color getColor(int nestedLevel, boolean b)
    {

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

    private void generate(PsiClass psiClass, List<PsiField> fields)
    {
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile())
        {
            @Override
            protected void run() throws Throwable
            {
                StringBuilder builder = new StringBuilder("public int emad;");
                PsiField fieldFromText = JavaPsiFacade.getElementFactory(getProject()).createFieldFromText(builder.toString(), psiClass);
                psiClass.add(fieldFromText);
            }

        }.execute();
    }

    @Override
    public void update(AnActionEvent e)
    {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        e.getPresentation().setVisible((project != null && editor != null));

    }

    private PsiClass getPsiClassFromContext(AnActionEvent e)
    {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile==null || editor == null)
        {
            e.getPresentation().setEnabled(false);
            return null;
        }

        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
        return psiClass;
    }
}
