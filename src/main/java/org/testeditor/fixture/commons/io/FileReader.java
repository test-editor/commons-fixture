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

package org.testeditor.fixture.commons.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testeditor.fixture.commons.text.generate.NameGenerator;
import org.testeditor.fixture.core.FixtureException;

import com.google.common.io.CharStreams;

import io.inbot.testfixtures.RandomNameGenerator;


public class FileReader {
    
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);

    /**
     * Opens a file defined as fileName and read the content line by line.
     * 
     * @return File content as String
     */
    public String getFileContentAsString(String fileName) throws FixtureException {

        // Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
       
        String result = null;
        try {
            result = CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.info("The file with the name {} can not be read in the resource folder. {}" , fileName, e);
            throw new FixtureException("file could not be found in resource folder",
                    FixtureException.keyValues("fileName", fileName));
        } catch (NullPointerException e) {
            logger.info("The file with the name {} can not be found in the resource folder.", fileName);
            result = "";
        }
        return result;
    }
    
    /**
     * 
     * @param resource
     * @return
     */
    public  List<String> loadNames(String resource) {
        List<String> names = new ArrayList<>();
        // use classloader that loaded the jar with this class to ensure we can get the csv's
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                NameGenerator.class.getClassLoader().getResourceAsStream(resource), StandardCharsets.UTF_8))) {
            br.lines().map(String::trim).filter(line -> line.length() > 0).forEach(names::add);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return Collections.unmodifiableList(names);
    }

}
