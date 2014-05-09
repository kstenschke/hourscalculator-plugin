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
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NonNls;

public class UtilsPreferences {

    @NonNls
    public static final String ID_DIALOG_HOURSCALCULATOR = "PluginHoursCalculator.DialogHoursCalculator";

    @NonNls
    public static final String PROP_START_END_TIMES = "PluginHoursCalculator.StartEndTimes";

    @NonNls
    public static final String PROP_SHOW_SUMS = "PluginHoursCalculator.ShowSums";

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

    /**
     * @param   showSums   Show sums (min, fraction, duration) as comma-separated string of 0 or 1s
     */
    public static void saveShownSums(String showSums) {
        PropertiesComponent.getInstance().setValue(PROP_SHOW_SUMS, showSums);
    }

    /**
     * @return  String[]
     */
    public static String[] getShownSums() {
        return getProperty(PROP_SHOW_SUMS, "1,1,1", true).split(",");
    }

    /**
     * @param   index       0 = minutes, 1 = fraction, 2 = duration
     * @return  Boolean     is set now?
     */
    private static Boolean toggleShowSum(Integer index) {
        String[] shown   = getShownSums();
        shown[index] = shown[index].equals("1") ? "0" : "1";
        saveShownSums(StringUtils.join(shown, ","));

        return shown[index].equals("1");
    }

    public static Boolean toggleShowSumMinutes() {
        return toggleShowSum(0);
    }

    public static Boolean toggleShowSumFraction() {
        return toggleShowSum(1);
    }

    public static Boolean toggleShowSumDuration() {
        return toggleShowSum(2);
    }

}