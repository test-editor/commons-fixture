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
