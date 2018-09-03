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

package org.testeditor.fixture.commons.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testeditor.fixture.core.FixtureException;
import org.testeditor.fixture.core.interaction.FixtureMethod;

/**
 * This Class is for convenience purposes to use in tests. To create e.g. birthday dates in the past with a negative 
 * number as offset or a positive number for birthday dates in the future. For increasing dates as year you have to 
 * use the OffsetTyp.YEAR, the usage is equivalent for all other OffsetTypes [YEAR, MONTH, WEEK, DAY, HOUR, MINUTE, 
 * SECOND, MILLISECOND] 
 * 
 */
public class DateGenerator {
    
    enum OffsetType {
        YEAR, 
        MONTH,
        WEEK,
        DAY, 
        HOUR,
        MINUTE, 
        SECOND, 
        MILLISECOND 
    }

    /**
     * This method returns a date as String in a given format like e.g."dd.MM.yyyy" with a given offset
     * in e.g years, months, weeks, days, hours, minutes, seconds, milliseconds henceforth. <br>
     * E.g. current date = 02.08.2014; OffsetType.DAY ; offset = 14; <br>
     * You will get a date as String like "16.08.2014". <br>
     * For a negative offset = -14 and current date = 02.08.2014 <br>
     * You will get a date as String like "19.07.2014".
     *
     * @param offset
     *            This offset represents a number as a long.  A negative number represents e.g. 
     *            a day for the OffsetType.DAY in the past (-30 means 30 days in the past). 
     *            Accordingly a positive number represents a day in the future.
     *            This is equivalent for all the other OffsetTypes like YEAR, MONTH, WEEK, HOUR, MINUTE, 
     *            SECOND, MILLISECOND       
     * @param format
     *            String in the form "dd.MM.yyyy". Regarding the letters representing the pattern, 
     *            refer to {@link SimpleDateFormat}.
     *            
     * @param type This {@link Enum} represents which OffsetType the user wants to manipulate. 
     * Possible options are:
     * 
     * <ul>
     *   <li>YEAR</li>
     *   <li>MONTH</li>
     *   <li>WEEK</li>
     *   <li>DAY</li>
     *   <li>HOUR</li>
     *   <li>MINUTE</li>
     *   <li>SECOND</li>
     *   <li>MILLISECOND</li>
     * </ul>  
     *    
     *
     * @return Date as String in the preferred format, included given offset.
     * @throws FixtureException Will be thrown when an exception occurs during generation of dates 
     */
    @FixtureMethod
    public String generateDateFromNowWithOffset(long offset, String format, OffsetType type) throws FixtureException {
        String formattedDateWithOffset = null;
        
        try {
            switch (type) {
                case YEAR:
                    formattedDateWithOffset = generateYearsFromNowWithOffset(offset, format);
                    break;
                case MONTH:
                    formattedDateWithOffset = generateMonthsFromNowWithOffset(offset, format);
                    break; 
                case WEEK:
                    formattedDateWithOffset = generateWeeksFromNowWithOffset(offset, format);
                    break;
                case DAY:
                    formattedDateWithOffset = generateDaysFromNowWithOffset(offset, format);
                    break;
                case HOUR:
                    formattedDateWithOffset = generateHoursFromNowWithOffset(offset, format);    
                    break; 
                case MINUTE:
                    formattedDateWithOffset = generateMinutesFromNowWithOffset(offset, format);        
                    break;    
                case SECOND:
                    formattedDateWithOffset = generateSecondsFromNowWithOffset(offset, format);
                    break;
                case MILLISECOND:
                    formattedDateWithOffset = generateMillisecondsFromNowWithOffset(offset, format);
                    break;                
                default:
                    break;
            }
            
        } catch (Exception e) {
            throw new FixtureException("An exception occured when generating a date with offset.", //
                    FixtureException.keyValues("offsetType", type, "offset", offset, "format" , format), e);
        }
        return formattedDateWithOffset;
    }
    
