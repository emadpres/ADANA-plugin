package com.reveal.asia;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;

/**
 * Created by emadpres on 8/12/17.
 */
public class MyPsiUtils
{

    static MyPsiUtils instance = new MyPsiUtils();

    static public MyPsiUtils getInstance()
    {
        return instance;
    }

    public ArrayList<PsiMethod> getAllClassLevelMethodsInFile(PsiJavaFile psiJavaFile)
    {
        final PsiClass[] classes = psiJavaFile.getClasses();
        ArrayList<PsiMethod> allMethods = new ArrayList<>();

        for (int cIndex = 0; cIndex < classes.length; cIndex++)
        {
            PsiElement[] allMethodsForCurrentClass = PsiTreeUtil.collectElements(classes[cIndex], new PsiElementFilter()
            {
                public boolean isAccepted(PsiElement e)
                {
                    if (e instanceof PsiMethod && e.getParent() instanceof PsiClass && e.getParent() instanceof PsiAnonymousClass == false)
                        return true;
                    return false;
                }
            });
            for (int i = 0; i < allMethodsForCurrentClass.length; i++)
                allMethods.add((PsiMethod) allMethodsForCurrentClass[i]);
        }
        return allMethods;
    }


    public ArrayList<PsiElement> createListOfMeaningfulElements(PsiElement startingPsiElement, PsiElement endingPsiElement)
    {

        ArrayList<PsiElement> psiElements = new ArrayList<>();

        if(startingPsiElement==null || endingPsiElement==null)
            return psiElements;

        PsiElement p = startingPsiElement;

        ArrayList<PsiElement> toDelete = new ArrayList<>();

        while(p!=endingPsiElement.getNextSibling())
        {
            if(p instanceof PsiComment)
                toDelete.add(p);
            else if(isAMinumumMeaningfullNode(p))
                psiElements.add(p);
            p = p.getNextSibling();
        }

        for(int i=0;i<toDelete.size(); i++)
            removePsiElementsCommentNodes(toDelete.get(i));

        return psiElements;
    }

    public void removePsiElementsCommentNodes(PsiElement psiElementToClean)
    {
        if(psiElementToClean instanceof PsiComment)
        {
            new WriteCommandAction.Simple(psiElementToClean.getProject(), psiElementToClean.getContainingFile())
            {
                @Override
                protected void run() throws Throwable
                {
                    psiElementToClean.delete();
                }

            }.execute();
        }
        else
        {

            PsiElement[] allComments = PsiTreeUtil.collectElements(psiElementToClean, new PsiElementFilter()
            {
                public boolean isAccepted(PsiElement e)
                {
                    if (e instanceof PsiComment)
                        return true;
                    return false;
                }
            });
            for (PsiElement comment : allComments)
            {
                PsiComment c = (PsiComment) comment;
                new WriteCommandAction.Simple(psiElementToClean.getProject(), psiElementToClean.getContainingFile())
                {
                    @Override
                    protected void run() throws Throwable
                    {
                        c.delete();
                    }

                }.execute();
            }
        }
    }

    public boolean isAMinumumMeaningfullNode(PsiElement e)
    {
        if(e instanceof PsiStatement == false && e instanceof PsiMethod == false && e instanceof PsiClass == false && e instanceof PsiJavaFile==false)
            return false;
        return true;
    }

    public int countNMeaninfulNodeInWholeSubtree(ArrayList<PsiElement> es)
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
                if(MyPsiUtils.getInstance().isAMinumumMeaningfullNode(e)==true && e instanceof PsiCodeBlock==false && e instanceof PsiBlockStatement==false)
                    return true;
                return false;
            }
        });
        return meaningfulElements.length;
    }
}
