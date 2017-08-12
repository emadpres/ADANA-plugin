package com.reveal.asia;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;

import java.util.ArrayList;

/**
 * Created by emadpres on 8/11/17.
 */
public class DocumentAllAction extends AnAction
{

    @Override
    public void actionPerformed(AnActionEvent e)
    {
        /// Now for current file
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        if(psiFile instanceof PsiJavaFile ==false)
        {
            Messages.showInfoMessage("This file is not Java!", "Abort!");
            return;
        }


        ArrayList<PsiMethod> allMethods = MyPsiUtils.getInstance().getAllClassLevelMethodsInFile((PsiJavaFile) psiFile);
        EditorHighlightHelper.clearAllHighlightRange(editor.getMarkupModel());
        for(int methodIndex=0; methodIndex<allMethods.size(); methodIndex++)
        {
            PsiMethod curMethod = allMethods.get(methodIndex);
            PsiCodeBlock methodBody = curMethod.getBody();
            if(methodBody==null)
                continue;//Method Signature Declaration only.


            PsiElement firstMethodElement = methodBody.getFirstBodyElement();
            PsiElement lastMethodElement = methodBody.getLastBodyElement();
            if(firstMethodElement==null)
                continue;

            String currentMethodName = curMethod.getName();
            ArrayList<StronglyRelatedPsiElements> listOfStronglyRelatedPsiElements = MyDocumenter.getInstance().breakdownScopes(firstMethodElement, lastMethodElement, MyDocumenter.FIRST_NESTED_LEVEL_INDEX, MyDocumenter.INF_NESTED);

            if(listOfStronglyRelatedPsiElements!=null && listOfStronglyRelatedPsiElements.size()!=0)
            {
                EditorHighlightHelper.highlightAllDiscoveredCodeSnippet(editor.getMarkupModel(), listOfStronglyRelatedPsiElements, false);
                boolean foundAny = false;
                //TODO foundAny = MyDocumenter.getInstance().retrieveAndAddDescriptions(listOfStronglyRelatedPsiElements);
                if(foundAny)
                {
                    String codeWithoutComment = "";
                    String commentedCode = "";
                    //TODO: Save to File
                }
            }

        }
    }
}
