package com.reveal.asia;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;

import java.io.File;
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
        //Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);

        String path_project = "/Users/emadpres/IdeaProjects/commentedMethods/"+project.getName()+"/";
        new File(path_project).mkdirs();


        int totalMethods = 0, totalCommentedMethods = 0;

        for (VirtualFile virtualFile : FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project)))
        {
            if (virtualFile.getExtension() == null || !virtualFile.getExtension().equals("java"))
                continue;
            PsiJavaFile aPsiFile = (PsiJavaFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (aPsiFile == null)
                continue;

            String currentFileName = aPsiFile.getName();
            currentFileName = currentFileName.substring(0, currentFileName.length() - 5); //remove .java
            if (currentFileName.endsWith("Test"))
                continue;

            ArrayList<PsiMethod> allMethods = MyPsiUtils.getInstance().getAllClassLevelMethodsInFile(aPsiFile);
            totalMethods += allMethods.size();

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
                    StronglyRelatedPsiElements topLevel_stronglyRelatedPsiElements = listOfStronglyRelatedPsiElements.get(0);
                    String uncommentedCode = topLevel_stronglyRelatedPsiElements.convertPsiElementsAndCommentRetrievedFromServerToText();
                    boolean foundAny = false;
                    foundAny = MyDocumenter.getInstance().retrieveAndAddDescriptions(listOfStronglyRelatedPsiElements);
                    if(foundAny)
                    {
                        totalCommentedMethods += 1;
                        /////////////// Select File name
                        File checkFileExists = new File(path_project+currentFileName+"."+currentMethodName+".java");
                        if(checkFileExists.exists())
                        {
                            int avoidingDuplicateMethodNameInSameClassSuffixNumber = -1;
                            do
                            {
                                avoidingDuplicateMethodNameInSameClassSuffixNumber++;
                                checkFileExists = new File(path_project+currentFileName+"."+currentMethodName+Integer.toString(avoidingDuplicateMethodNameInSameClassSuffixNumber)+".java");
                            }while(checkFileExists.exists());
                            currentMethodName = currentMethodName+Integer.toString(avoidingDuplicateMethodNameInSameClassSuffixNumber);
                        }
                        ///////////////// Save file
                        String methodPath_withoutSuffix = path_project+currentFileName+"."+currentMethodName;
                        String commentedCode = topLevel_stronglyRelatedPsiElements.convertPsiElementsAndCommentRetrievedFromServerToText();
                        OddsAndEnds.simpleWriteToFile(methodPath_withoutSuffix+".java",uncommentedCode);
                        OddsAndEnds.simpleWriteToFile(methodPath_withoutSuffix+"_commented.java", commentedCode);
                    }
                }
            }
        }




        Messages.showInfoMessage("All Document Commented and Saved. ("+totalCommentedMethods+" of "+totalMethods+")", "Done");
    }
}
