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

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.testeditor.fixture.commons.io.FileReader;

public class FileReaderTest {

    static final String lineSeparator = System.getProperty("line.separator");
        
    String result = "firefox-cap1 = \"abcdüöß\"" + lineSeparator  
            + "firefox-cap2 = 8" + lineSeparator  
            + "firefox-cap3 = true";

    @Test
    public void testReadFile() throws Exception {
        FileReader reader = new FileReader();
        String fileContentAsString = reader.getFileContentAsString("utf8EncodedTextWithUmlaut.txt");
        Assert.assertEquals(result, fileContentAsString);
    }
    
    @Test
    public void testReadfirstnames() throws Exception {
        FileReader reader = new FileReader();
        String fileContentAsString = reader.getFileContentAsString("names/firstnames.csv");
        Assert.assertTrue(fileContentAsString.startsWith("Aabid"));
    }
    
    @Test
    public void testReadlastnames() throws Exception {
        FileReader reader = new FileReader();
        String fileContentAsString = reader.getFileContentAsString("names/lastnames.csv");
        Assert.assertTrue(fileContentAsString.startsWith("Aaberg"));
    }
    
    @Test
    public void testloadfirstnames() throws Exception {
        FileReader reader = new FileReader();
        List<String> names = reader.loadNames("names/lastnames.csv");
        Assert.assertEquals(names.get(0), "Aaberg");
    }
    
    


}
