#!/usr/bin/env bash
#
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/.
################################################################################
#
# This script builds ListForAll with make.
#

set -e

cwd=$(cd $(dirname $0); pwd -P)

# Usage info
show_help()
{
    cat "$cwd/help.txt"
}
# defaults are build and test for fedora
buildroot=${HOME}/buildjss
target4make=run
slf4jpath=/usr/share/java/slf4j/api.jar:/usr/share/java/slf4j/jdk14.jar

# For Debian 10 use /usr/share/java/slf4j-api.jar:/usr/share/java/jdk14.jar
#
# For openSUSE Tumbleweed we have the same patch as for Fedora but to build jss
# you need the opensuse-tumbleweed-container branch from the emaldona jss fork
# For the capabilites app make sure javac and java are from the same
# jdk using sudo update-alternatives --config java|javac to prevent
# a failure like this one:
# Error: LinkageError occurred while loading main class ListerForAll
# java.lang.UnsupportedClassVersionError: ListerForAll 
# has been compiled by a more recent version of the Java Runtime 
# (class file version 56.0), this version of the Java Runtime only 
# recognizes class file versions up to 55.0
# which isn't needed when the runing the jss test suite

#
# Parse command line arguments.
#

while [[ "$1" =~ ^- && ! "$1" == "--" ]]; do case $1 in
  -h | --help )
    shift; show_help;
    ;;
  -s | --slf4jpath )
    shift; slf4jpath=$1
    ;;
  -t | --target4make)
    shift; target4make=$1
    ;;
  -b | --buildroot )
    buildroot=$1
    ;;
esac; shift; done
if [[ "$1" == '--' ]]; then shift; fi

# Now make

BUILDROOT=${buildroot} \
SLF4JPATH=${slf4jpath} \
TARGET4MAKE=${target4make} \
make -f Makefile ${target4make}

