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

package org.testeditor.fixture.commons.text.generate;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class NameGeneratorTest {

    @Test
    void generateRandomFirstNameTest() {
        String randomName = NameGenerator.getRandomFirstName();
        Assert.assertNotNull(randomName);
        Assert.assertTrue(randomName.length() > 0);
    }
    
    @Test
    void generateFirstNameWithSeedTest() {
        String randomName = NameGenerator.getFirstName(32);
        Assert.assertEquals("Briana", randomName);
    }
      
    @Test
    void generateRandomFullname() {
        String randomName = NameGenerator.getRandomFullName();
        Assert.assertNotNull(randomName);
        Assert.assertTrue(randomName.length() > 0);
        Assert.assertTrue(randomName.matches("[a-zA-Z]*[\\s]{1}[a-zA-Z].*"));
    }

    @Test
    void generateFullnameWithSeed() {
        String randomName = NameGenerator.getFullName(32);
        Assert.assertEquals("Briana Dimpfl", randomName);
    }
    
    @Test
    void generateRandomLastNameTest() {
        String randomName = NameGenerator.getRandomLastName();
        Assert.assertNotNull(randomName);
        Assert.assertTrue(randomName.length() > 0);
    }
    
    @Test
    void generateLastNameWithSeedTest() {
        String randomName = NameGenerator.getLastName(32);
        Assert.assertEquals("Dimpfl", randomName);
    }
    
    @Test
    void generateRandomCompanyNameTest() {
        String randomName = NameGenerator.getRandomCompanyName();
        Assert.assertNotNull(randomName);
        Assert.assertTrue(randomName.length() > 0);
    }
    
    @Test
    void generateCompanyNameWithSeedTest() {
        String randomName = NameGenerator.getCompanyName(32);
        Assert.assertEquals("Yingli Green Energy Holding Company Limited", randomName);
    }
    
    @Test
    void generateRandomPersonFieldsNameTest() {
        String[] personFields = NameGenerator.getRandomPersonFields();
        Assert.assertNotNull(personFields);
        Assert.assertTrue(personFields.length == 5);
    }
    
    @Test
    void generatePersonFieldsNameTest() {
        String[] personFields = NameGenerator.getPersonFields(32);
        Assert.assertEquals("Broiana", personFields[0]);
        Assert.assertEquals("Dimpfl", personFields[1]);
        Assert.assertEquals("Yingli Green Energy Holding Company Limited", personFields[2]);
        Assert.assertEquals("yingligreenenergyholdingcompanylimited.com", personFields[3]);
        Assert.assertEquals("brianadimpfl@yingligreenenergyholdingcompanylimited.com", personFields[4]);
    }
    
    @Test
    void generatePersonFieldsWithGermanNamesTest() {
        long seed = 32;
        NameGenerator nameGen = new NameGenerator(seed);
        String[] personFields = nameGen.getExtendedPersonFields(seed);
        Assert.assertEquals("Briana", personFields[0]);
        Assert.assertEquals("Dimpfl", personFields[1]);
        Assert.assertEquals("Yingli Green Energy Holding Company Limited", personFields[2]);
        Assert.assertEquals("yingligreenenergyholdingcompanylimited.com", personFields[3]);
        Assert.assertEquals("brianadimpfl@yingligreenenergyholdingcompanylimited.com", personFields[4]);
    }
    
}


