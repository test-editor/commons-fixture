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
        Assert.assertEquals("Ugochi", randomName);
    }
      
    @Test
    void generateRandomFullname() {
        String randomName = NameGenerator.getRandomFullName();
        Assert.assertNotNull(randomName);
        Assert.assertTrue(randomName.length() > 0);
        //\p{L} - any Unicode letter. \p{M} - any diacritic. \s - whitespace, ' and - are literal single quote and -
        Assert.assertTrue(randomName.matches("(?U)[\\p{L}\\p{M}]+(?:[\\s'-][\\p{L}\\p{M}]+)*"));
    }

    @Test
    void generateFullnameWithSeed() {
        String randomName = NameGenerator.getFullName(32);
        Assert.assertEquals("Ugochi Strohmeier", randomName);
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
        Assert.assertEquals("Strohmeier", randomName);
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
        Assert.assertEquals("Fortune Brands Home & Security", randomName);
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
        Assert.assertEquals("Ugochi", personFields[0]);
        Assert.assertEquals("Strohmeier", personFields[1]);
        Assert.assertEquals("Fortune Brands Home & Security", personFields[2]);
        Assert.assertEquals("fortunebrandshomesecurity.com", personFields[3]);
        Assert.assertEquals("ugochistrohmeier@fortunebrandshomesecurity.com", personFields[4]);
    }
    
    @Test
    void generatePersonFieldsWithUmlautTest() {
        long seed = 1;
        String[] personFields = NameGenerator.getPersonFields(seed);
        Assert.assertEquals("Özşan", personFields[0]);
        Assert.assertEquals("Marois", personFields[1]);
        Assert.assertEquals("Manning & Napier", personFields[2]);
        Assert.assertEquals("manningnapier.com", personFields[3]);
        Assert.assertEquals("zanmarois@manningnapier.com", personFields[4]);
    }
    
}


