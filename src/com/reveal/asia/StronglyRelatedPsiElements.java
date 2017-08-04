package com.reveal.asia;

import org.json.*;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.markup.*;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by emadpres on 8/3/17.
 */
class StronglyRelatedPsiElements
{
    ASIAAction asiaAction;
    ArrayList<PsiElement> psiElements = new ArrayList<>();
    ArrayList<PsiElement> newlyAddedComments = new ArrayList<>();
    String retrievedCodeDescription = "";
    //////
    RangeHighlighter rh;
    Color color;
    int nestedLevel;


    public StronglyRelatedPsiElements(int _nestedLevel, ArrayList<PsiElement> _elements, ASIAAction _asiaAction)
    {
        nestedLevel = _nestedLevel;
        psiElements = _elements;
        asiaAction = _asiaAction;
    }

    private String convertPsiElementsToText()
    {
        String code = "";
        for(int i=0;i<psiElements.size();i++)
            code =  code + psiElements.get(i).getText();
        code = code.replace("\n"," ");
        return code;
    }

    public void setColor(Color _color)
    {
        color = _color;
    }

    public void addPsiElement(PsiElement e)
    {
        psiElements.add(e);
    }

    public TextRange getTextRange()
    {
        int start = psiElements.get(0).getTextRange().getStartOffset();
        if(newlyAddedComments.size()>0)
            start = newlyAddedComments.get(0).getTextRange().getStartOffset();

        int end = psiElements.get(psiElements.size()-1).getTextRange().getEndOffset();
        return new TextRange(start, end);
    }

    public int getNestedLevel()
    {
        return nestedLevel;
    }

    private String getServerResult()
    {

        try
        {
            String urlParameters  = "selectedCode="+convertPsiElementsToText();
            //urlParameters = URLEncoder.encode(urlParameters, "UTF-8");
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );

            int    postDataLength = postData.length;
            String request        = "http://localhost:8080/ASIAWebApp/MainServlet";
            URL url            = null;
            url = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();
            return response;
        } catch (IOException e)
        {
            e.printStackTrace();
            return String.format("{\"result\": -1, \"errorDescription\":\"%s\" }",e.toString());
        }
    }

    public void fetchDescription()
    {
        String serverRes = getServerResult();

        JSONObject obj = new JSONObject(serverRes);
        int resultCode = obj.getInt("result");

        if(resultCode<0)
        {
            retrievedCodeDescription = "--->>> ERROR: "+obj.getString("errorDescription");
        }
        else
        {
            String serverSideReceivedCode = obj.getString("selectedCode");
            serverSideReceivedCode.substring(0,Math.min(30,serverSideReceivedCode.length()));
            /////
            JSONArray retrievedCloneDescriptions = obj.getJSONArray("retrievedCloneDescriptions");
            if(retrievedCloneDescriptions.length()>0)
                retrievedCodeDescription = retrievedCloneDescriptions.getString(0)+"\tReceived Code:"+serverSideReceivedCode;
            else
                retrievedCodeDescription = "--->>> No Description Found <<<----"+"\tReceived Code:"+serverSideReceivedCode;
        }

    }

    public void addCommentPsiElement()
    {
        PsiElement firstPsiElement ;
        if(psiElements.get(0) instanceof PsiCodeBlock)
            firstPsiElement = ((PsiCodeBlock) psiElements.get(0)).getFirstBodyElement().getNextSibling();
        else
            firstPsiElement = psiElements.get(0);


        PsiElement m = firstPsiElement;
        new WriteCommandAction.Simple(firstPsiElement.getProject(), firstPsiElement.getContainingFile())
        {
            @Override
            protected void run() throws Throwable
            {
                String[] commentStrs = retrievedCodeDescription.split("\n");
                int asas=0;

                for(int i=0;i<commentStrs.length;i++)
                {
                    String commentToInsert = "// "+commentStrs[i];
                    PsiComment commentFromText = JavaPsiFacade.getElementFactory(getProject()).createCommentFromText(commentToInsert, firstPsiElement);
                    newlyAddedComments.add(firstPsiElement.getParent().addBefore(commentFromText, firstPsiElement));
                }
            }

        }.execute();
    }

    public void removeAddedCommentPsiElement()
    {
        if(newlyAddedComments.size()==0)
            return;

        new WriteCommandAction.Simple(newlyAddedComments.get(0).getProject(), newlyAddedComments.get(0).getContainingFile())
        {
            @Override
            protected void run() throws Throwable
            {

                for(int i=0;i<newlyAddedComments.size();i++)
                {
                    newlyAddedComments.get(i).delete();
                }
                newlyAddedComments.clear();
            }

        }.execute();
    }

    public void performHighlighting()
    {
        TextAttributes myTextAtt = new TextAttributes(Color.BLACK, color, Color.BLACK, EffectType.ROUNDED_BOX, Font.ITALIC);
        rh = asiaAction.editor.getMarkupModel().addRangeHighlighter(getTextRange().getStartOffset(), getTextRange().getEndOffset(), HighlighterLayer.SYNTAX-100+getNestedLevel(), myTextAtt, HighlighterTargetArea.EXACT_RANGE);

        rh.setGutterIconRenderer(new GutterIconRenderer()
        {
            @Override
            public boolean equals(Object o)
            {
                return false;
            }

            @Override
            @Nullable
            public String getTooltipText() {
                return "Some Code Explanation. \n New Line?";
            }

            @Override
            public int hashCode()
            {
                return 0;
            }

            @NotNull
            @Override
            public Icon getIcon()
            {
                return Icons.ASPECT_ICON;
            }

            @Nullable
            public ActionGroup getPopupMenuActions() {
                return new ActionGroup()
                {
                    @NotNull
                    @Override
                    public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent)
                    {
                        AnAction keepAction  = new AnAction("Keep", "Keep Comment.", Icons.CHECK_ICON)
                        {
                            @Override
                            public void actionPerformed(AnActionEvent anActionEvent)
                            {
                                asiaAction.editor.getMarkupModel().removeHighlighter(rh);
                            }


                        };

                        AnAction discardAction  = new AnAction("Discard", "Remove Comment.", Icons.DELETE_ICON)
                        {
                            @Override
                            public void actionPerformed(AnActionEvent anActionEvent)
                            {
                                asiaAction.editor.getMarkupModel().removeHighlighter(rh);
                                removeAddedCommentPsiElement();
                            }


                        };

                        AnAction[] actions = new AnAction[]{keepAction, discardAction};
                        return actions;
                    }
                };
            }

            /*@Nullable
            public AnAction getClickAction() {
                return new AnAction()
                {
                    @Override
                    public void actionPerformed(AnActionEvent anActionEvent)
                    {
                        ListPopup something = JBPopupFactory.getInstance().createConfirmation("Keep?", "Yes", "No", null, 0);
                        ListPopup something = JBPopupFactory.getInstance().createListPop
                        something.show(something.getContent());
                    }
                };
            }*/

        });
    }
}
