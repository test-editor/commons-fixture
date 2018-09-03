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

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testeditor.fixture.commons.io.FileReader;
import org.testeditor.fixture.core.FixtureException;
import org.testeditor.fixture.core.interaction.FixtureMethod;

import io.inbot.testfixtures.RandomNameGenerator;


/**
 * Simple name generator for first name, last name that generates unique names using a csv file. Or also a Person with
 * email address, company, first and last name. 
 *
 * The number of unique first and last name combinations ranges well into the millions which means you have to generate
 * millions before you start encountering duplicates. This property, makes this generator ideal as a test fixture for 
 * unit and integration tests.
 *
 * The random seed can be controlled via the constructor of this class. This ensures that you can regenerate the same
 * sequence of names if you use the same seed.
 *
 * The names are in english ,german with umlauts, turkish, arabian, polish, french, portuguese or spanish 
 * special characters. The resources can be found under src/main/resources/names.
 *
 */
public class NameGenerator extends RandomNameGenerator {
    
    private static final Logger logger = LoggerFactory.getLogger(NameGenerator.class);
    private static final String firstNames = "names/firstnames.csv";
    private static final String lastNames = "names/lastnames.csv";
    private static final String companyNames = "names/companies.csv";
    
    
    
    /**
     * Generates names, with email addresses and company names. 
     * @throws FixtureException 
     */
    public NameGenerator() throws FixtureException {
        super(loadNames(firstNames),
               loadNames(lastNames),loadNames(companyNames));
        super.shuffle(new Random());
    }
    
    /**
     * Generates names, with email addresses and company names. 
     * @param seed  The random seed ensures that you can regenerate 
     * the same sequence of names if you use the same seed.
     * @throws FixtureException 
     */
    public NameGenerator(long seed) throws FixtureException  {
        super(loadNames(firstNames),loadNames(lastNames),loadNames(companyNames));
        shuffle(new Random(seed));
    }
    
    protected static List<String> loadNames(String resource) throws FixtureException {
        FileReader reader = new FileReader();
        List<String> loadedNames = null;
        try {
            loadedNames = reader.loadNames(resource);
        } catch (Exception e) {
            throw new FixtureException("Exception occured during loading names", //
                    FixtureException.keyValues("resourceName", resource), e);
        }
        return loadedNames;
    }       

    /**
     * @return Random generated first name.
     */
    @FixtureMethod
    public String getRandomFirstName() throws FixtureException {
        return getFirstName(System.currentTimeMillis());
    }
    
    /**
     * @return Generated first name  given seed.
     */
    @FixtureMethod
    public String getFirstName(long seed) throws FixtureException {
        String name  = null;
        try {
            NameGenerator nameGenerator = new NameGenerator(seed);
            name = nameGenerator.nextFirstName();
        } catch (Exception e) {
            throw new FixtureException("Exception occured, creating a first name with the seed: " + seed, //
                    FixtureException.keyValues("seed", seed), e);
        }
        logger.debug("For getFirstName used seed is {}", seed) ;
        return name;
    }
    
    /**
     * @return Random generated full name.
     */
    @FixtureMethod
    public String getRandomFullName() throws FixtureException {
        return getFullName(System.currentTimeMillis());
    }
    
    /**
     * @return Generated full name with given seed.
     */
    @FixtureMethod
    public String getFullName(long seed) throws FixtureException {
        String name  = null;
        try {
            NameGenerator nameGenerator = new NameGenerator(seed);
            name = nameGenerator.nextFullName();
        } catch (Exception e) {
            throw new FixtureException("Exception occured, creating a full name with the seed: " + seed, //
                    FixtureException.keyValues("seed", seed), e);
        }
        logger.debug("For getFullName used seed is {}", seed) ;
        return name;
    }
    
    /**
     * @return Random generated last name.
     */
    @FixtureMethod
    public String getRandomLastName() throws FixtureException {
        return getLastName(System.currentTimeMillis());
    }

    /**
     * @return Generated last name with given seed.
     */
    @FixtureMethod
    public String getLastName(long seed) throws FixtureException {
        String name  = null;
        try {
            NameGenerator nameGenerator = new NameGenerator(seed);
            name = nameGenerator.nextLastName();
        } catch (Exception e) {
            throw new FixtureException("Exception occured, creating a last name with the seed: " + seed, //
                    FixtureException.keyValues("seed", seed), e);
        } 
        logger.debug("For getLastName used seed is {}", seed) ;
        return name;
    }
    
    /**
     * @return Random generated company name.
     */
    @FixtureMethod
    public String getRandomCompanyName() throws FixtureException {
        return getCompanyName(System.currentTimeMillis());
    }
    
    /**
     * @return Generated company name with given seed.
     */
    @FixtureMethod
    public String getCompanyName(long seed) throws FixtureException {
        String name  = null;
        try {
            NameGenerator nameGenerator = new NameGenerator(seed);
            name = nameGenerator.nextCompanyName();
        } catch (Exception e) {
            throw new FixtureException("Exception occured, creating a company name with the seed: " + seed, //
                    FixtureException.keyValues("seed", seed), e);
        }
        logger.debug("For getCompanyName used seed is {}", seed) ;
        return name;
    }
    
    /**
     * @return Random generated Person
     */    
    @FixtureMethod
    public String[] getRandomPersonFields() throws FixtureException {
        return getPersonFields(System.currentTimeMillis());
    }
    
    /**
     * @return Generated person with given seed
     */
    @FixtureMethod
    public String[] getPersonFields(long seed) throws FixtureException {
        String[] personFields  = null;
        try {
            NameGenerator nameGenerator = new NameGenerator(seed);
            personFields = nameGenerator.nextPersonFields();
        } catch (Exception e) {
            throw new FixtureException("Exception occured, creating a person field with the seed: " + seed, //
                    FixtureException.keyValues("seed", seed), e);
        }
        logger.debug("For getPersonFields used seed is {}", seed) ;
        return personFields;
    }


    
}


