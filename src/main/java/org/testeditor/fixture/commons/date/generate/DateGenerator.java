/*******************************************************************************
 * Copyright (c) 2012 - 2018 Signal Iduna Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Signal Iduna Corporation - initial API and implementation
 * akquinet AG
 * itemis AG
 *******************************************************************************/

package org.testeditor.fixture.commons.date.generate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This Class is for convenience purposes to use in tests. To create Birthday dates in the past with a negative number
 * as offset or a positive number for birthday dates in the future. <p>
 * 
 * The method generateDateFromNowWithOffset() returns a date as String in a given format like e.g.
 * "dd.MM.yyyy" with a given offset in days henceforth. <br>
 * E.g. current date = 02.08.2014; offset = 14; <br>
 * You will get a date as String like "16.08.2014". <br>
 * For a negative offset = -14 and current date = 02.08.2014 <br>
 * You will get a date as String like "19.07.2014".
 */
class DateGenerator {
    
    /**
     * This method returns a date as String in a given format like e.g.
     * "dd.MM.yyyy" with a given offset in days henceforth. <br>
     * E.g. current date = 02.08.2014; offset = 14; <br>
     * You will get a date as String like "16.08.2014". <br>
     * For a negative offset = -14 and current date = 02.08.2014 <br>
     * You will get a date as String like "19.07.2014".
     *
     * @param offsetInDays
     *            Offset in days, as a long.  A negative number represents a day in the past 
     *            (-30 means 30 days in the past). Accordingly a positive number represents a day in the future.
     * @param format
     *            String in the form "dd.MM.yyyy". Regarding the letters representing the pattern, 
     *            refer to {@link SimpleDateFormat}.
     *
     * @return Date as String in the preferred format
     */
    public String generateDateFromNowWithOffset(long offsetInDays, String format) {
        LocalDateTime timePoint = LocalDateTime.now();
        LocalDate localDate = timePoint.toLocalDate();
        LocalDate daysWithOffset = localDate.plusDays(offsetInDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return daysWithOffset.format(formatter);
    }
        
}


