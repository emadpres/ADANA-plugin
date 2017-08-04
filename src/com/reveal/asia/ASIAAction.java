package com.reveal.asia;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.*;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

/**
 * Created by emadpres on 7/25/17.
 */
public class ASIAAction extends AnAction
{
    public Editor editor = null;
    PsiElement lowestCommonAncestorPsiElement = null;
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
        //highlightRange(selectionStartPsiElement.getTextRange(), 0);
        //highlightRange(selectionEndPsiElement.getTextRange(), 1);
        lowestCommonAncestorPsiElement = findLowestCommonAncestor(selectionStartPsiElement, selectionEndPsiElement);
        //highlightRange(lowestCommonAncestorPsiElement.getTextRange(), 0);

        while(lowestCommonAncestorPsiElement instanceof PsiCodeBlock == false && lowestCommonAncestorPsiElement instanceof PsiMethod == false && lowestCommonAncestorPsiElement instanceof PsiJavaFile == false)
        {
            lowestCommonAncestorPsiElement = lowestCommonAncestorPsiElement.getParent();
        }


        if(lowestCommonAncestorPsiElement instanceof PsiJavaFile)
        {
            clearAllHighlightRange();
            return;
        }




        JPanel granularitySliderPanel = new GranularitySliderPanel(this);
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



                //PsiCodeBlock codeFromText = PsiElementFactory.SERVICE.getInstance(project).createCodeBlockFromText("", null);
                //System.out.print(codeFromText.getText());
                //ChunkTextToRelatedGroup_basic(codeFromText);


                ////////////////////////
        /*PsiClass psiClass = getPsiClassFromContext(e);
        GenerateDialog dlg = new GenerateDialog(psiClass);
        dlg.show();
        if(dlg.isOK()){
            generate(psiClass, dlg.getFields());
        }*/
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

    public ArrayList<Integer> preProcessBreakDownWithDifferentThresholds()
    {
        ArrayList<Integer> thresholds = new ArrayList<>();
        int value = 1;
        int lastN = -1;
        while(value<10)
        {
            tryToBreakDownSelectedCode(value);
            if(listOfStronglyRelatedPsiElements!=null && lastN!=listOfStronglyRelatedPsiElements.size())
            {
                thresholds.add(value);
                lastN = listOfStronglyRelatedPsiElements.size();
            }
            value++;
        }
        return thresholds;
    }

    public void tryToBreakDownSelectedCode(int sliderValue)
    {
        if( ASIAAction.this.lowestCommonAncestorPsiElement instanceof PsiCodeBlock)
        {
            listOfStronglyRelatedPsiElements = breakDownPsiElementToRelatedParts((PsiCodeBlock) ASIAAction.this.lowestCommonAncestorPsiElement, 0, sliderValue);
            highlightAllDiscoveredCodeSnippet();
        }
        else
        {
            listOfStronglyRelatedPsiElements = null;
            System.out.print("Common parent is not PsiCodeBlock");
        }
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
        /////////////////////////////// CONST
        int MIN_BLOCK_SIZE = 1;
        int MAX_ACCEPTABLE_NOISE = 1;
        /////////////////////////////// TO FILL
        int nStatements = -1;
        // For following arrays, index=0 stores the information regarding the 'index'th PsiElement that we are visiting
        ArrayList<PsiElement> psiElements = new ArrayList<>();
        ArrayList<ArrayList<String>> psiElementIdentifiers = new ArrayList<>();
        ArrayList<ArrayList<Integer>> directlyRelatedAdjMatrix= new ArrayList<>();
        ArrayList<ArrayList<Integer>> indirectlyRelatedAdjMatrix= new ArrayList<>();
        ///////////////////////////////
        PsiElement p = _psiCodeBlock.getFirstChild();
        while(p!=null)
        {
            if (p instanceof PsiStatement)
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
            ArrayList<PsiElement> currentStronglyRelatedPsiElements_array = new ArrayList<>();
            currentStronglyRelatedPsiElements_array.add(_psiCodeBlock);
            stronglyRelatedPsiElements.add(new StronglyRelatedPsiElements(nestedLevel, currentStronglyRelatedPsiElements_array, this));
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



            if(nStatements<MIN_BLOCK_SIZE)
                return stronglyRelatedPsiElements;

            while(currentPsiElementIndex<nStatements)
            {
                int latestContinuesIndex = findContinuesLineNumberSeries(indirectlyRelatedAdjMatrix.get(currentPsiElementIndex), currentPsiElementIndex, MAX_ACCEPTABLE_NOISE);


                ArrayList<PsiElement> currentStronglyRelatedPsiElements_array = new ArrayList<>();
                for(int i=currentPsiElementIndex; i<=latestContinuesIndex; i++)
                    currentStronglyRelatedPsiElements_array.add(psiElements.get(i));
                currentPsiElementIndex = latestContinuesIndex+1;

                PsiElement theOnlyElement = currentStronglyRelatedPsiElements_array.get(0);
                PsiBlockStatement childBlockStarement = getDirectPsiBlockStatementIfExits(theOnlyElement);

                if (currentStronglyRelatedPsiElements_array.size() == 1 && childBlockStarement!=null)
                {

                    if (childBlockStarement != null)
                    {
                        ArrayList<StronglyRelatedPsiElements> result = breakDownPsiElementToRelatedParts((PsiCodeBlock) childBlockStarement.getFirstChild(), nestedLevel + 1, maxAccetableVariableDistance);

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

    private PsiBlockStatement getDirectPsiBlockStatementIfExits(PsiElement element)
    {
        PsiElement[] children = element.getChildren();
        for(int i=0; i<children.length; i++)
        {
            if(children[i] instanceof PsiBlockStatement)
                return (PsiBlockStatement)children[i];
        }
        return null;
    }


    private void clearAllHighlightRange()
    {
        editor.getMarkupModel().removeAllHighlighters();
    }


    /*private void highlightRange(int startOffset, int endOffset, int nestedLevel, boolean b, int highlightZ)
    {
        Color color = getColor(nestedLevel, b);
        TextAttributes myTextAtt = new TextAttributes(Color.BLACK, color, Color.BLACK, EffectType.ROUNDED_BOX, Font.ITALIC);
        RangeHighlighter rh = editor.getMarkupModel().addRangeHighlighter(startOffset, endOffset, highlightZ, myTextAtt, HighlighterTargetArea.EXACT_RANGE);

    }

    private void highlightRange(TextRange r,  int nestedLevel, boolean b, int highlightZ)
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
        e.getPresentation().setVisible((project != null && editor != null  && editor.getSelectionModel().hasSelection()));

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
