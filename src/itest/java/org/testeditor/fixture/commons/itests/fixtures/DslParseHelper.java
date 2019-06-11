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

import java.io.StringReader;
import java.util.UUID;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.testeditor.aml.AmlModel;
import org.testeditor.aml.ModelElement;
import org.testeditor.aml.dsl.AmlStandaloneSetup;
import org.testeditor.tcl.MacroCollection;
import org.testeditor.tcl.TclModel;
import org.testeditor.tcl.TestCase;
import org.testeditor.tcl.TestConfiguration;
import org.testeditor.tcl.dsl.TclStandaloneSetup;
import org.testeditor.tsl.TslModel;
import org.testeditor.tsl.dsl.TslStandaloneSetup;

import com.google.common.collect.Iterators;
import com.google.inject.Injector;
import com.google.inject.Provider;

@SuppressWarnings("all")
public class DslParseHelper {
    public Provider<XtextResourceSet> resourceSetProvider;

    public XtextResourceSet resourceSet;

    public ISerializer tclSerializer;

    @Inject
    public InMemoryFileSystemAccess fsa;

    @Inject
    @Extension
    private XtextAssertionHelper xtextAssertionHelper;

    @Inject
    protected IParser iparser;

    protected ParseHelper<AmlModel> amlParseHelper;

    protected ParseHelper<TclModel> tclParseHelper;

    protected ParseHelper<TslModel> tslParseHelper;

    /**
     * 
     * @param resourceSetProvider
     */
    @Inject
    public DslParseHelper(final Provider<XtextResourceSet> resourceSetProvider) {
        this.resourceSetProvider = resourceSetProvider;
        this.resourceSet = resourceSetProvider.get();
        this.resourceSet.setClasspathURIContext(this);
        final Injector amlInjector = new AmlStandaloneSetup().createInjectorAndDoEMFRegistration();
        this.amlParseHelper = amlInjector.<ParseHelper>getInstance(ParseHelper.class);
        final Injector tclInjector = new TclStandaloneSetup().createInjectorAndDoEMFRegistration();
        this.tclParseHelper = tclInjector.<ParseHelper>getInstance(ParseHelper.class);
        final Injector tslInjector = new TslStandaloneSetup().createInjectorAndDoEMFRegistration();
        this.tslParseHelper = tslInjector.<ParseHelper>getInstance(ParseHelper.class);
        this.tclSerializer = tclInjector.<ISerializer>getInstance(ISerializer.class);
    }

