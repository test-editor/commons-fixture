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

package org.testeditor.fixture.commons.text;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testeditor.fixture.core.FixtureException;

import com.google.gson.JsonObject;

class NameGeneratorTest {
    
    private static final Logger logger = LoggerFactory.getLogger(NameGenerator.class);
    
    
    @Test
    public void getExceptionWhenLoadingNames() {
        // given
        String ecpectedResourceName = "notExisting.csv";

        //when 
        Throwable exception = assertThrows(FixtureException.class, () -> {
            NameGenerator generator = new NameGenerator();
            generator.loadNames(ecpectedResourceName);
        });
        
        // then
        assertEquals("Exception occured during loading names", exception.getMessage());
        
        Map<String, Object> keyValueStore = ((FixtureException) exception).getKeyValueStore();
        String actualResourceName = (String) keyValueStore.get("resourceName");
        Assert.assertEquals(ecpectedResourceName, actualResourceName);
        
    }
    
    @Test
    public void getPersonTestRandom() throws FixtureException {
        // given
        NameGenerator generator = new NameGenerator();
        generator.shuffleRandom();

        // when         
        JsonObject person = generator.getPerson();
        String firstName = person.get("firstName").getAsString();
        String lastName = person.get("lastName").getAsString();
        String fullName = person.get("fullName").getAsString();
        String userName = person.get("userName").getAsString();
        String company = person.get("companyName").getAsString();
        String domainName = person.get("domainName").getAsString();
        String email = person.get("email").getAsString();
        
        // then        
        Assert.assertNotNull(firstName);
        Assert.assertNotNull(lastName);
        Assert.assertNotNull(fullName);
        Assert.assertNotNull(userName);
        Assert.assertNotNull(company);
        Assert.assertNotNull(domainName);
        Assert.assertNotNull(email);
        logger.debug("firstName: {}", firstName);
        logger.debug("lastName: {}", lastName);
        logger.debug("fullName: {}", fullName);
        logger.debug("userName: {}", userName);
        logger.debug("companyName: {}", company);
        logger.debug("domainName: {}", domainName);
        logger.debug("email address: {}", email);
    }  
    
    @Test
    public void getPersonTestWithGivenSeed() throws FixtureException {
        // given
        long seed = 9;
        NameGenerator generator = new NameGenerator();
        generator.shuffle(seed);

        // when         
        JsonObject person = generator.getPerson();
        String firstName = person.get("firstName").getAsString();
        String lastName = person.get("lastName").getAsString();
        String fullName = person.get("fullName").getAsString();
        String userName = person.get("userName").getAsString();
        String company = person.get("companyName").getAsString();
        String domainName = person.get("domainName").getAsString();
        String email = person.get("email").getAsString();
        
        // then        
        Assert.assertEquals("Then firstName is not correct", "Özkerman", firstName);
        Assert.assertEquals("Then lastName is not correct", "Borger", lastName);
        Assert.assertEquals("Then fullName is not correct", "Özkerman Borger", fullName);
        Assert.assertEquals("Then userName is not correct", "zkermanborger", userName);
        Assert.assertEquals("Then company is not correct", "Cidara Therapeutics", company);
        Assert.assertEquals("Then domainName is not correct", "cidaratherapeutics.com", domainName);
        Assert.assertEquals("Then email address is not correct", "zkermanborger@cidaratherapeutics.com", email);
        logger.debug("firstName: {}", firstName);
        logger.debug("lastName: {}", lastName);
        logger.debug("fullName: {}", fullName);
        logger.debug("userName: {}", userName);
        logger.debug("companyName: {}", company);
        logger.debug("domainName: {}", domainName);
        logger.debug("email address: {}", email);
    }

}


