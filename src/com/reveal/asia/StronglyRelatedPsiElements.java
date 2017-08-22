package com.reveal.asia;

import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
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
    String uncommentedCode = "";
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
        // We convert PsiElements to text immediately and never repeat it. because later we might add "comment" to subtrees.
        uncommentedCode = convertPsiElementsToText();
    }

    public String getUncommentedCode()
    {
        // It's not guranteed unless in the constructors the _elements didn't include any comments in subtrees.
        // TODO: To guarantee it, first we should iterate all elements (like "RemoveCodeComment" action)
        return uncommentedCode;
    }

    public String getRetrievedCodeDescriptionFromServer()
    {
        return retrievedCodeDescriptionFromServer;
    }

    public String getCodeDescriptionExistsInEditor()
    {
        /*String codeDescriptionExistsInEditor = "";
        for(int i=0; i<newlyAddedComments.size(); i++)
        {
            codeDescriptionExistsInEditor += newlyAddedComments.get(i).getText();
        }
        return codeDescriptionExistsInEditor;*/


        //Solution when comments are supposed to change Below Code:
        // TODO: newlyAddedComments elements are not valid if comments are changed in editor. By changing in editor,
        // The PsiElements (in Editor Tree) are replaced with new PsiElements and we don't have reference to them.
        // Here we do a trick and access changed comment, using valid (because of unchanged) first elements.
        // WARNING: For if added comments is one line
        String codeDescriptionExistsInEditor = "";
        if(newlyAddedComments.size()==0)
            return "";
        codeDescriptionExistsInEditor += psiElements.get(0).getPrevSibling().getPrevSibling().getText();
        return codeDescriptionExistsInEditor;
    }

    private String convertPsiElementsToText()
    {
        //Note: The code might include already=-commented subtree code blocks! (if inner parts are annotated with comment in editor)
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
        codeWithComment += getUncommentedCode();
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

    private String sendCodeToServerAndRetrieveDescriptionsLocally()
    {
        String code = getUncommentedCode();
        code = code.replace("\n"," ");
        String response = ASIAWrapper.getInstance().findDescriptions(code);
        return response;
    }

    private String sendCodeToServerAndRetrieveDescriptions()
    {
        try
        {
            String code = getUncommentedCode();
            code = code.replace("\n"," ");
            code = URLEncoder.encode(code,"UTF-8");
            String urlParameters  = "selectedCode="+code;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            ////////////////
            String request        = "http://localhost:8080/ASIAWebApp/MainServlet/getDescription";
            URL url = new URL( request );
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
            OddsAndEnds.showInfoBalloon("ADANA Plugin", "Connecting to ADANA server failed"); //TODO: No, we may be in auto-all-document mode and UI sucks.
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
        //String serverRes = sendCodeToServerAndRetrieveDescriptionsLocally();
        String serverRes = sendCodeToServerAndRetrieveDescriptions();

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
            retrievedCodeDescriptionFromServer = obj.getString("retrievedCloneDescription");
        }

        if(retrievedCodeDescriptionFromServer.isEmpty())
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
                        AnAction keepAction_1star  = new AnAction("Keep ☆", "Keep comment", Icons.CHECK_ICON)
                        {
                            @Override
                            public void actionPerformed(AnActionEvent anActionEvent)
                            {
                                documentMarkupModel.removeHighlighter(rh);
                                updateADANADatasetWithConfirmedDescription(1);
                            }
                        };

                        AnAction keepAction_2star  = new AnAction("Keep ☆☆", "Keep comment", Icons.CHECK_ICON)
                        {
                            @Override
                            public void actionPerformed(AnActionEvent anActionEvent)
                            {
                                documentMarkupModel.removeHighlighter(rh);
                                updateADANADatasetWithConfirmedDescription(2);
                            }
                        };

                        AnAction keepAction_3star  = new AnAction("Keep ☆☆☆", "Keep comment", Icons.CHECK_ICON)
                        {
                            @Override
                            public void actionPerformed(AnActionEvent anActionEvent)
                            {
                                documentMarkupModel.removeHighlighter(rh);
                                updateADANADatasetWithConfirmedDescription(3);
                            }
                        };

                        AnAction discardAction  = new AnAction("Delete", "Delete comment", Icons.DELETE_ICON)
                        {
                            @Override
                            public void actionPerformed(AnActionEvent anActionEvent)
                            {
                                documentMarkupModel.removeHighlighter(rh);
                                removeAddedCommentPsiElement();
                            }


                        };

                        AnAction[] actions = new AnAction[]{keepAction_1star, keepAction_2star, keepAction_3star, discardAction};
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

    private String removePeriodFromEndOfString(String str)
    {
        char lastChar = str.charAt(str.length()-1);
        if(lastChar=='.')
            return str.substring(0, str.length()-1);
        else
            return str;
    }

    private void updateADANADatasetWithConfirmedDescription(int userScore)
    {
        String uncommentedCode = getUncommentedCode();
        String updatedComment = getCodeDescriptionExistsInEditor();
        updatedComment = updatedComment.substring("//".length());
        updatedComment = updatedComment.trim();
        updatedComment = removePeriodFromEndOfString(updatedComment);
        String resultJson = sendCodeAndCommentToServerToUpdateDataset(uncommentedCode, updatedComment, userScore);
        if(true)
            System.out.print("Updateed Suc");
        else
            System.out.print("Failed");
    }

    private String sendCodeAndCommentToServerToUpdateDataset(String _code, String _description, int _score)
    {

        try
        {
            String urlParameters = "";
            ////////
            _code = _code.replace("\n"," ");
            _code = URLEncoder.encode(_code,"UTF-8");
            urlParameters += "code="+_code;
            ////////
            _description = _description.replace("\n"," ");
            _description = URLEncoder.encode(_description,"UTF-8");
            urlParameters += "&description="+_description;
            ////////
            urlParameters += "&score="+Integer.toString(_score);
            ////////
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            ////////////////////
            String request        = "http://localhost:8080/ASIAWebApp/MainServlet/updateDataset";
            URL url = new URL( request );
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
            OddsAndEnds.showInfoBalloon("ADANA Plugin", "Connecting to ADANA server failed"); //TODO: No, we may be in auto-all-document mode and UI sucks.
            e.printStackTrace();
            return String.format("{\"result\": -1, \"errorDescription\":\"%s\" }",e.toString());
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        return true;
        //return this.psiElements.equals(((StronglyRelatedPsiElements)obj).psiElements);
    }
}
