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

import com.google.gson.JsonObject;

import io.inbot.testfixtures.Person;
import io.inbot.testfixtures.RandomNameGenerator;


/**
 * Simple name generator for first name, last name that generates unique names using a resource file where each line
 * is a data set. Or also a Person with email address, company, first and last name. 
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
public class NameGenerator {
    
    private static final Logger logger = LoggerFactory.getLogger(NameGenerator.class);
    private static final String firstNames = "names/firstnames.csv";
    private static final String lastNames = "names/lastnames.csv";
    private static final String companyNames = "names/companies.csv";
    private final RandomNameGenerator generator;
    private long seed;
    
    
    
    /**
     * Generates names, with email addresses and company names. 
     */
    public NameGenerator()  {
        logger.trace("***************************************************************************");
        logger.trace("Beginning to lo load files ...");
        generator = new RandomNameGenerator(getNameList(firstNames),getNameList(lastNames),getNameList(companyNames));
        logger.trace("All files  loaded");
        logger.trace("***************************************************************************");
    }
    
    /**
     * Generated person as JsonObject for usage in test cases. 
     * The usable keywords for the Person JsonObject are: 
     *  <ul>
     *    <li>firstName</li>
     *    <li>lastName</li> 
     *    <li>fullName</li> 
     *    <li>userName</li> 
     *    <li>companyName</li> 
     *    <li>domainName</li> 
     *    <li>email</li> 
     *  </ul>
     *  <pre>
     *  
     *  usage in AML : 
     *  interaction type createPerson {
     *    template = "Create Person" 
     *     method = NameGenearator.getPerson
     *   }
     *   
     *  usage in TCL :
     *  person = Create Person
     *  assert person.firstName is "Ferdinand"
     *  </pre>
     *  
     *  For all the other keywords the approach is corresponding in TCL.
     * @return A JsonObject with the literals of given keywords above.
     * @throws FixtureException When an error occurs during creation of a person field.
     */
    @FixtureMethod
    public JsonObject getPerson() throws FixtureException {
        JsonObject person = new JsonObject();
        try {
            Person nextPerson = generator.nextPerson();
            person.addProperty("firstName", nextPerson.getFirstName());
            person.addProperty("lastName", nextPerson.getLastName());
            person.addProperty("fullName", nextPerson.getFullName());
            person.addProperty("userName", nextPerson.getUserName());
            person.addProperty("companyName", nextPerson.getCompany());
            person.addProperty("domainName", nextPerson.getDomainName());
            person.addProperty("email", nextPerson.getEmail());
            logger.debug("Got following person data : " + personToString(nextPerson));
        } catch (Exception e) {
            throw new FixtureException("Exception occured, creating a person field with the seed: " + getSeed(), //
                FixtureException.keyValues("seed", getSeed()), e);
        }
        
        return person;
    }
    
    
    /**
     * Reads all names from given resource file and offers a List of Strings. 
     * @param resource File with names per line for every new entry. 
     * @return List of names, one per line. 
     * @throws FixtureException An error can occur reading the resource file.  
     */
    protected List<String> loadNames(String resource) throws FixtureException  {
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
     * Shuffles a given list of names in a structured order with the aid of a given seed as a number (long).
     * This is for using the same tests for analyzing bugs 
     * @throws FixtureException Exception occurs when something is wrong during shuffle.
     */
    @FixtureMethod
    public void shuffle(long seed) throws FixtureException {
        this.seed = seed;
        logger.debug("Used seed for shuffle : {}", seed);
        try {
            generator.shuffle(new Random(seed));
        } catch (Exception e) {
            throw new FixtureException("Exception occured during shuffling names with seed: " + getSeed() , //
                    FixtureException.keyValues("resourceName", getSeed()), e);

        }
    }
    
    /**
     * Shuffles a given list of names in a random order.
     * This is for usage in tests if using random names is desired.
     * @throws FixtureException Exception occurs when something is wrong during shuffle.
     */
    @FixtureMethod
    public void shuffleRandom() throws FixtureException {
        this.shuffle(System.nanoTime());
    }

    protected long getSeed() {
        return seed;
    }

    protected void setSeed(long seed) {
        this.seed = seed;
    }

    private List<String> getNameList(String filename) {
        List<String> list = null;
        try {
            list = loadNames(filename);
        } catch (FixtureException e) {
            logger.error("Exception occured during loading names{}" , e.getMessage());
        }
        
        return list;
    }
    
    private String personToString(Person person) {
        return "Person [ firstName='" + person.getFirstName() 
            + "', lastName='" + person.getLastName() 
            + "', fullName='" + person.getFullName() 
            + "', company='" + person.getCompany() 
            + "', userName='" + person.getUserName() 
            + "', email='" + person.getEmail() 
            + "', domainName='" + person.getDomainName() + "' ]";
    }
    
}


