package com.reveal.asia;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;




public class GranularitySliderPanel extends JPanel {

    ArrayList<Integer> thresholds;
    private JSlider breakDownThresholdSlider;
    JButton sliderConfirmBtn;
    ASIAAction asiaAction;


    public GranularitySliderPanel(ASIAAction _asiaAction, ArrayList<Integer> _thresholds) {
        super();
        thresholds = _thresholds;
        asiaAction = _asiaAction; //BAD Design

        this.setLayout(new BoxLayout(this , BoxLayout.X_AXIS));

        createSlider();
        this.add(breakDownThresholdSlider);

        createOKBtn();
        this.add(sliderConfirmBtn);

        asiaAction.tryToBreakDownSelectedCode(asiaAction.WHOLE_BLOCK_THRESHOLD_MAGIC_NUMBER);
    }

    private void createSlider()
    {
        breakDownThresholdSlider = new TransparentSlider(JSlider.HORIZONTAL, 0, thresholds.size()-1, 0);
        breakDownThresholdSlider.setSnapToTicks(true);
        breakDownThresholdSlider.setMajorTickSpacing(1);
        breakDownThresholdSlider.setPaintTicks(true);
        breakDownThresholdSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = (int)source.getValue();
                    value = thresholds.get(thresholds.size()-1-value); //Mapping
                    System.out.print(value);
                    ////////////
                    asiaAction.tryToBreakDownSelectedCode(value);
                }
            }
        });
    }

    private void createOKBtn()
    {
        sliderConfirmBtn = new JButton("OK");
        sliderConfirmBtn.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                asiaAction.fetchDescriptions();
                asiaAction.sliderPopup.cancel();
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