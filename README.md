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

    sudo dnf build-dep jss

while for debian:

    sudo apt build-dep jss

Building
========================================
To build ListerForAll:

    git clone https://github.com/emaldona/listerforall.git
    cd listerforall
    Launch build.sh [with-some-options]
    where ./build.sh -h will enumerate the options

	Usage: build.sh [-h] [-b] [-s] [-t]

	This script builds ListerForAll application with make.

	ListerForAll build tool options:

	-h               display this help and exit
	-b               path to where jss was built
	-s               path to slf4j jar file, either system's or latest built
	-t               target for makefile [ empty for build | run ]

LIMITATIONS:
========================================
Using cmake:
Currently doesn't work.

Using make:
This currently works on Fedora and Debian. It also works on openSUSE Tumbleweed
where some manual adjustments are needed when building against system-installed
JSS. For the needed changes see the adjustements4openSUSE.txt file in this
directory.
