package com.reveal.asia;

import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.psi.PsiElement;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;


public class GranularitySliderPanel extends JPanel {

    int maxNestedLevelInSelectedCode;
    private JSlider breakDownThresholdSlider;
    JButton sliderConfirmBtn;
    MarkupModel documentMarkupModel;
    DocumentSelectedCodeAction documentSelectedCodeAction;
    PsiElement firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement;
    ArrayList<StronglyRelatedPsiElements> listOfStronglyRelatedPsiElements;

    public GranularitySliderPanel(PsiElement _firstLowestSameLevelPsiElement, PsiElement _secondLowestSameLevelPsiElement, DocumentSelectedCodeAction _documentSelectedCodeAction, int _maxNestedLevelInSelectedCode, MarkupModel _documentMarkupModel) {
        super();
        firstLowestSameLevelPsiElement = _firstLowestSameLevelPsiElement;
        secondLowestSameLevelPsiElement = _secondLowestSameLevelPsiElement;
        maxNestedLevelInSelectedCode = _maxNestedLevelInSelectedCode;
        documentMarkupModel = _documentMarkupModel;
        documentSelectedCodeAction = _documentSelectedCodeAction; //BAD Design

        this.setLayout(new BoxLayout(this , BoxLayout.X_AXIS));

        if(maxNestedLevelInSelectedCode!=MyDocumenter.FIRST_NESTED_LEVEL_INDEX)
        {
            createSlider();
            this.add(breakDownThresholdSlider);
        }
        else
            breakDownThresholdSlider = null;

        createOKBtn();
        this.add(sliderConfirmBtn);

        listOfStronglyRelatedPsiElements  = MyDocumenter.getInstance().breakdownAndHighlight(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement, MyDocumenter.FIRST_NESTED_LEVEL_INDEX, documentMarkupModel);
    }

    private void createSlider()
    {
        breakDownThresholdSlider = new TransparentSlider(JSlider.HORIZONTAL, MyDocumenter.FIRST_NESTED_LEVEL_INDEX, maxNestedLevelInSelectedCode, MyDocumenter.FIRST_NESTED_LEVEL_INDEX);
        breakDownThresholdSlider.setSnapToTicks(true);
        breakDownThresholdSlider.setMajorTickSpacing(1);
        breakDownThresholdSlider.setPreferredSize(new Dimension(200,40));
        breakDownThresholdSlider.setPaintTicks(true);
        Hashtable<Integer, JLabel> labels =  new Hashtable<Integer, JLabel>();
        labels.put(MyDocumenter.FIRST_NESTED_LEVEL_INDEX, new JLabel("High"));
        labels.put(maxNestedLevelInSelectedCode, new JLabel("Low"));
        breakDownThresholdSlider.setLabelTable(labels);
        breakDownThresholdSlider.setPaintLabels(true);
        breakDownThresholdSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = (int)source.getValue();
                    //System.out.print(value);
                    ////////////
                    listOfStronglyRelatedPsiElements = MyDocumenter.getInstance().breakdownAndHighlight(firstLowestSameLevelPsiElement, secondLowestSameLevelPsiElement, value, documentMarkupModel);
                }
            }
        });
    }

    private void createOKBtn()
    {
        sliderConfirmBtn = new JButton("Retrieve Code Description");
        sliderConfirmBtn.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MyDocumenter.getInstance().getDescriptionsAndHighlightAny(listOfStronglyRelatedPsiElements, documentMarkupModel);
                documentSelectedCodeAction.sliderPopup.cancel();
            }

        });
    }


    /*@Override
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        super.paintComponent(g);
    }*/


    public class TransparentSlider extends JSlider
    {

        public TransparentSlider(int orientation, int min, int max, int value)
        {
            super(orientation, min, max, value);
            setOpaque(false);
        }

        /*@Override
        public void paint(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
            super.paint(g2d);
            g2d.dispose();
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            // We need this because we've taken over the painting of the component
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();

            super.paintComponent(g);
        }*/
    }

}