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

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.testeditor.fixture.core.FixtureException;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

class JsonLoaderTest {

    @Test
    void readsCsvFileIntoJson() throws FixtureException {
        // given
        String resource = "org/testeditor/fixture/commons/io/json/sample.csv";
        JsonLoader loader = new JsonLoader();

        // when
        Iterable<JsonObject> actual = loader.loadFromCsv(resource);

        // then
        assertNotNull(actual);
        ArrayList<JsonObject> listOfJsonObjects = Lists.newArrayList(actual);
        assertEquals(3, listOfJsonObjects.size());
        assertAll("elements", 
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("firstName").getAsString(), "Arthur"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("lastName").getAsString(), "Dent"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("age").getAsInt(), 42),

            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("firstName").getAsString(), "Ford"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("lastName").getAsString(), "Prefect"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("age").getAsInt(), 42),

            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("firstName").getAsString(), "Zaphod"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("lastName").getAsString(), "Beeblebrox"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("age").getAsInt(), 42));
    }
    
    @Test
    void readsExcelExportedCsvFileIntoJson() throws FixtureException {
        // given
        String resource = "org/testeditor/fixture/commons/io/json/excelSample.csv";
        JsonLoader loader = new JsonLoader();

        // when
        Iterable<JsonObject> actual = loader.loadFromExcelCsv(resource);

        // then
        assertNotNull(actual);
        ArrayList<JsonObject> listOfJsonObjects = Lists.newArrayList(actual);
        assertEquals(3, listOfJsonObjects.size());
        assertAll("elements", 
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("firstName").getAsString(), "Adam"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("lastName").getAsString(), "Young"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("age").getAsInt(), 11),

            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("firstName").getAsString(), "Anathema"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("lastName").getAsString(), "Device"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("age").getAsInt(), 20),

            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("firstName").getAsString(), "Newton"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("lastName").getAsString(), "Pulsifer"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("age").getAsInt(), 26));
    }
    
    @Test
    void readsJsonFileIntoJson() throws FixtureException {
        // given
        String resource = "org/testeditor/fixture/commons/io/json/sample.json";
        JsonLoader loader = new JsonLoader();

        // when
        Iterable<JsonObject> actual = loader.loadFromJson(resource);

        // then
        assertNotNull(actual);
        ArrayList<JsonObject> listOfJsonObjects = Lists.newArrayList(actual);
        assertEquals(3, listOfJsonObjects.size());
        assertAll("elements", 
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("firstName").getAsString(), "Luke"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("lastName").getAsString(), "Skywalker"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("strongWithTheForce").getAsBoolean(), true),

            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("firstName").getAsString(), "Han"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("lastName").getAsString(), "Solo"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("strongWithTheForce").getAsBoolean(), false),

            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("firstName").getAsString(), "Leia"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("lastName").getAsString(), "Organa"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("strongWithTheForce").getAsBoolean(), true));
    }

    @Test
    void readsYamlFileIntoJson() throws FixtureException {
        // given
        String resource = "org/testeditor/fixture/commons/io/json/sample.yaml";
        JsonLoader loader = new JsonLoader();

        // when
        Iterable<JsonObject> actual = loader.loadFromYaml(resource);

        // then
        assertNotNull(actual);
        ArrayList<JsonObject> listOfJsonObjects = Lists.newArrayList(actual);
        assertEquals(3, listOfJsonObjects.size());
        assertAll("elements", 
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("firstName").getAsString(), "John"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("lastName").getAsString(), "Steinbeck"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("yearOfBirth").getAsInt(), 1902),

            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("firstName").getAsString(), "Ernest"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("lastName").getAsString(), "Hemingway"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("yearOfBirth").getAsInt(), 1899),

            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("firstName").getAsString(), "Ken"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("lastName").getAsString(), "Kesey"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("yearOfBirth").getAsInt(), 1935));
    }
    
    @Test
    void readsTableIntoJson() throws FixtureException {
        // given
        JsonLoader loader = new JsonLoader();

        // when
        Iterable<JsonObject> actual = loader.loadFromTable("\n"
                + "| firstName | lastName   | age |\n"
                + "| Arthur    | Dent       |  42 |\n"
                + "| Ford      | Prefect    |  42 |\n"
                + "| Zaphod    | Beeblebrox |  42 |\n"
                );

        // then
        assertNotNull(actual);
        ArrayList<JsonObject> listOfJsonObjects = Lists.newArrayList(actual);
        assertEquals(3, listOfJsonObjects.size());
        assertAll("elements", 
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("firstName").getAsString(), "Arthur"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("lastName").getAsString(), "Dent"),
            () -> assertEquals(listOfJsonObjects.get(0).getAsJsonPrimitive("age").getAsInt(), 42),

            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("firstName").getAsString(), "Ford"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("lastName").getAsString(), "Prefect"),
            () -> assertEquals(listOfJsonObjects.get(1).getAsJsonPrimitive("age").getAsInt(), 42),

            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("firstName").getAsString(), "Zaphod"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("lastName").getAsString(), "Beeblebrox"),
            () -> assertEquals(listOfJsonObjects.get(2).getAsJsonPrimitive("age").getAsInt(), 42));
    }
}
