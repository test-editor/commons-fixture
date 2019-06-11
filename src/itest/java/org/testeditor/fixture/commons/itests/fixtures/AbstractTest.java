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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.impl.BinaryGrammarResourceFactoryImpl;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

/**
 * Abstract dependency-injection aware test class for Xtext tests. We don't want
 * to use the XtextRunner as this limits us to not use e.g. parameterized tests.
 */
@SuppressWarnings("all")
public abstract class AbstractTest {
    @Inject
    private Injector injector;

    /**
     * Subclass-aware logger.
     */
    @Extension
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 
     */
    @BeforeAll
    public static void registerXtextBin() {
        Map<String, Object> extensionToFactoryMap = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
        BinaryGrammarResourceFactoryImpl binaryGrammarResourceFactoryImpl = new BinaryGrammarResourceFactoryImpl();
        extensionToFactoryMap.putIfAbsent("xtextbin", binaryGrammarResourceFactoryImpl);
    }

    /**
     * 
     */
    @BeforeEach
    public void performInjection() {
        MockitoAnnotations.initMocks(this);
        if ((this.injector == null)) {
            this.injector = this.createInjector();
            this.injector.injectMembers(this);
        }
    }

    protected Injector createInjector() {
        final LinkedList<com.google.inject.Module> modules = 
                CollectionLiterals.<com.google.inject.Module>newLinkedList();
        this.collectModules(modules);
        return Guice.createInjector(AbstractTest.mixin(
                ((com.google.inject.Module[]) Conversions.unwrapArray(modules, com.google.inject.Module.class))));
    }

    protected void collectModules(final List<com.google.inject.Module> modules) {
    }

    /**
     * Copied from org.eclipse.xtext.util.Modules2
     */
    protected static com.google.inject.Module mixin(final com.google.inject.Module... modules) {
        if (modules.length == 0) {
            return Modules.EMPTY_MODULE;
        }
        com.google.inject.Module current = IterableExtensions.<com.google.inject.Module>head(
                ((Iterable<com.google.inject.Module>) Conversions.doWrapArray(modules)));
        for (int i = 1; (i < modules.length); i++) {
            current = Modules.override(current).with(modules[i]);
        }
        return current;
    }
}
