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
package com.kstenschke.hourscalculator.utils;

import com.intellij.ide.util.PropertiesComponent;
import org.jetbrains.annotations.NonNls;

public class Preferences {

    @NonNls
    public static final String ID_DIALOG_HOURSCALCULATOR = "PluginHoursCalculator.DialogHoursCalculator";

    @NonNls
    public static final String PROP_START_END_TIMES = "PluginHoursCalculator.StartEndTimes";

    /**
     * @param propertyName Name of the preference property
     * @param defaultValue Default value to be set if null
     * @param setDefaultIfEmpty Set default also if empty?
     * @return String
     */
    private static String getProperty(String propertyName, String defaultValue, Boolean setDefaultIfEmpty) {
        String value = PropertiesComponent.getInstance().getValue(propertyName);
        if (value == null) {
            value = defaultValue;
        }
        if (value.equals("") && setDefaultIfEmpty && !defaultValue.equals("")) {
            value = defaultValue;
        }

        return value;
    }

    /**
     * @param idDialog
     * @param x
     * @param y
     */
    public static void saveDialogPosition(String idDialog, Integer x, Integer y) {
        PropertiesComponent.getInstance().setValue(idDialog + ".Position", x.toString() + "x" + y.toString());
    }

    /**
     * @param idDialog
     */
    public static String getDialogPosition(String idDialog) {
        return getProperty( idDialog + ".Position", "0x0", false );
    }

    /**
     * @param   startEndTimes   Start- and End- Times as comma-separated string ex: Start1,End1,Start2,End2,...,End5
     */
    public static void saveStartEndTimes(String startEndTimes) {
        PropertiesComponent.getInstance().setValue(PROP_START_END_TIMES, startEndTimes);
    }

    /**
     * @return  String[]
     */
    public static String[] getStartEndTimes() {
        return getProperty(PROP_START_END_TIMES, "0:00,0:00,0:00,0:00,0:00,0:00,0:00,0:00,0:00,0:00", true).split(",");
    }

}