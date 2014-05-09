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

import com.kstenschke.hourscalculator.utils.UtilsString;

public class HoursCalculator {

    /**
     * @param   timeFrom    Time string formatted like "13:50" or "13"
     * @param   timeUntil
     * @return  Integer
     */
    public static Integer getDurationInMinutes(String timeFrom, String timeUntil) {
        Integer sumMinutes  = 0;

        if( !timeFrom.isEmpty() && !timeUntil.isEmpty() ) {

            Integer hourFrom    = extractHourFromTime(timeFrom);
            Integer minutesFrom = extractMinutesFromTime(timeFrom);

            Integer hourTo      = extractHourFromTime(timeUntil);
            Integer minutesTo   = extractMinutesFromTime(timeUntil);

            Integer from    = hourFrom * 60 + minutesFrom;
            Integer until   = hourTo   * 60 + minutesTo;

            return until - from;
        }

        return sumMinutes;
    }

    /**
     * @param   timeStr
     */
    private static Integer extractHourFromTime(String timeStr) {
        timeStr = UtilsString.sanitizeTimeString(timeStr);

        if( timeStr.contains(":")) {
            String hourStr  = timeStr.split(":")[0];

            return !hourStr.equals("") ? Integer.parseInt( hourStr ) : 0;
        }

        return Integer.parseInt( timeStr );
    }

    /**
     * @param   timeStr
     */
    private static Integer extractMinutesFromTime(String timeStr) {
        if( timeStr.contains(":")) {
            return Integer.parseInt( timeStr.split(":")[1] );
        }

        return 0;
    }

}