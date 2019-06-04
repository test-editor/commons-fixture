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

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.createNumber;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;
import static org.testeditor.fixture.core.FixtureException.keyValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.testeditor.fixture.core.FixtureException;
import org.testeditor.fixture.core.interaction.FixtureMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Streams;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JsonLoader {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final char DEFAULT_CSV_DELIMITER = ';';
    public static final String DEFAULT_TABLE_DELIMITER = "|";
    public static final Set<String> VALID_BOOLEAN_LITERALS = unmodifiableSet(
            newHashSet("true", "True", "TRUE", "false", "False", "FALSE"));

    @FixtureMethod
    public Iterable<JsonObject> loadFromYaml(String resourceName) throws FixtureException {
        return loadFromYaml(resourceName, DEFAULT_CHARSET);
    }

    /**
     * Accepts a path to a YAML file expected to contain a collection of mapping collections,
     * and returns an Iterable of JsonObjects, each corresponding to a mapping collection.
     * For example:
     * <pre>
     * - firstName: John
     *   lastName: Doe
     * - firstName: Jane
     *   lastName: Doe
     * </pre>
     * would be represented as an Iterable of two JsonObjects, each with the properties
     * "firstName" and "lastName", and the corresponding values:
     * <pre>
     * [{
     *     "firstName": "John",
     *     "lastName": "Doe"
     * }, {
     *     "firstName": "Jane",
     *     "lastName": "Doe"
     * }]
     * </pre>
     * @param resourceName path to the YAML file to read
     * @param charset character set to use for reading the file
     * @return an Iterable of JsonObjects corresponding to the YAML file contents
     * @throws FixtureException if the YAML file cannot be processed
     */
    @SuppressWarnings("serial")
    @FixtureMethod
    public Iterable<JsonObject> loadFromYaml(String resourceName, Charset charset) throws FixtureException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        ObjectMapper jsonWriter = new ObjectMapper();
        Gson jsonReader = new Gson();
        try {
            return jsonReader.fromJson(
                    jsonWriter.writeValueAsString(
                            yamlReader.readValue(resourceReader(resourceName, charset), Object.class)),
                    new TypeToken<Collection<JsonObject>>() {
                    }.getType());
        } catch (IOException exception) {
            throw new FixtureException("Could not load test data.",
                    keyValues("resourceName", resourceName, "charset", charset), exception);
        }
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromJson(String resourceName) throws FixtureException {
        return loadFromJson(resourceName, DEFAULT_CHARSET);
    }

    @SuppressWarnings("serial")
    @FixtureMethod
    public Iterable<JsonObject> loadFromJson(String resourceName, Charset charset) throws FixtureException {
        return new Gson().fromJson(resourceReader(resourceName, charset), new TypeToken<Collection<JsonObject>>() {
        }.getType());
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromCsv(String resourceName) throws FixtureException {
        return loadFromCsv(resourceName, DEFAULT_CHARSET, DEFAULT_CSV_DELIMITER);
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromCsv(String resourceName, String charset) throws FixtureException {
        return loadFromCsv(resourceName, Charset.forName(charset), DEFAULT_CSV_DELIMITER);
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromCsv(String resourceName, Charset charset, char delimiter)
            throws FixtureException {
        return loadCsv(resourceName, charset, delimiter, CSVFormat.DEFAULT);
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromExcelCsv(String resourceName) throws FixtureException {
        return loadCsv(resourceName, DEFAULT_CHARSET, DEFAULT_CSV_DELIMITER, CSVFormat.EXCEL);
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromExcelCsv(String resourceName, Charset charset) throws FixtureException {
        return loadCsv(resourceName, charset, DEFAULT_CSV_DELIMITER, CSVFormat.EXCEL);
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromExcelCsv(String resourceName, Charset charset, char delimiter)
            throws FixtureException {
        return loadCsv(resourceName, charset, delimiter, CSVFormat.EXCEL);
    }

    @FixtureMethod
    public Iterable<JsonObject> loadFromTable(String table) throws FixtureException {
        return loadFromTable(table, DEFAULT_TABLE_DELIMITER);
    }

    /**
     * Accepts a string containing an ASCII-table and returns an iterable of
     * JsonObjects corresponding to the table's rows. The first row of the table is
     * assumed to contain column names.
     * 
     * @param table an ASCII table serving as input.
     * @param delimiter a character (sequence) delimiting columns, e.g. '|'.
     * @return an iterable of JsonObjects corresponding to the table's rows.
     * @throws FixtureException if the table cannot be processed.
     */
    @FixtureMethod
    public Iterable<JsonObject> loadFromTable(String table, String delimiter) throws FixtureException {
        String[] lines = table.trim().split("\\r?\\n");
        // collect in list to provide a "fresh" stream each time around (streams cannot
        // be reused!)
        List<String> propertyNames = processTableLine(lines[0], delimiter).collect(toList());
        return jsonObjects(Arrays.stream(lines).skip(1), delimiter, () -> propertyNames.stream(),
            (record) -> jsonPrimitives(processTableLine(record, delimiter))).collect(toList());
    }

    private Iterable<JsonObject> loadCsv(String resourceName, Charset charset, char delimiter, CSVFormat format)
            throws FixtureException {
        try {
            CSVParser records = format.withDelimiter(delimiter).withFirstRecordAsHeader()
                    .parse(resourceReader(resourceName, charset));
            // collect in list to provide a "fresh" stream each time around (streams cannot
            // be reused!)
            List<String> propertyNames = records.getHeaderMap().entrySet().stream().sorted(Map.Entry.comparingByValue())
                    .map((entry) -> entry.getKey()).collect(toList());

            return jsonObjects(stream(records), () -> propertyNames.stream(),
                (record) -> jsonPrimitives(stream(record))).collect(toList());
        } catch (IOException exception) {
            throw new FixtureException("Could not load test data.",
                    keyValues("resourceName", resourceName, "charset", charset, "delimiter", delimiter), exception);
        }
    }

    private <T> Stream<JsonObject> jsonObjects(Stream<T> records, Supplier<Stream<String>> propertyNames,
            ValueExtractor<T> toValues) {
        return this.jsonObjects(records, null, propertyNames, toValues);
    }

    private <T> Stream<JsonObject> jsonObjects(Stream<T> records, String delimiter,
            Supplier<Stream<String>> propertyNames, ValueExtractor<T> toValues) {
        return records.map((record) -> {
            JsonObject jsonObject = new JsonObject();
            Streams.forEachPair(propertyNames.get(), toValues.apply(record),
                (name, value) -> jsonObject.add(name, value));
            return jsonObject;
        });
    }

    private BufferedReader resourceReader(String resourceName, Charset charset) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
        return new BufferedReader(new InputStreamReader(inputStream, charset));

    }

    private Stream<String> processTableLine(String line, String delimiter) {
        String trimmedLine = line.trim();
        if (trimmedLine.startsWith(delimiter)) {
            trimmedLine = trimmedLine.substring(delimiter.length()).trim();
        }
        if (trimmedLine.endsWith(delimiter)) {
            trimmedLine = trimmedLine.substring(0, trimmedLine.length() - delimiter.length()).trim();
        }
        return Arrays.stream(trimmedLine.split(Pattern.quote(delimiter))).map((value) -> value.trim());
    }

    private Stream<JsonPrimitive> jsonPrimitives(Stream<String> values) {
        return values.map((value) -> {
            JsonPrimitive result = null;
            if (isCreatable(value)) {
                result = new JsonPrimitive(createNumber(value));
            } else if (VALID_BOOLEAN_LITERALS.contains(value)) {
                result = new JsonPrimitive(Boolean.parseBoolean(value));
            } else {
                result = new JsonPrimitive(value);
            }
            return result;
        });
    }

    @FunctionalInterface
    interface ValueExtractor<T> extends Function<T, Stream<JsonPrimitive>> {
        @Override
        Stream<JsonPrimitive> apply(T record);
    }

    private static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
