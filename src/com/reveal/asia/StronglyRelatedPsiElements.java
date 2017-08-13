package com.reveal.asia;

import com.intellij.openapi.util.Pair;
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
    ArrayList<PsiElement> psiElements = new ArrayList<>();
    ArrayList<PsiElement> newlyAddedComments = new ArrayList<>();
    String retrievedCodeDescriptionFromServer = "";
    //////
    RangeHighlighter rh;
    Color color;
    int nestedLevel;


    public StronglyRelatedPsiElements(int _nestedLevel, ArrayList<PsiElement> _elements)
    {
        nestedLevel = _nestedLevel;
        psiElements = _elements;
    }

    public String getRetrievedCodeDescriptionFromServer()
    {
        return retrievedCodeDescriptionFromServer;
    }

    public String getCodeDescriptionExistsInEditor()
    {
        String codeDescriptionExistsInEditor = "";
        for(int i=0; i<newlyAddedComments.size(); i++)
        {
            codeDescriptionExistsInEditor += newlyAddedComments.get(i).getText();
        }
        return codeDescriptionExistsInEditor;
        /* Solution when comments are supposed to change:
        ---------------------------------------------------
        ---------------------------------------------------
         String codeDescriptionExistsInEditor = "";
        // TODO: newlyAddedComments elements are not valid if comments are changed in editor. By changing in editor,
        // The PsiElements (in Editor Tree) are replaced with new PsiElements and we don't have reference to them.
        // Here we do a trick and access changed comment, using valid (because of unchanged) first elements.
        if(newlyAddedComments.size()==0)
            return "";
        codeDescriptionExistsInEditor += psiElements.get(0).getPrevSibling().getPrevSibling().getText()+"\n";
        return codeDescriptionExistsInEditor;
        ---------------------------------------------------
        ---------------------------------------------------
         */
    }

    public String convertPsiElementsToText()
    {
        String code = "";
        for(int i=0;i<psiElements.size();i++)
            code =  code + psiElements.get(i).getText()+"\n";
        return code;
    }

    public String convertPsiElementsAndCommentRetrievedFromServerToText()
    {
        String codeWithComment = "";
        if(getCodeDescriptionExistsInEditor()!="")
            codeWithComment += getCodeDescriptionExistsInEditor() + "\n";
        codeWithComment += convertPsiElementsToText();
        return codeWithComment;
    }

    public void setColor(Color _color)
    {
        color = _color;
    }

    /*public void addPsiElement(PsiElement e)
    {
        psiElements.add(e);
    }*/

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

    private String getServerResultLocally()
    {
        String code = convertPsiElementsToText();
        code = code.replace("\n"," ");
        String response = ASIAWrapper.getInstance().findDescriptions(code);
        return response;
    }

    private String getServerResult()
    {

        try
        {
            String code = convertPsiElementsToText();
            code = code.replace("\n"," ");

            code = URLEncoder.encode(code,"UTF-8");

            String urlParameters  = "selectedCode="+code;


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
            //OddsAndEnds.showInfoBalloon("ADANA Plugin", "Server connection refused."); //No, we may be in auto-all-document mode and UI sucks.
            e.printStackTrace();
            return String.format("{\"result\": -1, \"errorDescription\":\"%s\" }",e.toString());
        }
    }

    public void ignoreRetrievedComment()
    {
        retrievedCodeDescriptionFromServer = "";
        removeAddedCommentPsiElement();
    }

    public boolean retrieveDescription()
    {
        String serverRes = getServerResultLocally();//getServerResult();

        JSONObject obj = null;
        try
        {
            obj = new JSONObject(serverRes);
        }
        catch (JSONException e)
        {
            System.out.print("\n\n--------------- ++JSON++ ----------------\n");
            System.out.print(serverRes);
            System.out.print("\n\n--------------- --JSON-- ----------------\n");
            e.printStackTrace();
        }

        if(obj==null)
        {
            retrievedCodeDescriptionFromServer="";
            return false;
        }

        int resultCode = obj.getInt("result");

        if(resultCode<0)
        {
            retrievedCodeDescriptionFromServer = "";//"--->>> ERROR: "+obj.getString("errorDescription");
        }
        else
        {
            String serverSideReceivedCode = obj.getString("selectedCode");
            //serverSideReceivedCode = serverSideReceivedCode.substring(0,Math.min(30,serverSideReceivedCode.length()));
            /////
            JSONArray retrievedCloneDescriptions = obj.getJSONArray("retrievedCloneDescriptions");

            if(retrievedCloneDescriptions.length()>0)
            {
                if(retrievedCloneDescriptions.length()==1)
                {
                    JSONObject jsonObject = retrievedCloneDescriptions.getJSONObject(0);
                    String description = jsonObject.getString("description");
                    Double ASIA_similarity = jsonObject.getDouble("sim");
                    retrievedCodeDescriptionFromServer = description;//+ "\t\t--Received Code:" + serverSideReceivedCode;
                }
                else
                {
                    ArrayList<Pair<String, Double>> allRetrievedCodeDescription = new ArrayList<>();
                    for(int i=0; i<retrievedCloneDescriptions.length(); i++)
                    {
                        JSONObject jsonObject = retrievedCloneDescriptions.getJSONObject(i);
                        String description = jsonObject.getString("description");
                        Double ASIA_similarity = jsonObject.getDouble("sim");
                        allRetrievedCodeDescription.add(new Pair(description, ASIA_similarity));
                    }

                    DescriptionRanker ranker = new DescriptionRanker(convertPsiElementsToText(), allRetrievedCodeDescription);
                    String bestDescription = ranker.getBestDescription();
                    retrievedCodeDescriptionFromServer = bestDescription;//+ "\t\t--Received Code:" + serverSideReceivedCode;
                }

            }
            else
                retrievedCodeDescriptionFromServer = "";//"--->>> No Description Found <<<----"+"\t\t--Received Code:"+serverSideReceivedCode;
        }

        if(retrievedCodeDescriptionFromServer =="")
            return false;
        else
            return true;

    }

    public void addCommentPsiElement()
    {
        if(retrievedCodeDescriptionFromServer =="")
            return;

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
                String[] commentStrs = retrievedCodeDescriptionFromServer.split("\n");
                int asas=0;

                for(int i=0;i<commentStrs.length;i++)
                {
                    String commentToInsert = "// "+commentStrs[i];
                    PsiComment commentFromText = JavaPsiFacade.getElementFactory(getProject()).createCommentFromText(commentToInsert, firstPsiElement);
                    PsiElement newlyAddedInEditor = firstPsiElement.getParent().addBefore(commentFromText, firstPsiElement);
                    newlyAddedComments.add(newlyAddedInEditor);
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

    public void performHighlighting(MarkupModel documentMarkupModel)
    {
        TextAttributes myTextAtt = new TextAttributes(Color.BLACK, color, Color.BLACK, EffectType.ROUNDED_BOX, Font.ITALIC);
        rh = documentMarkupModel.addRangeHighlighter(getTextRange().getStartOffset(), getTextRange().getEndOffset(), HighlighterLayer.SYNTAX-100+getNestedLevel(), myTextAtt, HighlighterTargetArea.EXACT_RANGE);

        rh.setGutterIconRenderer(new GutterIconRenderer()
        {
            @Override
            public boolean equals(Object o)
            {
                return false;
            }

            /*@Override
            @Nullable
            public String getTooltipText() {
                return "Some Code Explanation. \n New Line?";
            }*/

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
                                documentMarkupModel.removeHighlighter(rh);
                            }


                        };

                        AnAction discardAction  = new AnAction("Discard", "Discard Comment.", Icons.DELETE_ICON)
                        {
                            @Override
                            public void actionPerformed(AnActionEvent anActionEvent)
                            {
                                documentMarkupModel.removeHighlighter(rh);
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

    @Override
    public boolean equals(Object obj)
    {
        return true;
        //return this.psiElements.equals(((StronglyRelatedPsiElements)obj).psiElements);
    }
}
