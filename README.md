Test-Editor Commons-Fixture
=============================

[![License](http://img.shields.io/badge/license-EPL-blue.svg?style=flat)](https://www.eclipse.org/legal/epl-v20.html)
[![Build Status](https://travis-ci.org/test-editor/commons-fixture.svg?branch=master)](https://travis-ci.org/test-editor/commons-fixture)
[![Download](https://api.bintray.com/packages/test-editor/Fixtures/commons-fixture/images/download.svg)](https://bintray.com/test-editor/Fixtures/commons-fixture/_latestVersion)


The **Test-Editor Commons-Fixture** package contains convenience methods that extend and augment the Test-Editor Fixture projects.

## Content
The convenience methods consists of following 
  + *generate unique IDs*
  + *generate dates (current date without any argument, or current date +/- an offset provided as argument)*
  + *first and last names generation*

## Development

### Build

    git submodule update --init --recursive
    ./gradlew build

### Release process

In order to create a release switch to the `master` branch and execute

    ./gradlew release

and enter the new version. After the commit and tag is pushed Travis will automatically build and deploy the tagged version to Bintray.

