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
package com.kstenschke.hourscalculator;

import com.kstenschke.hourscalculator.resources.forms.DialogHoursCalculator;
import com.kstenschke.hourscalculator.utils.Environment;
import com.kstenschke.hourscalculator.utils.Preferences;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoursCalculatorPopup {

    private DialogHoursCalculator dialog;
    public JPopupMenu popup;

    public JMenuItem menuItemHourToCurrentTime;
    public JMenuItem menuItemResetHour;
    public JMenuItem menuItemResetAllHours;
    public JMenuItem menuItemShowSumMinutes;
    public JMenuItem menuItemShowSumFraction;
    public JMenuItem menuItemShowSumDuration;

    /**
     * Constructor
     */
    public HoursCalculatorPopup(final DialogHoursCalculator dialog, final JTextField textField) {
        this.dialog = dialog;
        this.popup  = new JPopupMenu();

            // Set field to current to
        this.menuItemHourToCurrentTime = new JMenuItem(StaticTexts.POPUP_SET_CURRENT_HOUR);
        this.menuItemHourToCurrentTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldText(textField, Environment.getCurrentTime());
            }
        });
        this.popup.add(this.menuItemHourToCurrentTime);

            // Set field to 0:00
        this.menuItemResetHour = new JMenuItem(StaticTexts.POPUP_RESET_HOUR);
        this.menuItemResetHour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldText(textField, "0:00");
            }
        });
        this.popup.add(this.menuItemResetHour);

        this.popup.addSeparator();

            // Set all fields to 0:00
        this.menuItemResetAllHours = new JMenuItem(StaticTexts.POPUP_RESET_ALL_HOURS);
        this.menuItemResetAllHours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.resetAllHours();
            }
        });
        this.popup.add(this.menuItemResetAllHours);

        this.popup.addSeparator();

        String[] showSums   = Preferences.getShownSums();
        ImageIcon iconCheck = null;
        try {
            Image image = ImageIO.read( getClass().getResource("resources/images/check.png") );
            iconCheck   = new ImageIcon(image);
        } catch(Exception exception) {
            exception.printStackTrace();
        }

            // Show sum in minutes
        this.menuItemShowSumMinutes = new JMenuItem(StaticTexts.POPUP_SHOW_SUM_MINUTES);
        this.menuItemShowSumMinutes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean isShown = Preferences.toggleShowSumMinutes();
                updateShownChecks();
            }
        });
        this.menuItemShowSumMinutes.setIcon( showSums[0].equals("1") ? iconCheck : null );
        this.popup.add(this.menuItemShowSumMinutes);

            // Show sum as hours fraction
        this.menuItemShowSumFraction = new JMenuItem(StaticTexts.POPUP_SHOW_SUM_FRACTION);
        this.menuItemShowSumFraction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean isShown = Preferences.toggleShowSumFraction();
                updateShownChecks();
            }
        });
        this.menuItemShowSumFraction.setIcon( showSums[1].equals("1") ? iconCheck : null );
        this.popup.add(this.menuItemShowSumFraction);

            // Show sum as hours duration
        this.menuItemShowSumDuration = new JMenuItem(StaticTexts.POPUP_SHOW_SUM_DURATION);
        this.menuItemShowSumDuration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Preferences.toggleShowSumDuration();
                updateShownChecks();
            }
        });
        this.menuItemShowSumDuration.setIcon( showSums[2].equals("1") ? iconCheck : null );
        this.popup.add(this.menuItemShowSumDuration);
    }

    public void updateShownChecks() {
        String[] shownSums   = Preferences.getShownSums();

        this.dialog.panelSumMinutes.setVisible( shownSums[0].equals("1") );
        this.dialog.panelSumFraction.setVisible( shownSums[1].equals("1") );
        this.dialog.panelSumDuration.setVisible( shownSums[2].equals("1") );
    }

    /**
     * @param   textField
     * @param   text
     */
    private void setFieldText(JTextField textField, String text) {
        textField.setText(text);
        textField.requestFocusInWindow();
    }

    /**
     * @return  PopupListener
     */
    public PopupListener getPopupListener(DialogHoursCalculator dialog) {
        return new PopupListener(dialog);
    }

    /**
     * PopupListener
     */
    class PopupListener extends MouseAdapter {

        DialogHoursCalculator dialog;

        public PopupListener(DialogHoursCalculator dialog) {
            this.dialog  = dialog;
        }

        /**
         * @param   e
         */
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        /**
         * @param   e
         */
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        /**
         * @param   e
         */
        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger() ) {
                updateShownChecks();
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

}