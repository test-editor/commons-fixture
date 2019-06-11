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

package org.testeditor.fixture.commons.itests.fixtures;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@SuppressWarnings("all")
public class XtextAssertionHelper {
    private static final Logger logger = LoggerFactory.getLogger(XtextAssertionHelper.class);

    public <T extends EObject> T assertNoSyntaxErrors(final T element) {
        this.assertNoSyntaxErrors(element.eResource());
        return element;
    }

    /**
     * 
     * @param resource
     * @return
     */
    public XtextResource assertNoSyntaxErrors(final Resource resource) {
        final XtextResource xtextResource = ((XtextResource) resource);
        this.assertNoSyntaxErrors(xtextResource.getParseResult());
        return xtextResource;
    }

    /**
     * 
     * @param parseResult
     * @return
     */
    public IParseResult assertNoSyntaxErrors(final IParseResult parseResult) {
        final Iterable<INode> errors = parseResult.getSyntaxErrors();
        if (!IterableExtensions.isEmpty(errors)) {
            StringConcatenation builder = new StringConcatenation();
            builder.append("Expected no syntax errors but got:");
            builder.newLine();
            {
                for (final INode error : errors) {
                    builder.append("line ");
                    int startLine = error.getStartLine();
                    builder.append(startLine);
                    builder.append(": ");
                    SyntaxErrorMessage syntaxErrorMessage = error.getSyntaxErrorMessage();
                    builder.append(syntaxErrorMessage);
                    builder.newLineIfNotEmpty();
                }
            }
            final String message = builder.toString();
            StringConcatenation builder1 = new StringConcatenation();
            builder1.append(message);
            builder1.newLineIfNotEmpty();
            builder1.newLine();
            builder1.append("Input:");
            builder1.newLine();
            String textWithLineNumbers = this.getTextWithLineNumbers(parseResult);
            builder1.append(textWithLineNumbers);
            builder1.newLineIfNotEmpty();
            XtextAssertionHelper.logger.error(builder1.toString());
            Assert.fail(message);
        }
        return parseResult;
    }

    private String getTextWithLineNumbers(final IParseResult parseResult) {
        final String text = parseResult.getRootNode().getText();
        final String[] split = text.split("\r?\n");
        int size = ((List<String>) Conversions.doWrapArray(split)).size();
        final ArrayList<String> result = new ArrayList<String>(size);
        final Procedure2<String, Integer> _function = (String line, Integer number) -> {
            StringConcatenation builder = new StringConcatenation();
            builder.append(((number).intValue() + 1));
            builder.append(" | ");
            builder.append(line);
            result.add(builder.toString());
        };
        IterableExtensions.<String>forEach(((Iterable<String>) Conversions.doWrapArray(split)), _function);
        return IterableExtensions.join(result, System.lineSeparator());
    }
}
