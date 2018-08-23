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

import java.time.LocalDateTime;
import java.time.Month;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testeditor.fixture.commons.date.generate.DateGenerator.OffsetType;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateGenerator.class)
public class DateGeneratorTest {
    
    @Test
    public void generateDateWithPositiveYearOffsetTest() {
        
        //given
        int toAdd = 6;
        String format = "dd.MM.yyyy";
        String formattedString = "25.02.2021";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.YEAR);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }
    
    @Test
    public void generateDateWithNegativeYearOffsetTest() {
        
        //given
        int toAdd = -6;
        String format = "dd.MM.yyyy";
        String formattedString = "25.02.2009";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.YEAR);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }
    
    @Test
    public void generateDateWithPositiveMonthOffsetTest() {
        
        //given
        int toAdd = 12;
        String format = "dd.MM.yyyy";
        String formattedString = "25.02.2016";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.MONTH);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }   
    
    @Test
    public void generateDateWithNegativeMonthOffsetTest() {
        
        //given
        int toAdd = -3;
        String format = "dd.MM.yyyy";
        String formattedString = "25.11.2014";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.MONTH);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    } 
    
    @Test
    public void generateDateWithPositiveWeekOffsetTest() {
        
        //given
        int toAdd = 2;
        String format = "dd.MM.yyyy";
        String formattedString = "11.03.2015";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.WEEK);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }   
    
    @Test
    public void generateDateWithNegativeWeekOffsetTest() {
        
        //given
        int toAdd = -2;
        String format = "dd.MM.yyyy";
        String formattedString = "11.02.2015";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.WEEK);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    } 
    
    
    @Test
    public void generateDateWithPositiveDayOffsetTest() {
        
        //given
        int daystoAdd = 5;
        String format = "dd.MM.yyyy";
        String formattedString = "02.03.2015";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(daystoAdd, format, OffsetType.DAY);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }
    
    @Test
    public void generateDateWithNegativeDayOffsetTest() {
        
        //given
        int daystoAdd = -25;
        String format = "dd.MM.yyyy";
        String formattedString = "31.01.2015";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(daystoAdd, format, OffsetType.DAY);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }
    
    @Test
    public void generateDateWithNoDayOffsetTest() {
        
        //given
        int daystoAdd = 0;
        String format = "dd.MM.yyyy";
        String formattedString = "25.02.2015";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(daystoAdd, format, OffsetType.DAY);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }
    
    @Test
    public void generateDateWithPositiveHourOffsetTest() {
        
        //given
        int toAdd = 12;
        String format = "dd.MM.yyyy HH:mm:ss:SSSS";
        String formattedString = "26.02.2015 00:15:55:1234";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.HOUR);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }   
    
    @Test
    public void generateDateWithNegativeHourOffsetTest() {
        
        //given
        int toAdd = -13;
        String format = "dd.MM.yyyy HH:mm:ss:SSSS";
        String formattedString = "24.02.2015 23:15:55:1234";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.HOUR);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    } 
    
    @Test
    public void generateDateWithPositiveMinuteOffsetTest() {
        
        //given
        int toAdd = 46;
        String format = "dd.MM.yyyy HH:mm:ss:SSSS";
        String formattedString = "25.02.2015 13:01:55:1234";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.MINUTE);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }   
    
    @Test
    public void generateDateWithNegativeMinuteOffsetTest() {
        
        //given
        int toAdd = -76;
        String format = "dd.MM.yyyy HH:mm:ss:SSSS";
        String formattedString = "25.02.2015 10:59:55:1234";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.MINUTE);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    } 
    
    @Test
    public void generateDateWithPositiveSecondOffsetTest() {
        
        //given
        int toAdd = 6;
        String format = "dd.MM.yyyy HH:mm:ss:SSSS";
        String formattedString = "25.02.2015 12:16:01:1234";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.SECOND);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }   
    
    @Test
    public void generateDateWithNegativeSecondOffsetTest() {
        
        //given
        int toAdd = -56;
        String format = "dd.MM.yyyy HH:mm:ss:SSSS";
        String formattedString = "25.02.2015 12:14:59:1234";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.SECOND);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    } 
    
    @Test
    public void generateDateWithPositiveMilliSecondOffsetTest() {
        
        //given
        int toAdd = 877;
        String format = "dd.MM.yyyy HH:mm:ss:SSSS";
        String formattedString = "25.02.2015 12:15:56:0004";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.MILLISECOND);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }   
    
    @Test
    public void generateDateWithNegativeMilliSecondOffsetTest() {
        
        //given
        int toAdd = -120;
        String format = "dd.MM.yyyy HH:mm:ss:SSSS";
        String formattedString = "25.02.2015 12:15:55:0034";

        DateGenerator dateGenerator = PowerMockito.spy(new DateGenerator());
        LocalDateTime localdateTimeFixed = LocalDateTime.of(2015, Month.FEBRUARY, 25, 12, 15, 55, 123456789);
        
        // expectation
        Mockito.when(dateGenerator.createLocalDateTime()).thenReturn(localdateTimeFixed);

        // when 
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(toAdd, format, OffsetType.MILLISECOND);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    } 
    
}


