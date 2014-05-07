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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoursCalculatorPopup {

    public JPopupMenu popup;

    public JMenuItem menuItemHourToCurrentTime;
    public JMenuItem menuItemResetHour;
    public JMenuItem menuItemResetAllHours;

    /**
     * Constructor
     */
    public HoursCalculatorPopup(final DialogHoursCalculator dialog, final JTextField textField) {
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
    }

    private void setFieldText(JTextField textField, String text) {
        textField.setText(text);
        textField.requestFocusInWindow();
    }

    /**
     * @return  PopupListener
     */
    public PopupListener getPopupListener() {
        return new PopupListener();
    }

    /**
     * PopupListener
     */
    class PopupListener extends MouseAdapter {
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
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

}