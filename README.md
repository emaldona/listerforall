Capabilities Lister
========================================

Overview
========================================

**listerforall** has a Java application to list the capabilities
of the "Mozilla-JSS" provider and other installed providers.

This application is very similar to the CapabilitiesList test for JSS.


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
To build the capabilities lister:

    git clone https://github.com/emaldona/listerforall.git
    cd listerforall
    and launch build.sh [with-some-options]
    where ./build.sh -h will enumerate the options

LIMITATIONS:
========================================
Using cmake:
Currently doesn't work.

Using make:
This currently works on Fedora and Debian 10. For debian you need to install cmake
from the testing repo as the one installed by default is an older version than
the one required. Other packages may be needed from testing as well.
It also works on openSUSE Tumbleweed where some manual adjustments are needed
when building against system-installed JSS. For the needed changes
see the adjustements4openSUSE.txt file in this directory.

TODO:
#################################################
- Explore using cmake, as JSS does, as it should allow us to detect environment
  variables and thus make it easier to enable supporting other distros
  but for now ./build.sh and systemBuild.sh work okay
- Add support for other Linux distributions as they are requested
