Capabilities
========================================

Overview
========================================

**Capabilities** is a a set of Java applications
io list the capabilities for Mozilla-JSS and other installed providers to [NSS](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/NSS).

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

    git clone https://github.com/emaldona/capabilties.git
    cd capabilities
    and launch ./build.sh [with-some-options]
    where ./build.sh -h will enumerate the options

LIMITATIONS:
========================================
This currently works on Fedora 30 and Debian 10
OpenSUSE builds against the built JSS aren't working,
only builds against the system jss work and some manual adjustemnst
adjustments are needed.

=================================================
# For openSUSE builds against system installed jss
# Make this change to Makefile:
#----------------------------------------------
-export CLASSPATH=.:${BUILDROOT}/jss/cmake/jss4.jar:${SLF4JPATH}
+export CLASSPATH=.:${BUILDROOT}/jss/build/jss4.jar:${SLF4JPATH}

and for the systemjsMakefile do these:

-export LD_LIBRARY_PATH=${LIBRARYPATH4FEDORA}
-export CLASSPATH=${CLASSPATH4FEDORA}
+export LD_LIBRARY_PATH=${LIBRARYPATH4OPENSUSE}
+export CLASSPATH=${CLASSPATH4OPENSUSE}

per the comments given there, strictly local changes.
-----------------------------------------------------
TODO:
#################################################
- Explore using cmake, as JSS does, as it should allow us to detect environment
  variables and thus make it easier to enable supporting other distros
  but for now ./build.sh and systemBuild.sh work okay
- Add support for other Linux dstributions

