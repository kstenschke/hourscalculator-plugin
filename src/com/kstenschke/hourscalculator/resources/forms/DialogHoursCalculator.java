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
import com.kstenschke.hourscalculator.utils.Preferences;
import com.kstenschke.hourscalculator.utils.UtilsString;

import javax.swing.*;
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

    /**
     * Constructor
     */
    public DialogHoursCalculator() {
        setContentPane(contentPane);
        setModal(true);

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
        String[] times  = Preferences.getStartEndTimes();

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
        String timesCsv  =       getTimeVal(textFieldStart1.getText());
        timesCsv        += "," + getTimeVal( textFieldEnd1.getText() );
        timesCsv        += "," + getTimeVal( textFieldStart2.getText() );
        timesCsv        += "," + getTimeVal( textFieldEnd2.getText() );
        timesCsv        += "," + getTimeVal( textFieldStart3.getText() );
        timesCsv        += "," + getTimeVal( textFieldEnd3.getText() );
        timesCsv        += "," + getTimeVal( textFieldStart4.getText() );
        timesCsv        += "," + getTimeVal( textFieldEnd4.getText() );
        timesCsv        += "," + getTimeVal( textFieldStart5.getText() );
        timesCsv        += "," + getTimeVal( textFieldEnd5.getText() );

        Preferences.saveStartEndTimes(timesCsv);
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
        FocusListener focusListener = new FocusListener() {
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
     * Calculate sum of hours and update all result fields
     */
    private void calculateHourSums() {
        Integer sumMinutes  = HoursCalculator.getDurationInMinutes(textFieldStart1.getText(), textFieldEnd1.getText());
                sumMinutes  += HoursCalculator.getDurationInMinutes(textFieldStart2.getText(), textFieldEnd2.getText());
                sumMinutes  += HoursCalculator.getDurationInMinutes(textFieldStart3.getText(), textFieldEnd3.getText());
                sumMinutes  += HoursCalculator.getDurationInMinutes(textFieldStart4.getText(), textFieldEnd4.getText());
                sumMinutes  += HoursCalculator.getDurationInMinutes(textFieldStart5.getText(), textFieldEnd5.getText());

        Double sumFraction  = sumMinutes > 0 ? ( Double.valueOf(sumMinutes) / 60.00 ) : 0;
        String[] fractionParts= sumFraction.toString().split("\\.");

        Double fractionMinutes= ( 60 * ( Double.valueOf("0." + fractionParts[1]) ) );
        String sumHours       = fractionParts[0] + ":" + String.valueOf(fractionMinutes.intValue());
        if( sumHours.startsWith(":") ) {
            sumHours    = "0" + sumHours;
        }

        textFieldSumMinutes.setText( String.valueOf(sumMinutes) );
        textFieldSumFraction.setText( String.valueOf(  String.format("%.2g%n", sumFraction) ) );
        textFieldSumHours.setText( String.valueOf(sumHours) );
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