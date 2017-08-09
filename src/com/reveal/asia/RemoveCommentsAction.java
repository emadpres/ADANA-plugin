package com.reveal.asia;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;

/**
 * Created by emadpres on 8/9/17.
 */
public class RemoveCommentsAction extends AnAction
{

    @Override
    public void actionPerformed(AnActionEvent e)
    {
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        String currentProjectName = project.getName();

        for (VirtualFile virtualFile : FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project)))
        {
            if (virtualFile.getExtension() == null || !virtualFile.getExtension().equals("java"))
                continue;
            PsiJavaFile aPsiFile = (PsiJavaFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (aPsiFile == null)
                continue;
            Document document = PsiDocumentManager.getInstance(project).getDocument(aPsiFile);

            PsiElement[] allComments = PsiTreeUtil.collectElements(aPsiFile, new PsiElementFilter()
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
                new WriteCommandAction.Simple(project, aPsiFile)
                {
                    @Override
                    protected void run() throws Throwable
                    {
                        c.delete();
                    }

                }.execute();
            }

            FileDocumentManager.getInstance().saveDocument(document);
        }
        Messages.showInfoMessage(project,"Process Done!", "Done!");
    }
}
