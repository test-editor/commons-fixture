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

import java.util.List;

import org.testeditor.aml.dsl.AmlRuntimeModule;
import org.testeditor.aml.dsl.AmlStandaloneSetup;

import com.google.inject.Injector;

@SuppressWarnings("all")
public abstract class AbstractAmlTest extends AbstractTest {
    @Override
    protected Injector createInjector() {
        final Injector injector = super.createInjector();
        final AmlStandaloneSetup standaloneSetup = new AmlStandaloneSetup() {
            @Override
            public Injector createInjector() {
                return injector;
            }
        };
        standaloneSetup.createInjectorAndDoEMFRegistration();
        return injector;
    }

    @Override
    protected void collectModules(final List<com.google.inject.Module> modules) {
        super.collectModules(modules);
        AmlRuntimeModule amlRuntimeModule = new AmlRuntimeModule();
        modules.add(amlRuntimeModule);
    }
}
