Capabilities
========================================

Overview
========================================

**Capabilities** has a Java application to list the capabilities
of the "Mozilla-JSS" provider and other installed providers

Dependencies
========================================

This project has the following dependencies:

 - [JSS](https://github.com/dogtagpki/jss)
 - [OpenJDK](https://openjdk.java.net/)

To install these dependencies on Fedora, execute the following:

    sudo dnf install apache-commons-codec apache-commons-lang gcc-c++ \
                     java-devel jpackage-utils slf4j zlib-devel \
                     glassfish-jaxb-api nss-tools nss-devel cmake \
                     junit

Building
========================================
To build Capabilities:

    git clone https://github.com/emaldona/capabilities.git
    cd capabilities
    and launch build.sh [with-some-options]
    where ./build.sh -h will enumerate the options

LIMITATIONS:
========================================
This currently works on Fedora 30 and Debian 10
openSUSE builds against the built JSS aren't working,
only builds against the system jss work and some manual
adjustments are needed.

To view workarounds for openSUSE see workarounds4openSUSE.txt
in this directory.

TODO:
#################################################
- Explore using cmake, as JSS does, as it should allow us to detect environment
  variables and thus make it easier to enable supporting other distros
  but for now ./build.sh and systemBuild.sh work okay
- Add support for other Linux dstributions

