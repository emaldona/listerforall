#!/usr/bin/env bash

# BEGIN COPYRIGHT BLOCK
# (C) 2018 Red Hat, Inc.
# All rights reserved.
# END COPYRIGHT BLOCK

cwd=$(cd $(dirname $0); pwd -P)

# Usage info
show_help()
{
    cat "$cwd/help.txt"
}

buildroot=${HOME}/buildjss
target4make=run

# For Debian 10 use /usr/share/java/slf4j-api.jar:/usr/share/java/jdk14.jar
#
# For openSUSE Tumbleweed we have the same paths as for Fedora but to build jss
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
# Add /usr/share/java/slf4j/slf4j-jdk14.jar to the claspaths

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

isFedora=`grep fedora /usr/lib/os-release`
isDebian=`grep debian /usr/lib/os-release`
isOpenSUSE=`grep opensuse /usr/lib/os-release`


if [[ "${isOpenSUSE}" != '' ]]; then
   echo "openSUSE build"
   slf4jpath=/usr/share/java/slf4j/api.jar:/usr/share/java/slf4j/slf4j-jdk14.jar
elif [[ "${isDebian}" != '' ]]; then
   echo "Debian build"
   slf4jpath=/usr/share/java/slf4j-api.jar:/usr/share/java/jdk14.jar
else
   echo "Fedora build"
   slf4jpath=/usr/share/java/slf4j/api.jar:/usr/share/java/slf4j/jdk14.jar
fi

# Now make

BUILDROOT=${buildroot} \
SLF4JPATH=${slf4jpath} \
TARGET4MAKE=${target4make} \
make -f systemjssMakefile ${target4make}

