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
### Setup development

Make sure to have a working [nix](https://nixos.org/nix/) installation. Please ensure that the `nixpkgs-unstable` channel is available. It
can be added with `nix-channel --add https://nixos.org/channels/nixpkgs-unstable`.

To enter the development environment, execute `NIXPKGS_ALLOW_UNFREE=1 nix-shell` in this repos root directory. For even more convenience,
please install [direnv](https://github.com/direnv/direnv) which will enter the development environment automatically for you.

Once within the development environment, run `./gradlew build` to resolve all necessary dependencies.

### Build

    git submodule update --init --recursive
    ./gradlew build

### Release process

In order to create a release switch to the `master` branch and execute

    ./gradlew release

and enter the new version. After the commit and tag is pushed Travis will automatically build and deploy the tagged version to Bintray.

