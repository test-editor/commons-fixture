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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class DateGeneratorTest {
    
    @Test
    public void generateDateFromNowWithPositiveOffsetTest() {
        
        // given
        int daystoAdd = 10;
        String format = "dd.MM.yyyy";
        LocalDateTime timePoint = LocalDateTime.now();
        LocalDateTime days = timePoint.plusDays(daystoAdd);
        LocalDate localDate = days.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedString = localDate.format(formatter);
        
        // when
        DateGenerator dateGenerator = new DateGenerator();
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(daystoAdd , format);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }

    @Test
    public void generateDateFromNowWithNegativeOffsetTest() {
        
        // given
        int daystoAdd = -10;
        String format = "dd.MM.yyyy";
        LocalDateTime timePoint = LocalDateTime.now();
        LocalDateTime days = timePoint.plusDays(daystoAdd);
        LocalDate localDate = days.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedString = localDate.format(formatter);
        
        // when
        DateGenerator dateGenerator = new DateGenerator();
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(daystoAdd , format);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }
    
    @Test
    public void generateDateFromNowWithNoOffsetTest() {
        
        // given
        int daystoAdd = 0;
        String format = "dd.MM.yyyy";
        LocalDateTime timePoint = LocalDateTime.now();
        LocalDateTime days = timePoint.plusDays(daystoAdd);
        LocalDate localDate = days.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedString = localDate.format(formatter);
        
        // when
        DateGenerator dateGenerator = new DateGenerator();
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(daystoAdd , format);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }
    
    @Test
    public void generateDateFromNowWithMoreThanOneYearOffsetTest() {
        
        // given
        int daystoAdd = 366;
        String format = "dd.MM.yyyy";
        LocalDateTime timePoint = LocalDateTime.now();
        LocalDateTime days = timePoint.plusDays(daystoAdd);
        LocalDate localDate = days.toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedString = localDate.format(formatter);
        
        // when
        DateGenerator dateGenerator = new DateGenerator();
        String birthdateWithOffset = dateGenerator.generateDateFromNowWithOffset(daystoAdd , format);
        
        // then
        Assert.assertEquals(formattedString, birthdateWithOffset);
    }
    
}


