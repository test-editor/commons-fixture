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
import org.testeditor.fixture.commons.text.NameGenerator;

import com.google.common.io.CharStreams;




public class FileReader {
    
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);
    

    /**
     * Opens a file defined as fileName and read the content line by line.
     * 
     * @return File content as String
     * @throws IOException 
     */
    public String getFileContentAsString(String fileName) throws IOException {

        // Get file from resources folder
        InputStream inputStream = resolveInputStream(fileName, getClass()); 
        return CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }
    
    /**
     * Opens a file and extract all names in a line and return a List of Strings.
     * @param resourceName 
     * @return names in each separate line
     */
    public  List<String> loadNames(String resourceName) {
        List<String> names = new ArrayList<>();
        // use classloader that loaded the jar with this class to ensure we can get the csv's
      
        BufferedReader br = new BufferedReader(new InputStreamReader(
                resolveInputStream(resourceName, NameGenerator.class), StandardCharsets.UTF_8)) ;
        br.lines().map(String::trim).filter(line -> line.length() > 0).forEach(names::add);
        logger.debug("All data loaded from file {}", resourceName);
        return Collections.unmodifiableList(names);
    }
    
    private InputStream resolveInputStream(String resourceName, Class<?> className) {
        return className.getClassLoader().getResourceAsStream(resourceName);
    }
    

}
