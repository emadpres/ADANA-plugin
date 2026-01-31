package com.reveal.asia;

import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by emadpres on 8/3/17.
 */
public class CodeDescriptionPopup
{
    StronglyRelatedPsiElements associatedSRPE;
    DocumentSelectedCodeAction documentSelectedCodeAction;
    JBPopup component;
    //////// UI
    JTextArea editableCodeDescription;
    JButton confirmDocumentation;

    public CodeDescriptionPopup(StronglyRelatedPsiElements _associatedSRPE, DocumentSelectedCodeAction _documentSelectedCodeAction)
    {
        associatedSRPE = _associatedSRPE;
        documentSelectedCodeAction = _documentSelectedCodeAction;
        createJBPopup();
    }

    public JBPopup getComponent()
    {
        return component;
    }

    private void createJBPopup()
    {
        JPanel rowPane = createJPanel();
        ComponentPopupBuilder componentPopupBuilder_textField = JBPopupFactory.getInstance().createComponentPopupBuilder(rowPane, null);
        component = componentPopupBuilder_textField.createPopup();
    }

    private JPanel createJPanel()
    {
        createTextArea();
        createConfirmButton();
        JButton discardDocumentation = createDiscardButton();
        //////
        JPanel rowPane = new JPanel();
        rowPane.add(editableCodeDescription);
        rowPane.add(confirmDocumentation);
        rowPane.add(discardDocumentation);
        rowPane.setLayout(new BoxLayout(rowPane , BoxLayout.X_AXIS));
        return rowPane;
    }

    private JTextArea createTextArea()
    {
        editableCodeDescription = new JTextArea("Test Some thing.");
        editableCodeDescription.setPreferredSize(new Dimension(200,30));
        return editableCodeDescription;
    }

    private JButton createConfirmButton()
    {
        confirmDocumentation = new JButton("Add");
        confirmDocumentation.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String txt = editableCodeDescription.getText();
                documentSelectedCodeAction.generareComment(CodeDescriptionPopup.this.associatedSRPE,"// "+txt);
                CodeDescriptionPopup.this.component.cancel();
            }
        });
        return confirmDocumentation;
    }

    private JButton createDiscardButton()
    {
        JButton discardDocumentation = new JButton("Discard");
        discardDocumentation.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CodeDescriptionPopup.this.component.cancel();
            }
        });
        return discardDocumentation;
    }

    public void addConfirmAction(ActionListener action)
    {

    }
}
