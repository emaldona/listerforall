Capabilities
========================================

Overview
--------

**Capabilities** is a a set of Java applications
io list the capabilties for Mozilla-JSS and other installed providersto [NSS](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/NSS).

Dependencies
------------

This project has the following dependencies:

 - [JSS](https://github.com/dogtagpki/jss)
 - [OpenJDK](https://openjdk.java.net/)

To install these dependencies on Fedora, execute the following:

    sudo dnf install apache-commons-codec apache-commons-lang gcc-c++ \
                     java-devel jpackage-utils slf4j zlib-devel \
                     glassfish-jaxb-api nss-tools nss-devel cmake \
                     junit

Building
--------
To build Capabilities:

    git clone https://github.com/emaldona/capabilties.git
    cd capabilties
    make
    make run
      or
    make runJS
        to run the app that displays a brief output

TODO:
------------
This currently only works on Fedora
- Add support for other Linux destributions
- Explore using cmake, as JSS does, as it should allow us to detect environment
  variables and thus make it easier to enable supporting other distros

