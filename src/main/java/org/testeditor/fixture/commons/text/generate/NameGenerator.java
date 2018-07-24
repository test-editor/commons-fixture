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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
 * Limitations: the names are English and do not contain any special characters. To test with german, umlauts, turkish, 
 * arabian, polish, french, portuguese or spanish special characters please use the csv files under 
 * src/main/resources/names.
 *
 */
public class NameGenerator {
    
    private RandomNameGenerator randomNameGenerator = null;
    
    
    /**
     * 
     * @param seed
     */
    public NameGenerator(long seed) {
        randomNameGenerator = new RandomNameGenerator(loadNames("names/firstnames.csv"),
               loadNames("names/lastnames.csv"),loadNames("namescompanies.csv"));
        System.err.println("What");
       
    }
    
    private static List<String> loadNames(String resource) {
        List<String> names = new ArrayList<>();
        // use classloader that loaded the jar with this class to ensure we can get the csvs
        try (BufferedReader br = new BufferedReader(new InputStreamReader(RandomNameGenerator.class
                .getClassLoader().getResourceAsStream(resource), StandardCharsets.UTF_8))) {
            br.lines().map(String::trim).filter(line -> line.length() > 0).forEach(names::add);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return Collections.unmodifiableList(names);
    }       

    public static String getRandomFirstName() {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(System.currentTimeMillis());
        return randomNameGenerator.nextFirstName();
    }
    
    public static String getFirstName(long seed) {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(seed);
        return randomNameGenerator.nextFirstName();
    }
    
    public static String getRandomFullName() {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(System.currentTimeMillis());
        return randomNameGenerator.nextFullName();
    }
    
    public static String getFullName(long seed) {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(seed);
        return randomNameGenerator.nextFullName();
    }
    
    public static String getRandomLastName() {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(System.currentTimeMillis());
        return randomNameGenerator.nextLastName();
    }

    public static String getLastName(long seed) {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(seed);
        return randomNameGenerator.nextLastName();
    }
    
    public static String getRandomCompanyName() {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(System.currentTimeMillis());
        return randomNameGenerator.nextCompanyName();
    }
    
    public static String getCompanyName(long seed) {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(seed);
        return randomNameGenerator.nextCompanyName();
    }
    
    public static String[] getRandomPersonFields() {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(System.currentTimeMillis());
        return randomNameGenerator.nextPersonFields();
    }
    
    public static String[] getPersonFields(long seed) {
        RandomNameGenerator randomNameGenerator = new RandomNameGenerator(seed);
        return randomNameGenerator.nextPersonFields();
    }
    
    public String[] getExtendedPersonFields(long seed) {
        return this.randomNameGenerator.nextPersonFields();
    }

}


