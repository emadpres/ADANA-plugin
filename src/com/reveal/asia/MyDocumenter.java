package com.reveal.asia;

import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;

/**
 * Created by emadpres on 8/12/17.
 */
public class MyDocumenter
{
    ///////// CONST
    static final public int FIRST_NESTED_LEVEL_INDEX = 0;
    static final public int MIN_BLOCK_SIZE = 3;
    static final public int INF_NESTED = 100;
    /////////
    static private MyDocumenter instance = new MyDocumenter();
    private MyDocumenter(){}
    static MyDocumenter getInstance()
    {
        return instance;
    }

    public ArrayList<StronglyRelatedPsiElements> breakdownScopes(PsiElement startingPsiElement, PsiElement endingPsiElement, int _currentNestedLevel, int _maxNestedLevel)
    {
        //Precondition: "startingPsiElement" and "endingPsiElement" must be in same level;
        assert startingPsiElement.getParent() == endingPsiElement.getParent();
        if(_currentNestedLevel>_maxNestedLevel)
            return null;
        //////////////////

        ArrayList<PsiElement> psiElements = MyPsiUtils.getInstance().createListOfMeaningfulElements(startingPsiElement, endingPsiElement);
        if(MyPsiUtils.getInstance().countNMeaninfulNodeInWholeSubtree(psiElements)<MIN_BLOCK_SIZE)
            return null;
        int nStatements = psiElements.size();

        ArrayList<StronglyRelatedPsiElements> stronglyRelatedPsiElements = new ArrayList<>();
        stronglyRelatedPsiElements.add(new StronglyRelatedPsiElements(_currentNestedLevel,psiElements));


        for(int i=0; i<nStatements; i++)
        {
            PsiElement currentNode = psiElements.get(i);
            MyPsiUtils.getInstance().removePsiElementsCommentNodes(currentNode);

            PsiElement[] codeBlocksInSubtree = PsiTreeUtil.collectElements(currentNode, new PsiElementFilter() {
                public boolean isAccepted(PsiElement e) {
                    if(e instanceof PsiCodeBlock)
                        return true;
                    return false;
                }
            });

            for(int j=0; j<codeBlocksInSubtree.length; j++)
            {
                PsiCodeBlock childCodeBlock = (PsiCodeBlock)(codeBlocksInSubtree[j]);
                int nl = FIRST_NESTED_LEVEL_INDEX+1;
                PsiElement p = childCodeBlock;
                while(p!=null && p.getParent()!=currentNode.getParent())
                {
                    p = p.getParent();
                    if(p instanceof PsiCodeBlock)
                        nl++;
                }
                nl = _currentNestedLevel+nl;
                if(nl>_maxNestedLevel)
                    continue;
                ArrayList<PsiElement> codeBlockPsiElements = MyPsiUtils.getInstance().createListOfMeaningfulElements(childCodeBlock.getFirstBodyElement(), childCodeBlock.getLastBodyElement());
                if(MyPsiUtils.getInstance().countNMeaninfulNodeInWholeSubtree(codeBlockPsiElements)<MIN_BLOCK_SIZE)
                    continue;
                stronglyRelatedPsiElements.add(new StronglyRelatedPsiElements(nl,codeBlockPsiElements));
            }


            /*PsiElement[] children = currentNode.getChildren();
            for(int j=0; j<children.length; j++)
            {
                if(children[j] instanceof PsiBlockStatement)
                {
                    PsiCodeBlock childCodeBlock = (PsiCodeBlock)(children[j].getFirstChild());
                    ArrayList<StronglyRelatedPsiElements> t = breakdownScopes(childCodeBlock.getFirstBodyElement(), childCodeBlock.getLastBodyElement(), _currentNestedLevel+1, _maxNestedLevel);
                    if(t!=null)
                        stronglyRelatedPsiElements.addAll(t);
                }

            }*/

        }


        return stronglyRelatedPsiElements;
    }

