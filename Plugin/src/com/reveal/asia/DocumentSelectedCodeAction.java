package com.reveal.asia;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.*;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;


/**
 * Created by emadpres on 7/25/17.
 */
public class DocumentSelectedCodeAction extends AnAction
{
    /////////////////////////////// CONST


    final int MAX_ACCEPTABLE_NOISE = 1;
    final int L_MIN = 3, L_MAX = 20; //for mini-tool
    ///////////////////////////////
    private Project project = null;
    PsiElement lowestCommonAncestorPsiElement = null;
    PsiElement firstLowestSameLevelPsiElement=null,secondLowestSameLevelPsiElement=null;
    ///////
    ArrayList<StronglyRelatedPsiElements> listOfStronglyRelatedPsiElements = null;
    final int WHOLE_BLOCK_THRESHOLD_MAGIC_NUMBER = -1;
    /////

    //////// UI ++
    public JBPopup sliderPopup  = null;
    /////// UI --

    @Override
    public void actionPerformed(AnActionEvent e)
    {

        project = e.getRequiredData(CommonDataKeys.PROJECT);
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        listOfStronglyRelatedPsiElements = null;

        Pair<PsiElement, PsiElement> pair = GetFirstAndLastPsiElementsInSelectedArea(e);
        editor.getSelectionModel().removeSelection();
        firstLowestSameLevelPsiElement = pair.first;
        secondLowestSameLevelPsiElement = pair.second;
        listOfStronglyRelatedPsiElements = MyDocumenter.getInstance().breakdownScopes(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement, MyDocumenter.FIRST_NESTED_LEVEL_INDEX, MyDocumenter.INF_NESTED);

        if(listOfStronglyRelatedPsiElements==null || listOfStronglyRelatedPsiElements.size()==0)
        {
            OddsAndEnds.showInfoBalloon("ADANA Plugin", "Not enough code is selected.");
            return;
        }

        int maxNestedLevelInSelectedCode = MyDocumenter.FIRST_NESTED_LEVEL_INDEX;
        for(int i=0; i<listOfStronglyRelatedPsiElements.size();i++)
            if(listOfStronglyRelatedPsiElements.get(i).getNestedLevel()>maxNestedLevelInSelectedCode)
                maxNestedLevelInSelectedCode = listOfStronglyRelatedPsiElements.get(i).getNestedLevel();


        JPanel granularitySliderPanel = new GranularitySliderPanel(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement,this, maxNestedLevelInSelectedCode, editor.getMarkupModel());
        ComponentPopupBuilder componentPopupBuilder = JBPopupFactory.getInstance().createComponentPopupBuilder(granularitySliderPanel, null);
        sliderPopup = componentPopupBuilder.createPopup();

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
                    EditorHighlightHelper.clearAllHighlightRange(editor.getMarkupModel());
            }
        });



        if(true)
            return;


        /*ArrayList<Integer> thresholds = preProcessBreakDownWithDifferentThresholds();
        if(thresholds.size()==0)
        {
            // Not enough code is selected.
            //System.out.print("Not enough code is selected.");
            return;
        }*/

        /*JPanel  granularitySliderPanel = new GranularitySliderPanel(this, thresholds);
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
                                            EditorHighlightHelper.clearAllHighlightRange(editor.getMarkupModel());
                                    }
                                });*/
    }

    private Pair<PsiElement, PsiElement> GetFirstAndLastPsiElementsInSelectedArea(AnActionEvent e)
    {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final SelectionModel selectionModel = editor.getSelectionModel();
        int selectionStartOffset = selectionModel.getSelectionStart();
        int selectionEndOffset = selectionModel.getSelectionEnd();
        //showStartingEndingParentOfSelection();

        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        PsiElement selectionStartPsiElement = psiFile.findElementAt(selectionStartOffset);
        PsiElement selectionEndPsiElement = psiFile.findElementAt(selectionEndOffset);


        //lowestCommonAncestorPsiElement = findLowestCommonAncestor(selectionStartPsiElement, selectionEndPsiElement);

        EditorHighlightHelper.clearAllHighlightRange(editor.getMarkupModel());


        Pair<PsiElement, PsiElement> pair = findLowestSameLevelStatement(selectionStartPsiElement, selectionEndPsiElement);
        return pair;
    }

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

    private Pair<PsiElement, PsiElement> findLowestSameLevelStatement(PsiElement selectionStartPsiElement, PsiElement selectionEndPsiElement)
    {
        if(selectionStartPsiElement==selectionEndPsiElement)
            return new Pair<>(selectionStartPsiElement, selectionEndPsiElement);

        Pair<PsiElement, PsiElement> pair = findLowestSameLevelPsiElements(selectionStartPsiElement, selectionEndPsiElement);

        firstLowestSameLevelPsiElement = pair.first;
        while(firstLowestSameLevelPsiElement!=null && (firstLowestSameLevelPsiElement instanceof PsiWhiteSpace || firstLowestSameLevelPsiElement instanceof PsiComment))
            firstLowestSameLevelPsiElement = firstLowestSameLevelPsiElement.getNextSibling();

        secondLowestSameLevelPsiElement = pair.second;
        while (secondLowestSameLevelPsiElement != null && (secondLowestSameLevelPsiElement instanceof PsiWhiteSpace || secondLowestSameLevelPsiElement instanceof PsiComment))
            secondLowestSameLevelPsiElement = secondLowestSameLevelPsiElement.getPrevSibling();

        if(MyPsiUtils.getInstance().isAMinumumMeaningfullNode(firstLowestSameLevelPsiElement) == false || MyPsiUtils.getInstance().isAMinumumMeaningfullNode(secondLowestSameLevelPsiElement) ==false)
        {
            PsiElement lowestStatement = findLowestStatement(firstLowestSameLevelPsiElement);
            return new Pair<>(lowestStatement, lowestStatement);
        }
        else
            return new Pair<>(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement);
    }

    private Pair<PsiElement, PsiElement> findLowestSameLevelPsiElements(PsiElement selectionStartPsiElement, PsiElement selectionEndPsiElement)
    {
        if(selectionStartPsiElement==selectionEndPsiElement) // || selectionStartPsiElement.getParent()==selectionEndPsiElement.getParent()
        {
            return new Pair<>(selectionStartPsiElement, selectionEndPsiElement);
        }

        PsiElement lowestCommonAncestor = findLowestCommonAncestor(selectionStartPsiElement, selectionEndPsiElement);

        if(lowestCommonAncestor==selectionStartPsiElement || lowestCommonAncestor==selectionEndPsiElement)
            return new Pair<>(lowestCommonAncestor, lowestCommonAncestor);

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
        while(e!=null  && MyPsiUtils.getInstance().isAMinumumMeaningfullNode(e) == false) //&& e instanceof PsiExpressionStatement==false && e instanceof CompositePsiElement == false
            e = e.getParent();

        return e;
    }

    private Pair<PsiElement, PsiElement> getPsiElementByLineNumber(int startingLine, int endingLine, PsiFile psiFile)
    {
        Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);


        startingLine--;
        int selectionStartOffset = document.getLineStartOffset(startingLine);
        PsiElement selectionStartPsiElement = psiFile.findElementAt(selectionStartOffset);
        if (document.getLineNumber(selectionStartPsiElement.getTextOffset()) != startingLine)
            // to handle "PsiWhiteSpace" which is first character in line L, but if you ask what's your line it says L-1. Because half of it is in L-1 and half is in L.
            selectionStartPsiElement = selectionStartPsiElement.getNextSibling();



        endingLine--;
        int selectionEndOffset = document.getLineEndOffset(endingLine);
        PsiElement selectionEndPsiElement = psiFile.findElementAt(selectionEndOffset);
        if(document.getLineNumber(selectionEndPsiElement.getTextOffset()) != endingLine)
            selectionEndPsiElement = selectionEndPsiElement.getPrevSibling();

        //highlightRange(selectionStartOffset, selectionEndOffset, Color.GREEN);
        //highlightRange(selectionStartPsiElement.getTextRange().getStartOffset(), selectionEndPsiElement.getTextRange().getEndOffset(), Color.GREEN);

        return new Pair<>(selectionStartPsiElement, selectionEndPsiElement);

    }

    private String getMethodNameFromLineNumber(int methodStartingLineNumber, PsiFile psiFile)
    {
        Pair<PsiElement,PsiElement> p  = getPsiElementByLineNumber(methodStartingLineNumber, methodStartingLineNumber, psiFile);
        PsiElement m = p.first;
        while(m.getParent() instanceof PsiClass==false) //This PsiClass might be different from "topLevelClass" in above line. It could refer to an inner class.
            m = m.getParent();
        //System.out.print("Method: "+ ((PsiMethod)m).getName());
        if(m instanceof PsiMethod==false)
            return null;
        else
            return ((PsiMethod)m).getName();
    }

    private void processMethod(int methodStartingLine, int methodEndingLine, PsiFile psiFile, String currentProjectName, String currentFileName, String currentMethodName)
    {
        //if(methodEndingLine-methodStartingLine+1 < L_MIN )
        //    return; //Comment: because we consider "nLines" for sliding window. the fact a expanded part has enough statement will be calculated using AST.

        String currentLFolder = "/Users/emadpres/IdeaProjects/expandedCode/L";

        String currentMethodFilePrefix = currentFileName+"."+currentMethodName;

        File checkFileExists = new File("/Users/emadpres/IdeaProjects/expandedCode/L20/"+currentProjectName+"/"+currentMethodFilePrefix+".0.java");
        if(checkFileExists.exists())
        {
            int avoidingDuplicateMethodNameInSameClassSuffixNumber = -1;
            do
            {
                avoidingDuplicateMethodNameInSameClassSuffixNumber++;
                checkFileExists = new File("/Users/emadpres/IdeaProjects/expandedCode/L20/"+currentProjectName+"/"+currentMethodFilePrefix+"_"+Integer.toString(avoidingDuplicateMethodNameInSameClassSuffixNumber)+".0.java");
            }while(checkFileExists.exists());
            currentMethodFilePrefix = currentMethodFilePrefix+"_"+Integer.toString(avoidingDuplicateMethodNameInSameClassSuffixNumber);
        }



        for(int l=L_MIN; l<=L_MAX; l++)
        {
            int thisMethod_thisL_codeIndex = 0;
            currentLFolder =  "/Users/emadpres/IdeaProjects/expandedCode/L"+Integer.toString(l)+"/"+currentProjectName+"/";
            for(int window_start = methodStartingLine; window_start<=methodEndingLine; window_start++)
            {
                int window_end = Math.min(window_start+l-1,methodEndingLine);
                String expanded_code = processLineRange(window_start, window_end, psiFile);
                if(expanded_code!=null)
                {
                    String currentLFile = currentMethodFilePrefix +"."+ Integer.toString(thisMethod_thisL_codeIndex) + ".java";
                    OddsAndEnds.simpleWriteToFile(currentLFolder + currentLFile, expanded_code);
                    thisMethod_thisL_codeIndex++;
                }
            }
        }
    }

    private String convertPsiELementsToCode(ArrayList<PsiElement> psiElements)
    {
        String code = "";
        for(int i=0;i<psiElements.size();i++)
            code += psiElements.get(i).getText();
        //code = code.replace("\n"," ");
        return code;
    }

    private String processLineRange(int startingLine, int endingLine, PsiFile psiFile)
    {
        Pair<PsiElement, PsiElement> elementByLineNumber = getPsiElementByLineNumber(startingLine, endingLine, psiFile);
        PsiElement selectionStartPsiElement = elementByLineNumber.first;
        PsiElement selectionEndPsiElement = elementByLineNumber.second;
        Pair<PsiElement, PsiElement> pair = findLowestSameLevelStatement(selectionStartPsiElement, selectionEndPsiElement);
        PsiElement firstLowestSameLevelPsiElement = pair.first;
        PsiElement secondLowestSameLevelPsiElement = pair.second;
        ArrayList<PsiElement> psiElements = MyPsiUtils.getInstance().createListOfMeaningfulElements(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement);

        if(MyPsiUtils.getInstance().countNMeaninfulNodeInWholeSubtree(psiElements)<MyDocumenter.MIN_BLOCK_SIZE)
        {
            //System.out.print("Not Enought Code");
            return null;
        }
        else
        {
            //highlightRange(firstLowestSameLevelPsiElement.getTextRange().getStartOffset(), secondLowestSameLevelPsiElement.getTextRange().getEndOffset(), Color.CYAN);

            String expanded_code = convertPsiELementsToCode(psiElements);

            //final SelectionModel selectionModel = editor.getSelectionModel();
            //selectionModel.setSelection(firstLowestSameLevelPsiElement.getTextRange().getStartOffset(), secondLowestSameLevelPsiElement.getTextRange().getEndOffset());
            //String expanded_code = selectionModel.getSelectedText();

            return expanded_code;
        }
    }

    public void actionPerformed_minitool(AnActionEvent e)
    {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        project = e.getRequiredData(CommonDataKeys.PROJECT);
        String currentProjectName = project.getName();

        for(int l=L_MIN; l<=L_MAX; l++)
        {
            String lFolder = "/Users/emadpres/IdeaProjects/expandedCode/L" + Integer.toString(l) + "/" + currentProjectName + "/";
            new File(lFolder).mkdirs(); //once is enough
        }

        for (VirtualFile virtualFile : FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project)))
        {
            if(virtualFile.getExtension()==null || !virtualFile.getExtension().equals("java"))
                continue;
            PsiJavaFile aPsiFile = (PsiJavaFile)PsiManager.getInstance(project).findFile(virtualFile);
            if(aPsiFile==null)
                continue;
            Document document = PsiDocumentManager.getInstance(project).getDocument(aPsiFile);


            String currentFileName = aPsiFile.getName();
            currentFileName = currentFileName.substring(0, currentFileName.length()-5); //remove .java
            if(currentFileName.endsWith("Test"))
                continue;

            ArrayList<PsiMethod> allMethods = MyPsiUtils.getInstance().getAllClassLevelMethodsInFile(aPsiFile);

            for(int m=0; m<allMethods.size();m++)
            {

                PsiMethod mm = allMethods.get(m);
                String currentMethodName = mm.getName();
                //System.out.print(">"+currentFileName+">"+currentMethodName);


                PsiCodeBlock methodBody = mm.getBody();
                if(methodBody==null)
                    continue;//Method Signature Declaration only.
                PsiElement lB = methodBody.getLBrace();
                PsiElement rB = methodBody.getRBrace();
                int methodStartingLine = document.getLineNumber(lB.getTextOffset())+1;
                methodStartingLine++;
                int methodEndingLine = document.getLineNumber(rB.getTextOffset())+1;
                methodEndingLine--;

                if(methodEndingLine<methodStartingLine) continue;
                //System.out.print(">"+methodStartingLine+":"+methodEndingLine+"\n");

                processMethod(methodStartingLine, methodEndingLine, aPsiFile, currentProjectName, currentFileName, currentMethodName);
            }

        }

        Messages.showInfoMessage(project,"Process Done!", "Done!");

    }

    private boolean showStartingEndingParentOfSelection(MarkupModel editorMarkupModel)
    {
        if(firstLowestSameLevelPsiElement==null || secondLowestSameLevelPsiElement==null)
        {
            //System.out.print("starting or ending statement is null");
            return true;
        }

        EditorHighlightHelper.clearAllHighlightRange(editorMarkupModel);
        if(firstLowestSameLevelPsiElement!=secondLowestSameLevelPsiElement)
        {
            EditorHighlightHelper.highlightRange(editorMarkupModel, firstLowestSameLevelPsiElement.getTextRange().getStartOffset(), firstLowestSameLevelPsiElement.getTextRange().getEndOffset(), Color.RED);
            EditorHighlightHelper.highlightRange(editorMarkupModel, secondLowestSameLevelPsiElement.getTextRange().getStartOffset(), secondLowestSameLevelPsiElement.getTextRange().getEndOffset(), Color.YELLOW);
        }
        else
        {
            EditorHighlightHelper.highlightRange(editorMarkupModel, secondLowestSameLevelPsiElement.getTextRange().getStartOffset(), secondLowestSameLevelPsiElement.getTextRange().getEndOffset(), Color.ORANGE);
        }

        PsiElement parentOfBoth = firstLowestSameLevelPsiElement.getParent();
        if(secondLowestSameLevelPsiElement.getParent()!=parentOfBoth)
        {
            EditorHighlightHelper.clearAllHighlightRange(editorMarkupModel);
            EditorHighlightHelper.highlightRange(editorMarkupModel, parentOfBoth.getTextRange().getStartOffset(), parentOfBoth.getTextRange().getEndOffset(), Color.BLACK);
        }
        else
            EditorHighlightHelper.highlightRange(editorMarkupModel, parentOfBoth.getTextRange().getStartOffset(), parentOfBoth.getTextRange().getEndOffset(), Color.CYAN);
        return false;
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
            tryToBreakDownSelectedCode(value, null);
            if(listOfStronglyRelatedPsiElements!=null && listOfStronglyRelatedPsiElements.size()!=0 && exists_f(possibleResults, listOfStronglyRelatedPsiElements)==false)
            {
                thresholds.add(value);
                possibleResults.add(listOfStronglyRelatedPsiElements);
            }
        }
        ///////////// 2/2
        tryToBreakDownSelectedCode(WHOLE_BLOCK_THRESHOLD_MAGIC_NUMBER, null);
        if(listOfStronglyRelatedPsiElements!=null && listOfStronglyRelatedPsiElements.size()!=0 && exists_f(possibleResults, listOfStronglyRelatedPsiElements)==false)
        {
            thresholds.add(WHOLE_BLOCK_THRESHOLD_MAGIC_NUMBER);
            possibleResults.add(listOfStronglyRelatedPsiElements);
        }


        return thresholds;
    }

    public void tryToBreakDownSelectedCode(int sliderValue, MarkupModel editorMarkupModel)
    {
        if(firstLowestSameLevelPsiElement!=null && secondLowestSameLevelPsiElement!=null)
        {
            listOfStronglyRelatedPsiElements = breakDownPsiElementToRelatedParts(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement, 0, sliderValue);
            EditorHighlightHelper.highlightAllDiscoveredCodeSnippet(editorMarkupModel, listOfStronglyRelatedPsiElements, true);
        }
        else
        {
            listOfStronglyRelatedPsiElements = null;
            //System.out.print("Starting or Ending PsiElement is null");
        }


        /*if( DocumentSelectedCodeAction.this.lowestCommonAncestorPsiElement instanceof PsiCodeBlock)
        {
            listOfStronglyRelatedPsiElements = breakDownPsiElementToRelatedParts((PsiCodeBlock) DocumentSelectedCodeAction.this.lowestCommonAncestorPsiElement, 0, sliderValue);
            EditorHighlightHelper.highlightAllDiscoveredCodeSnippet(editor.getMarkupModel(), listOfStronglyRelatedPsiElements, true);

        }
        else
        {
            listOfStronglyRelatedPsiElements = null;
            System.out.print("Common parent is not PsiCodeBlock");
        }*/
    }

    /*public void createCodeDescriptionPopup(StronglyRelatedPsiElements s)
    {
        CodeDescriptionPopup c = new CodeDescriptionPopup(s, this);
        c.getComponent().showInBestPositionFor(editor);
    }*/

    private ArrayList<StronglyRelatedPsiElements> breakDownPsiElementToRelatedParts(PsiCodeBlock _psiCodeBlock, int nestedLevel, int maxAccetableVariableDistance)
    {
        return breakDownPsiElementToRelatedParts(_psiCodeBlock.getFirstBodyElement(), _psiCodeBlock.getLastBodyElement(), nestedLevel, maxAccetableVariableDistance);
    }

    private ArrayList<StronglyRelatedPsiElements> breakDownPsiElementToRelatedParts(PsiElement startingPsiElement, PsiElement endingPsiElement, int nestedLevel, int maxAccetableVariableDistance)
    {
        //Precondition: "startingPsiElement" and "endingPsiElement" must be in same level;
        assert startingPsiElement.getParent() == endingPsiElement.getParent();
        /////////////////////////////// TO FILL
        int nStatements = -1;
        // For following arrays, index=0 stores the information regarding the 'index'th PsiElement that we are visiting
        ArrayList<PsiElement> psiElements = new ArrayList<>();
        ArrayList<ArrayList<String>> psiElementIdentifiers = new ArrayList<>();
        ArrayList<ArrayList<Integer>> directlyRelatedAdjMatrix= new ArrayList<>();
        ArrayList<ArrayList<Integer>> indirectlyRelatedAdjMatrix= new ArrayList<>();
        ///////////////////////////////


        psiElements = MyPsiUtils.getInstance().createListOfMeaningfulElements(startingPsiElement, endingPsiElement);
        nStatements = psiElements.size();
        for(int i=0; i<nStatements; i++)
        {
            ArrayList<String> currentPsiElement_identifiers = getIdentifiersFromPsiElement(psiElements.get(i));
            psiElementIdentifiers.add(currentPsiElement_identifiers);
        }


        if(maxAccetableVariableDistance==WHOLE_BLOCK_THRESHOLD_MAGIC_NUMBER)
        {
            ArrayList<StronglyRelatedPsiElements> stronglyRelatedPsiElements = new ArrayList<>(); //stronglyRelatedCodeSnippet;

            if(MyPsiUtils.getInstance().countNMeaninfulNodeInWholeSubtree(psiElements)<MyDocumenter.MIN_BLOCK_SIZE)
                return stronglyRelatedPsiElements;
            stronglyRelatedPsiElements.add(new StronglyRelatedPsiElements(nestedLevel, psiElements));
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



            if(MyPsiUtils.getInstance().countNMeaninfulNodeInWholeSubtree(psiElements)<MyDocumenter.MIN_BLOCK_SIZE && (psiElements.size()==0 || getDirectPsiCodeBlockIfExits(psiElements.get(0))==null))
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
                else if(countNStatementsInPsiElement(currentStronglyRelatedPsiElements_array)>=MyDocumenter.MIN_BLOCK_SIZE)
                    stronglyRelatedPsiElements.add(new StronglyRelatedPsiElements(nestedLevel, currentStronglyRelatedPsiElements_array));
            }
            return stronglyRelatedPsiElements;
        }
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
        project = e.getRequiredData(CommonDataKeys.PROJECT);

        Pair<PsiElement, PsiElement> pair = GetFirstAndLastPsiElementsInSelectedArea(e);
        firstLowestSameLevelPsiElement = pair.first;
        secondLowestSameLevelPsiElement = pair.second;
        ArrayList<PsiElement> psiElements = MyPsiUtils.getInstance().createListOfMeaningfulElements(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement);
        if(MyPsiUtils.getInstance().countNMeaninfulNodeInWholeSubtree(psiElements)<MyDocumenter.MIN_BLOCK_SIZE)
        {
            e.getPresentation().setEnabled(false);
        }
        else
        {
            e.getPresentation().setEnabled(true);
        }
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
        PsiClass psiClass = PsiTreeUtil.getTopmostParentOfType(elementAt, PsiClass.class);
        return psiClass;
    }
}
