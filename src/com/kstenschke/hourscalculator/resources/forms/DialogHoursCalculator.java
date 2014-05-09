/*
 * Copyright 2014 Kay Stenschke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kstenschke.hourscalculator.resources.forms;

import com.kstenschke.hourscalculator.HoursCalculator;
import com.kstenschke.hourscalculator.HoursCalculatorPopup;
import com.kstenschke.hourscalculator.utils.UtilsPreferences;
import com.kstenschke.hourscalculator.utils.UtilsString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogHoursCalculator extends JDialog {

    private JPanel contentPane;

    private JTextField textFieldSumFraction;
    private JTextField textFieldSumHours;
    private JTextField textFieldSumMinutes;

    private JTextField textFieldStart1;
    private JTextField textFieldStart2;
    private JTextField textFieldStart3;
    private JTextField textFieldStart4;
    private JTextField textFieldStart5;

    private JTextField textFieldEnd1;
    private JTextField textFieldEnd2;
    private JTextField textFieldEnd3;
    private JTextField textFieldEnd4;
    private JTextField textFieldEnd5;

    public JPanel panelSumMinutes;
    public JPanel panelSumFraction;
    public JPanel panelSumDuration;

    public JTextField textFieldSum1;
    public JTextField textFieldSum2;
    public JTextField textFieldSum3;
    public JTextField textFieldSum4;
    public JTextField textFieldSum5;

    public JLabel eq1;
    public JLabel eq2;
    public JLabel eq3;
    public JLabel eq4;
    public JLabel eq5;

    /**
     * Constructor
     */
    public DialogHoursCalculator() {
        setContentPane(contentPane);

        setResizable(false);
        setAlwaysOnTop(true);
        addPopupMenus();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void addPopupMenus() {
        textFieldStart1.addMouseListener( new HoursCalculatorPopup(this, textFieldStart1).getPopupListener(this) );
        textFieldEnd1.addMouseListener(   new HoursCalculatorPopup(this, textFieldEnd1).getPopupListener(this) );
        textFieldStart2.addMouseListener( new HoursCalculatorPopup(this, textFieldStart2).getPopupListener(this) );
        textFieldEnd2.addMouseListener(   new HoursCalculatorPopup(this, textFieldEnd2).getPopupListener(this) );
        textFieldStart3.addMouseListener( new HoursCalculatorPopup(this, textFieldStart3).getPopupListener(this) );
        textFieldEnd3.addMouseListener(   new HoursCalculatorPopup(this, textFieldEnd3).getPopupListener(this) );
        textFieldStart4.addMouseListener( new HoursCalculatorPopup(this, textFieldStart4).getPopupListener(this) );
        textFieldEnd4.addMouseListener(   new HoursCalculatorPopup(this, textFieldEnd4).getPopupListener(this) );
        textFieldStart5.addMouseListener( new HoursCalculatorPopup(this, textFieldStart5).getPopupListener(this) );
        textFieldEnd5.addMouseListener(   new HoursCalculatorPopup(this, textFieldEnd5).getPopupListener(this) );
    }

    private void onClose() {
        storeTimesPref();
        dispose();
    }

    public void initTimesFromPrefs() {
        String[] times  = UtilsPreferences.getStartEndTimes();

        textFieldStart1.setText(times[0]);
        textFieldEnd1.setText(times[1]);

        textFieldStart2.setText(times[2]);
        textFieldEnd2.setText(times[3]);

        textFieldStart3.setText(times[4]);
        textFieldEnd3.setText(times[5]);

        textFieldStart4.setText(times[6]);
        textFieldEnd4.setText(times[7]);

        textFieldStart5.setText(times[8]);
        textFieldEnd5.setText(times[9]);

        calculateHourSums();
    }

    public void resetAllHours() {
        textFieldStart1.setText("0:00");
        textFieldEnd1.setText("0:00");

        textFieldStart2.setText("0:00");
        textFieldEnd2.setText("0:00");

        textFieldStart3.setText("0:00");
        textFieldEnd3.setText("0:00");

        textFieldStart4.setText("0:00");
        textFieldEnd4.setText("0:00");

        textFieldStart5.setText("0:00");
        textFieldEnd5.setText("0:00");

        calculateHourSums();
    }

    private void storeTimesPref() {
        String timesCsv  =       getTimeVal( textFieldStart1.getText());
        timesCsv        += "," + getTimeVal(   textFieldEnd1.getText() );
        timesCsv        += "," + getTimeVal( textFieldStart2.getText() );
        timesCsv        += "," + getTimeVal(   textFieldEnd2.getText() );
        timesCsv        += "," + getTimeVal( textFieldStart3.getText() );
        timesCsv        += "," + getTimeVal(   textFieldEnd3.getText() );
        timesCsv        += "," + getTimeVal( textFieldStart4.getText() );
        timesCsv        += "," + getTimeVal(   textFieldEnd4.getText() );
        timesCsv        += "," + getTimeVal( textFieldStart5.getText() );
        timesCsv        += "," + getTimeVal(   textFieldEnd5.getText() );

        UtilsPreferences.saveStartEndTimes(timesCsv);
    }

    /**
     * @param   value
     * @return  Value
     */
    private String getTimeVal(String value) {
        if( value.isEmpty() ) {
            value = "0:00";
        }

        return value;
    }

    public void addListeners() {
            // Add component listener to dialog - store position when changed
        this.addComponentListener( getComponentListener() );

            // Add focus listener to all fields - sanitize value, recalculate sum
        FocusListener focusListener = getFocusListener();

        textFieldStart1.addFocusListener(focusListener);
        textFieldStart2.addFocusListener(focusListener);
        textFieldStart3.addFocusListener(focusListener);
        textFieldStart4.addFocusListener(focusListener);
        textFieldStart5.addFocusListener(focusListener);

        textFieldEnd1.addFocusListener(focusListener);
        textFieldEnd2.addFocusListener(focusListener);
        textFieldEnd3.addFocusListener(focusListener);
        textFieldEnd4.addFocusListener(focusListener);
        textFieldEnd5.addFocusListener(focusListener);
    }

    /**
     * @return  ComponentListener
     */
    private ComponentListener getComponentListener() {
        return new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {
                Component component = e.getComponent();
                UtilsPreferences.saveDialogPosition(UtilsPreferences.ID_DIALOG_HOURSCALCULATOR, component.getX(), component.getY());
            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        };
    }

    /**
     * @return  FocusListener
     */
    private FocusListener getFocusListener() {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField textField    = (JTextField) e.getComponent();
                String text = textField.getText();

                if( !text.isEmpty() ) {
                    textField.setSelectionStart(0);
                    textField.setSelectionEnd(text.length());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField textField    = (JTextField) e.getComponent();
                String text = UtilsString.sanitizeTimeString(textField.getText());

                if( text.endsWith(":")) {
                    text += "00";
                } else if( !text.contains(":") ) {
                    text += ":00";
                }

                if( text.startsWith(":")) {
                    text = "0" + text;
                }

                textField.setText( text );

                calculateHourSums();
            }
        };
    }

    /**
     * Calculate sum of hours and update all result fields
     */
    public void calculateHourSums() {
        Integer sumRow1  = getDurationSumMinutesPositiveFromTimeTextFields(textFieldStart1, textFieldEnd1);
        Integer sumRow2  = getDurationSumMinutesPositiveFromTimeTextFields(textFieldStart2, textFieldEnd2);
        Integer sumRow3  = getDurationSumMinutesPositiveFromTimeTextFields(textFieldStart3, textFieldEnd3);
        Integer sumRow4  = getDurationSumMinutesPositiveFromTimeTextFields(textFieldStart4, textFieldEnd4);
        Integer sumRow5  = getDurationSumMinutesPositiveFromTimeTextFields(textFieldStart5, textFieldEnd5);

        textFieldSum1.setText( sumRow1.toString() );
        textFieldSum2.setText( sumRow2.toString() );
        textFieldSum3.setText( sumRow3.toString() );
        textFieldSum4.setText( sumRow4.toString() );
        textFieldSum5.setText( sumRow5.toString() );

        Integer sumMinutes  = sumRow1 + sumRow2 + sumRow3 + sumRow4 + sumRow5;

        Double sumFraction  = sumMinutes > 0 ? ( Double.valueOf(sumMinutes) / 60.00 ) : 0;
        String[] fractionParts= sumFraction.toString().split("\\.");

        Double fractionMinutes= ( 60 * ( Double.valueOf("0." + fractionParts[1]) ) );
        String sumHours       = fractionParts[0] + ":" + String.valueOf(fractionMinutes.intValue());
        if( sumHours.startsWith(":") ) {
            sumHours    = "0" + sumHours;
        }

        textFieldSumMinutes.setText( String.valueOf(sumMinutes) );
        textFieldSumFraction.setText( String.valueOf(  String.format("%.2f", sumFraction) ) );
        textFieldSumHours.setText( String.valueOf(sumHours) );
    }

    /**
     * @param   field1
     * @param   field2
     * @return  Integer
     */
    private Integer getDurationSumMinutesPositiveFromTimeTextFields(JTextField field1, JTextField field2) {
        Integer sum     = HoursCalculator.getDurationInMinutes(field1.getText(), field2.getText());

        if( sum < 0 ) {
            field2.setText( field1.getText() );
            sum     = HoursCalculator.getDurationInMinutes(field1.getText(), field2.getText());
        }

        return sum;
    }

    /**
     * @param   args
     */
    public static void main(String[] args) {
        DialogHoursCalculator dialog = new DialogHoursCalculator();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}