    public ArrayList<StronglyRelatedPsiElements> breakdownAndHighlight(PsiElement firstLowestSameLevelPsiElement, PsiElement secondLowestSameLevelPsiElement, int _maxNestedLevel, MarkupModel documentMarkupModel)
    {
        ArrayList<StronglyRelatedPsiElements> listOfStronglyRelatedPsiElements = MyDocumenter.getInstance().breakdownScopes(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement, FIRST_NESTED_LEVEL_INDEX, _maxNestedLevel);
        if(listOfStronglyRelatedPsiElements==null || listOfStronglyRelatedPsiElements.size()==0)
        {
            OddsAndEnds.showInfoBalloon("ADANA Plugin", "Not enough code is selected (Err2).");
            return null;
        }
        EditorHighlightHelper.highlightAllDiscoveredCodeSnippet(documentMarkupModel, listOfStronglyRelatedPsiElements, true);
        return listOfStronglyRelatedPsiElements;
    }

    public boolean retrieveAndAddDescriptions(ArrayList<StronglyRelatedPsiElements> listOfStronglyRelatedPsiElements)
    {
        if(listOfStronglyRelatedPsiElements==null || listOfStronglyRelatedPsiElements.size()==0)
            return false;

        ArrayList<Pair<String, StronglyRelatedPsiElements>> retrievedDescriptions  = new ArrayList<>();

        for(int g=0;g<listOfStronglyRelatedPsiElements.size();g++)
        {
            StronglyRelatedPsiElements s = listOfStronglyRelatedPsiElements.get(g);
            // Here we retreive comment for each part but we SHOULD NOT add it in editor to the this code until end of this method.
            // Why? Read retrieveDescription() first line comment on the way we extract code from this StronglyRelatedPsiElements obj.
            boolean successful = s.retrieveDescription();
            if(successful)
            {
                String retrievedCodeDescription = s.getRetrievedCodeDescriptionFromServer();
                int duplicateIndex = -1;
                boolean preferMySelf = true;
                for(int i=0; i<retrievedDescriptions.size(); i++)
                {
                    if(retrievedDescriptions.get(i).getFirst().equals(retrievedCodeDescription))
                    {
                        //Duplicate. Prefer the one with lower nested level.
                        duplicateIndex = i;
                        if(s.getNestedLevel()>retrievedDescriptions.get(i).getSecond().getNestedLevel())
                        {
                            preferMySelf = false;
                            //The one which is in the list as lower nested level. Good!
                            s.removeAddedCommentPsiElement(); //ignore nested one.
                        }
                        else
                        {
                            preferMySelf = true;
                            break;
                        }

                    }
                }
                if(duplicateIndex!=-1)
                {
                    if(preferMySelf)
                    {
                        retrievedDescriptions.get(duplicateIndex).getSecond().ignoreRetrievedComment();
                        //replace in array
                        retrievedDescriptions.remove(duplicateIndex);
                        retrievedDescriptions.add(new Pair<>(retrievedCodeDescription, s));
                    }
                    else
                    {
                        s.ignoreRetrievedComment();
                    }

                }
                else
                    retrievedDescriptions.add(new Pair<>(retrievedCodeDescription, s));
            }
        }


        for(int g=0;g<listOfStronglyRelatedPsiElements.size();g++)
        {
            StronglyRelatedPsiElements s = listOfStronglyRelatedPsiElements.get(g);
            if(s.getRetrievedCodeDescriptionFromServer()!="")
                s.addCommentPsiElement();
        }

        return (retrievedDescriptions.size()!=0);
    }

    public void getDescriptionsAndHighlightAny(ArrayList<StronglyRelatedPsiElements> listOfStronglyRelatedPsiElements, MarkupModel documentMarkupModel)
    {
        boolean anyFound = retrieveAndAddDescriptions(listOfStronglyRelatedPsiElements);
        if(!anyFound)
        {
            EditorHighlightHelper.clearAllHighlightRange(documentMarkupModel);
            OddsAndEnds.showInfoBalloon("ADANA Plugin", "Don't be disappointed, but unfortunately no result.");
        }
        else
        {
            EditorHighlightHelper.highlightAllDiscoveredCodeSnippetIfHasComment(documentMarkupModel, listOfStronglyRelatedPsiElements, true);
        }
    }
}
