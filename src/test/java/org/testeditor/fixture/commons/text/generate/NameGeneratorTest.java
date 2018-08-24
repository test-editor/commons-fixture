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

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.testeditor.fixture.core.FixtureException;

class NameGeneratorTest {

    @Test
    void generateRandomFirstNameTest() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();
        
        // when 
        String randomName = generator.getRandomFirstName();
        
        // then
        Assert.assertNotNull(randomName);
        Assert.assertTrue(randomName.length() > 0);
    }
    
    @Test
    void generateFirstNameWithSeedTest() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();
        
        // when        
        String randomName = generator.getFirstName(32);
        
        // then
        Assert.assertEquals("Ugochi", randomName);
    }
      
    @Test
    void generateRandomFullname() throws FixtureException {
        // given        
        NameGenerator generator = new NameGenerator();
        
        // when        
        String randomName = generator.getRandomFullName();
        
        // then
        Assert.assertNotNull(randomName);
        Assert.assertTrue(randomName.length() > 0);
        //\p{L} - any Unicode letter. \p{M} - any diacritic. \s - whitespace, ' and - are literal single quote and -
        Assert.assertTrue(randomName.matches("(?U)[\\p{L}\\p{M}]+(?:[\\s'-][\\p{L}\\p{M}]+)*"));
    }

    @Test
    void generateFullnameWithSeed() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();

        // when        
        String randomName = generator.getFullName(32);

        // then       
        Assert.assertEquals("Ugochi Strohmeier", randomName);
    }
    
    @Test
    void generateRandomLastNameTest() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();
        
        // when         
        String randomName = generator.getRandomLastName();
        
        // then        
        Assert.assertNotNull(randomName);
        Assert.assertTrue(randomName.length() > 0);
    }
    
    @Test
    void generateLastNameWithSeedTest() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();
        
        // when         
        String randomName = generator.getLastName(32);
        
        // then        
        Assert.assertEquals("Strohmeier", randomName);
    }
    
    @Test
    void generateRandomCompanyNameTest() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();

        // when         
        String randomName = generator.getRandomCompanyName();

        // then        
        Assert.assertNotNull(randomName);
        Assert.assertTrue(randomName.length() > 0);
    }
    
    @Test
    void generateCompanyNameWithSeedTest() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();

        // when         
        String randomName = generator.getCompanyName(32);

        // then        
        Assert.assertEquals("Fortune Brands Home & Security", randomName);
    }
    
    @Test
    void generateRandomPersonFieldsNameTest() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();
        
        // when         
        String[] personFields = generator.getRandomPersonFields();
        
        // then        
        Assert.assertNotNull(personFields);
        Assert.assertTrue(personFields.length == 5);
    }
    
    @Test
    void generatePersonFieldsNameTest() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();
        
        // when         
        String[] personFields = generator.getPersonFields(32);
        
        // then        
        Assert.assertEquals("Ugochi", personFields[0]);
        Assert.assertEquals("Strohmeier", personFields[1]);
        Assert.assertEquals("Fortune Brands Home & Security", personFields[2]);
        Assert.assertEquals("fortunebrandshomesecurity.com", personFields[3]);
        Assert.assertEquals("ugochistrohmeier@fortunebrandshomesecurity.com", personFields[4]);
    }
    
    @Test
    void generatePersonFieldsWithUmlautTest() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();
        long seed = 1;
        
        // when         
        String[] personFields = generator.getPersonFields(seed);
        
        // then        
        Assert.assertEquals("Özşan", personFields[0]);
        Assert.assertEquals("Marois", personFields[1]);
        Assert.assertEquals("Manning & Napier", personFields[2]);
        Assert.assertEquals("manningnapier.com", personFields[3]);
        Assert.assertEquals("zanmarois@manningnapier.com", personFields[4]);
    }
    
    
    @Test
    void getExceptionWhenLoadingNames() {
        // given
        String ecpectedResourceName = "notExisting.csv";

        //when 
        Throwable exception = assertThrows(FixtureException.class, () -> {
            NameGenerator.loadNames(ecpectedResourceName);
        });
        
        // then
        assertEquals("Exception occured during loading names", exception.getMessage());
        
        Map<String, Object> keyValueStore = ((FixtureException) exception).getKeyValueStore();
        String actualResourceName = (String) keyValueStore.get("resourceName");
        Assert.assertEquals(ecpectedResourceName, actualResourceName);
        
    }
}