    /**
     * 
     * @param input
     * @return
     */
    public AmlModel parseAml(final CharSequence input) {
        try {
            return this.amlParseHelper.parse(input, this.resourceSet);
        } catch (Throwable e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    /**
     * 
     * @param input
     * @param alternateResourceSet
     * @return
     */
    public AmlModel parseAml(final CharSequence input, final ResourceSet alternateResourceSet) {
        try {
            return this.amlParseHelper.parse(input, alternateResourceSet);
        } catch (Throwable e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    /**
     * Creates a sample model by adding a package definition to the passed input,
     * i.e.
     * 
     * <pre>
     *     package com.example
     *     «input»
     * </pre>
     * 
     * and returns the first element of the passed type within the model's
     * eAllContents.
     * 
     * @param input the partial model to parse
     * @param elementClass the expected type
     * @return {@code model.eAllContents.filter(elementClass).head}
     */
    public <T extends ModelElement> T parseAmlWithStdPackage(final CharSequence input, final Class<T> elementClass) {
        return this.<T>parseAmlWithPackage(input, "com.example", elementClass);
    }

    /**
     * 
     * @param input
     * @param elementClass
     * @return
     */
    public <T extends ModelElement> T parseAmlWithUniquePackage(final CharSequence input, final Class<T> elementClass) {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        StringConcatenation builder = new StringConcatenation();
        builder.append("com.example");
        builder.append(uuid);
        return this.<T>parseAmlWithPackage(input, builder.toString(), elementClass);
    }

    /**
     * 
     * @param input
     * @param thePackage
     * @param elementClass
     * @return
     */
    public <T extends ModelElement> T parseAmlWithPackage(final CharSequence input, final String thePackage,
            final Class<T> elementClass) {
        try {
            StringConcatenation builder = new StringConcatenation();
            builder.append("package ");
            builder.append(thePackage);
            builder.newLineIfNotEmpty();
            builder.newLine();
            builder.append(input);
            builder.newLineIfNotEmpty();
            final String newInput = builder.toString();
            final AmlModel model = this.amlParseHelper.parse(newInput, this.resourceSet);
            return IteratorExtensions.<T>head(Iterators.<T>filter(model.eAllContents(), elementClass));
        } catch (Throwable e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    /**
     * 
     * @param model
     * @return
     */
    public <T extends EObject> T addToResourceSet(final T model) {
        boolean matched = false;
        if (model instanceof TclModel) {
            matched = true;
            if (((((TclModel) model).getMacroCollection() != null)
                    && (((TclModel) model).getMacroCollection().getMacros().size() > 0))) {
                String tclModelName = this.<EObject>tclModelName(((TclModel) model));
                String plus = (tclModelName + ".tml");
                return ((T) this.<T>addToResourceSet(((T) model), plus));
            } else {
                TestCase test = ((TclModel) model).getTest();
                boolean tripleNotEquals = (test != null);
                if (tripleNotEquals) {
                    String tclModelName = this.<EObject>tclModelName(((TclModel) model));
                    String plus = (tclModelName + ".tcl");
                    return ((T) this.<T>addToResourceSet(((T) model), plus));
                } else {
                    TestConfiguration config = ((TclModel) model).getConfig();
                    boolean tripleNotEquals1 = (config != null);
                    if (tripleNotEquals1) {
                        String tclModelName2 = this.<EObject>tclModelName(((TclModel) model));
                        String plus = (tclModelName2 + ".tfr");
                        return ((T) this.<T>addToResourceSet(((T) model), plus));
                    } else {
                        StringConcatenation builder = new StringConcatenation();
                        builder.append("tcl model is neither a macroCollection nor a test nor a config");
                        throw new RuntimeException(builder.toString());
                    }
                }
            }
        }
        if (!matched) {
            if (model instanceof AmlModel) {
                matched = true;
                return ((T) this.<T>addToResourceSet(((T) model), "Dummy.aml"));
            }
        }
        if (!matched) {
            if (model instanceof TslModel) {
                matched = true;
                return ((T) this.<T>addToResourceSet(((T) model), "Dummy.tsl"));
            }
        }
        StringConcatenation builder = new StringConcatenation();
        builder.append("unknown model=\'");
        String name = model.getClass().getName();
        builder.append(name);
        builder.append("\'.");
        throw new RuntimeException(builder.toString());
    }

    /**
     * 
     * @param model
     * @param fileName
     * @return
     */
    public <T extends EObject> T addToResourceSet(final T model, final String fileName) {
        final URI uri = URI.createURI(fileName);
        final Resource newResource = this.resourceSet.createResource(uri);
        newResource.getContents().add(model);
        return model;
    }

    /**
     * 
     * @param tcl
     * @return
     */
    public TclModel parseTcl(final String tcl) {
        try {
            return this.tclParseHelper.parse(tcl, this.resourceSet);
        } catch (Throwable e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    /**
     * 
     * @param tcl
     * @param fileName
     * @return
     */
    public TclModel parseTcl(final String tcl, final String fileName) {
        try {
            return this.tclParseHelper.parse(tcl, URI.createURI(fileName), this.resourceSet);
        } catch (Throwable e) {
            throw Exceptions.sneakyThrow(e);
        }
    }
    

    /**
     * 
     * @param tcl
     * @param fileName
     * @param alternateResourceSet
     * @return
     */
    public TclModel parseTcl(final String tcl, final String fileName, final ResourceSet alternateResourceSet) {
        try {
            return this.tclParseHelper.parse(tcl, URI.createURI(fileName), alternateResourceSet);
        } catch (Throwable e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    /**
     * 
     * @param tsl
     * @return
     */
    public TslModel parseTsl(final String tsl) {
        try {
            return this.tslParseHelper.parse(tsl, this.resourceSet);
        } catch (Throwable e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    /**
     * 
     * @param tsl
     * @param fileName
     * @return
     */
    public TslModel parseTsl(final String tsl, final String fileName) {
        try {
            return this.tslParseHelper.parse(tsl, URI.createURI(fileName), this.resourceSet);
        } catch (Throwable e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    /**
     * 
     * @param model
     * @return
     */
    public Object getJavaFile(final TclModel model) {
        final String package_ = model.getPackage();
        final String name = this.<EObject>tclModelName(model);
        StringConcatenation builder = new StringConcatenation();
        builder.append(IFileSystemAccess.DEFAULT_OUTPUT);
        String replaceAll = package_.replaceAll("\\.", "/");
        builder.append(replaceAll);
        builder.append("/");
        builder.append(name);
        builder.append(".java");
        final String key = builder.toString();
        return this.fsa.getAllFiles().get(key);
    }

    /**
     * 
     * @param input
     * @param rule
     * @param ruleClass
     * @return
     */
    public <T extends Object> T parse(final CharSequence input, final ParserRule rule, final Class<T> ruleClass) {
        String string = input.toString();
        StringReader stringReader = new StringReader(string);
        return this.<T>getParsedRule(
                this.xtextAssertionHelper.assertNoSyntaxErrors(this.iparser.parse(rule, stringReader)), ruleClass);
    }

    /**
     * 
     * @param rule
     * @param input
     * @return
     */
    public IParseResult partialParse(final ParserRule rule, final CharSequence input) {
        String string = input.toString();
        StringReader stringReader = new StringReader(string);
        return this.iparser.parse(rule, stringReader);
    }

    public <T extends Object> T getParsedRule(final IParseResult parseResult, final Class<T> rule) {
        return rule.cast(parseResult.getRootASTElement());
    }

    private <T extends EObject> String tclModelName(final TclModel model) {
        String elvis = null;
        String elvis1 = null;
        MacroCollection macroCollection = model.getMacroCollection();
        String name = null;
        if (macroCollection != null) {
            name = macroCollection.getName();
        }
        if (name != null) {
            elvis1 = name;
        } else {
            TestCase test = model.getTest();
            String name1 = null;
            if (test != null) {
                name1 = test.getName();
            }
            elvis1 = name1;
        }
        if (elvis1 != null) {
            elvis = elvis1;
        } else {
            TestConfiguration config = model.getConfig();
            String name2 = null;
            if (config != null) {
                name2 = config.getName();
            }
            elvis = name2;
        }
        return elvis;
    }
}
