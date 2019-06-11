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

package org.testeditor.fixture.commons.itests;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.inject.Inject;

import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.jupiter.api.Test;
import org.testeditor.aml.AmlModel;
import org.testeditor.fixture.commons.itests.fixtures.AbstractAmlTest;
import org.testeditor.fixture.commons.itests.fixtures.DslParseHelper;
import org.testeditor.fixture.commons.itests.fixtures.XtextAssertionHelper;

import com.google.common.io.CharStreams;

class JsonLoaderITest extends AbstractAmlTest {

    @Inject
    protected ValidationTestHelper validationTestHelper;
    @Inject
    protected DslParseHelper dslParseHelper;
    @Inject
    protected XtextAssertionHelper xtextAssertionHelper;

    @Test
    void canParseJsonLoaderAml() throws IOException {
        // given
        InputStream jsonLoader = this.getClass()
                .getResourceAsStream("/org/testeditor/fixture/commons/io/json/JsonLoader.aml");
        try (final Reader reader = new InputStreamReader(jsonLoader)) {
            String amlContent = CharStreams.toString(reader);

            // when
            AmlModel model = dslParseHelper.parseAml(amlContent);

            // then
            xtextAssertionHelper.assertNoSyntaxErrors(model);
        }
    }
    
    @Test
    void jsonLoaderAmlisFreeOfValidationErrors() throws IOException {
        // given
        InputStream jsonLoader = this.getClass()
                .getResourceAsStream("/org/testeditor/fixture/commons/io/json/JsonLoader.aml");
        try (final Reader reader = new InputStreamReader(jsonLoader)) {
            String amlContent = CharStreams.toString(reader);

            // when
            AmlModel model = dslParseHelper.parseAml(amlContent);

            // then
            validationTestHelper.assertNoIssues(model);
        }
    }

}
