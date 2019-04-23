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

package org.testeditor.fixture.commons.io.json;

import static org.testeditor.fixture.core.FixtureException.keyValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testeditor.fixture.core.FixtureException;
import org.testeditor.fixture.core.interaction.FixtureMethod;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JsonLoader {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final String DEFAULT_CSV_DELIMITER = ";";
    public static final String DEFAULT_TABLE_DELIMITER = "|";

    @FixtureMethod
    public Iterable<JsonObject> loadFromCsv(String resourceName) throws FixtureException {
        return loadFromCsv(resourceName, DEFAULT_CHARSET, DEFAULT_CSV_DELIMITER);
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromCsv(String resourceName, String charset) throws FixtureException {
        return loadFromCsv(resourceName, Charset.forName(charset), DEFAULT_CSV_DELIMITER);
    }
    
    @FixtureMethod
    public Iterable<JsonObject> loadFromCsv(String resourceName, String charset, String delimiter)
            throws FixtureException {
        return loadFromCsv(resourceName, Charset.forName(charset), delimiter);
    }
    
    private Iterable<JsonObject> loadFromCsv(String resourceName, Charset charset, String delimiter)
            throws FixtureException {
        BufferedReader reader = resourceReader(resourceName, charset);
        try {
            String[] propertyNames = reader.readLine().split(delimiter);
            return jsonObjects(reader.lines(), delimiter, propertyNames, (line, delimiter0) -> line.split(delimiter0));
        } catch (IOException exception) {
            throw new FixtureException("Could not load test data.", 
                    keyValues("resourceName", resourceName, "charset", charset, "delimiter", delimiter),
                    exception);
        }
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromTable(String table) throws FixtureException {
        return loadFromTable(table, DEFAULT_TABLE_DELIMITER);
    }
    
    /**
     * Accepts a string containing an ASCII-table and returns an iterable of
     * JsonObjects corresponding to the table's rows.
     * The first row of the table is assumed to contain column names.
     * @param table an ASCII table serving as input.
     * @param delimiter a character (sequence) delimiting columns, e.g. '|'.
     * @return an iterable of JsonObjects corresponding to the table's rows.
     * @throws FixtureException if the table cannot be processed.
     */
    @FixtureMethod
    public Iterable<JsonObject> loadFromTable(String table, String delimiter) throws FixtureException {
        String[] lines = table.trim().split("\\r?\\n");
        String[] propertyNames = processTableLine(lines[0], delimiter);
        return jsonObjects(Arrays.stream(lines).skip(1), delimiter, propertyNames, this::processTableLine);
    }
    
    private Iterable<JsonObject> jsonObjects(Stream<String> lines, String delimiter, String[] propertyNames,
            ValueExtractor toValues) {
        return lines.map((line) -> {
            JsonObject jsonObject = new JsonObject();
            String[] values = toValues.apply(line, delimiter);
            for (int i = 0; i < Math.min(propertyNames.length, values.length); i++) {
                jsonObject.add(propertyNames[i], new JsonPrimitive(values[i]));
            }
            return jsonObject;
        }).collect(Collectors.toList());
    }

    private BufferedReader resourceReader(String resourceName, Charset charset) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
        return new BufferedReader(new InputStreamReader(inputStream, charset));
        
    }
    
    private String[] processTableLine(String line, String delimiter) {
        String trimmedLine = line.trim();
        if (trimmedLine.startsWith(delimiter)) {
            trimmedLine = trimmedLine.substring(delimiter.length()).trim();
        }
        if (trimmedLine.endsWith(delimiter)) {
            trimmedLine = trimmedLine.substring(0, trimmedLine.length() - delimiter.length()).trim();
        }
        return Arrays.stream(trimmedLine.split(Pattern.quote(delimiter)))
                .map((value) -> value.trim())
                .toArray(String[]::new);
    }
    
    @FunctionalInterface
    interface ValueExtractor extends BiFunction<String, String, String[]> {
        @Override
        String[] apply(String line, String delimiter);
    }
}