    /**
     * This method returns a date as String in a given format like e.g."dd.MM.yyyy" with a given offset
     * in e.g years, months, weeks, days, hours, minutes, seconds, milliseconds henceforth. <br>
     * E.g. current date = 02.08.2014; OffsetType.DAY ; offset = 14; <br>
     * You will get a date as String like "16.08.2014". <br>
     * For a negative offset = -14 and current date = 02.08.2014 <br>
     * You will get a date as String like "19.07.2014".
     *
     * @param offset
     *            This offset represents a number as a long.  A negative number represents e.g. 
     *            a day for the OffsetType.DAY in the past (-30 means 30 days in the past). 
     *            Accordingly a positive number represents a day in the future.
     *            This is equivalent for all the other OffsetTypes like YEAR, MONTH, WEEK, HOUR, MINUTE, 
     *            SECOND, MILLISECOND       
     * @param format
     *            String in the form "dd.MM.yyyy". Regarding the letters representing the pattern, 
     *            refer to {@link SimpleDateFormat}.
     *            
     * @param type This String represents which type of offset the user wants to manipulate. 
     * Possible options are:
     * 
     * <ul>
     *   <li>YEAR</li>
     *   <li>MONTH</li>
     *   <li>WEEK</li>
     *   <li>DAY</li>
     *   <li>HOUR</li>
     *   <li>MINUTE</li>
     *   <li>SECOND</li>
     *   <li>MILLISECOND</li>
     * </ul>  
     * This method is as a workaround and for compatibility reason because an AML file can not handle 
     * {@link Enum}s yet.    
     *
     * @return Date as String in the preferred format, included given offset.
     * @throws FixtureException Will be thrown when an exception occurs during generation of dates 
     */
    @FixtureMethod
    public String generateDateFromNowWithOffset(long offset, String format, String type) throws FixtureException {
        String formattedDateWithOffset = null;
        
        try {
            formattedDateWithOffset = generateDateFromNowWithOffset(offset, format, OffsetType.valueOf(type));       
        } catch (Exception e) {
            throw new FixtureException("An exception occured when generating a date with offset.", 
                  FixtureException.keyValues("offsetType", type, "offset", offset, "format" , format), e);
       
        }
        return formattedDateWithOffset;
    }
    
    

    protected String generateYearsFromNowWithOffset(long offset, String format) {
        LocalDateTime localDate = createLocalDateTime();
        LocalDate dateWithOffset = localDate.plusYears(offset).toLocalDate();
        return formattedDate(format, dateWithOffset);
    }

    protected String generateMonthsFromNowWithOffset(long offset, String format) {
        LocalDateTime localDate = createLocalDateTime();
        LocalDate dateWithOffset = localDate.plusMonths(offset).toLocalDate();
        return formattedDate(format, dateWithOffset);
    }
    
    protected String generateWeeksFromNowWithOffset(long offset, String format) {
        LocalDateTime localDate = createLocalDateTime();
        LocalDate dateWithOffset = localDate.plusWeeks(offset).toLocalDate();
        return formattedDate(format, dateWithOffset);
    }
    
    protected String generateDaysFromNowWithOffset(long offset, String format) {
        LocalDateTime localDate = createLocalDateTime();
        LocalDate dateWithOffset = localDate.plusDays(offset).toLocalDate();
        return formattedDate(format, dateWithOffset);
    }

    protected String generateHoursFromNowWithOffset(long offset, String format) {
        LocalDateTime localDate = createLocalDateTime();
        LocalDateTime dateWithOffset = localDate.plusHours(offset);
        return formattedDate(format, dateWithOffset);
    }

    protected String generateMinutesFromNowWithOffset(long offset, String format) {
        LocalDateTime localDate = createLocalDateTime();
        LocalDateTime dateWithOffset = localDate.plusMinutes(offset);
        return formattedDate(format, dateWithOffset);
    }

    protected String generateSecondsFromNowWithOffset(long offset, String format) {
        LocalDateTime localDate = createLocalDateTime();
        LocalDateTime dateWithOffset = localDate.plusSeconds(offset);
        return formattedDate(format, dateWithOffset);
    }
    
    protected String generateMillisecondsFromNowWithOffset(long offset, String format) {
        LocalDateTime localDate = createLocalDateTime();
        LocalDateTime dateWithOffset = localDate.plusNanos(offset * 1000000);
        return formattedDate(format, dateWithOffset);
    }
 
    protected LocalDateTime createLocalDateTime() {
        return LocalDateTime.now();
    }    
    
    protected String formattedDate(String format, LocalDate dateWithOffset) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateWithOffset.format(formatter);
    }
    
    protected String formattedDate(String format, LocalDateTime dateWithOffset) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateWithOffset.format(formatter);
    }

}


