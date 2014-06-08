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
import com.kstenschke.hourscalculator.utils.UtilsEnvironment;
import com.kstenschke.hourscalculator.utils.UtilsPreferences;

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
    public JMenuItem menuItemShowSumMinutes;
    public JMenuItem menuItemShowSumFraction;
    public JMenuItem menuItemShowSumDuration;

    private ImageIcon iconCheck = null;

    /**
     * Constructor
     */
    public HoursCalculatorPopup(final DialogHoursCalculator dialog, final JTextField textField) {
        this.dialog = dialog;
        this.popup  = new JPopupMenu();

            // Set field to current time
        this.menuItemHourToCurrentTime = new JMenuItem(StaticTexts.POPUP_SET_CURRENT_HOUR);
        this.menuItemHourToCurrentTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldText(textField, UtilsEnvironment.getCurrentTime());
                dialog.calculateHourSums();
            }
        });
        try {
            Image imageClock = ImageIO.read( getClass().getResource("resources/images/clock.png") );
            ImageIcon iconClock   = new ImageIcon(imageClock);
            this.menuItemHourToCurrentTime.setIcon(iconClock);
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        this.popup.add(this.menuItemHourToCurrentTime);

            // Set field to 0:00
        this.menuItemResetHour = new JMenuItem(StaticTexts.POPUP_RESET_HOUR);
        this.menuItemResetHour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldText(textField, "0:00");
            }
        });
        try {
            Image imageReset = ImageIO.read( getClass().getResource("resources/images/set-zero.png") );
            ImageIcon iconReset   = new ImageIcon(imageReset);
            this.menuItemResetHour.setIcon(iconReset);
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        this.popup.add(this.menuItemResetHour);

        this.popup.addSeparator();

        String[] showSums   = UtilsPreferences.getShownSums();
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
                UtilsPreferences.toggleShowSumMinutes();
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
                UtilsPreferences.toggleShowSumFraction();
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
                UtilsPreferences.toggleShowSumDuration();
                updateShownChecks();
            }
        });
        this.menuItemShowSumDuration.setIcon( showSums[2].equals("1") ? iconCheck : null );
        this.popup.add(this.menuItemShowSumDuration);
    }

    public void updateShownChecks() {
        String[] shownSums   = UtilsPreferences.getShownSums();

        Boolean showMinutes = shownSums[0].equals("1");
        Boolean showFraction= shownSums[1].equals("1");
        Boolean showDuration= shownSums[2].equals("1");

        this.dialog.panelSumMinutes.setVisible(  showMinutes );
        this.dialog.eq1.setVisible( showMinutes );
        this.dialog.eq2.setVisible( showMinutes );
        this.dialog.eq3.setVisible( showMinutes );
        this.dialog.eq4.setVisible( showMinutes );
        this.dialog.eq5.setVisible( showMinutes );
        this.dialog.textFieldSum1.setVisible( showMinutes );
        this.dialog.textFieldSum2.setVisible( showMinutes );
        this.dialog.textFieldSum3.setVisible( showMinutes );
        this.dialog.textFieldSum4.setVisible( showMinutes );
        this.dialog.textFieldSum5.setVisible( showMinutes );

        this.dialog.panelSumFraction.setVisible( showFraction );
        this.dialog.panelSumDuration.setVisible( showDuration );

        this.menuItemShowSumMinutes.setIcon( showMinutes ? iconCheck : null );
        this.menuItemShowSumFraction.setIcon( showFraction ? iconCheck : null );
        this.menuItemShowSumDuration.setIcon( showDuration ? iconCheck : null );
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
            updateShownChecks();

            if (e.isPopupTrigger() ) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

}