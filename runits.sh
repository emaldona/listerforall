#!/usr/bin/env bash

# Similar to the build.sh script but instead of building
# our own app it just executes the CapabilitiesList test
# that was already built as part the jss build
# This version runs the listing against the system-installed
# jss instead of the one just built. Usefull to compare and
# see what has improved since last release

cwd=$(cd $(dirname $0); pwd -P)

# Usage info
show_help()
{
    cat "$cwd/help4runs.txt"
    exit
}

# defaults
buildroot=${HOME}/buildjss
target4make=run
testpath=${buildroot}/jss/build/tests-jss.jar
# To be replaced with system-installed jss
jsspath=${buildroot}/jss/build/jss.jar

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
  -j | --systemjsspath)
    shift; prefix=$1
    ;;
  -b | --buildroot )
    buildroot=$1
    ;;
esac; shift; done
if [[ "$1" == '--' ]]; then shift; fi

if [[ ! -f /etc/os-release ]] ; then
    echo 'File "/etc/os-release" is not there, aborting.'
    exit
fi

isFedora=`grep fedora /etc/os-release`
isDebian=`grep debian /etc/os-release`
isOpenSUSE=`grep opensuse /etc/os-release`


if [[ "${isOpenSUSE}" != '' ]]; then
    echo "openSUSE build"
    slf4jpath=/usr/share/java/slf4j/api.jar:/usr/share/java/slf4j/slf4j-jdk14.jar
elif [[ "${isDebian}" != '' ]]; then
    echo "Debian build"
    slf4jpath=/usr/share/java/slf4j-api.jar:/usr/share/java/jdk14.jar
elif [[ "${isFedora}" != '' ]]; then
    echo "Fedora build"
    slf4jpath=/usr/share/java/slf4j/api.jar:/usr/share/java/slf4j/jdk14.jar
else
    echo "Unsupported distribution"
    exit
fi

# Now run the CapabilitiesList app

java -classpath ${testpath}:${jsspath}:${slf4jpath} org.mozilla.jss.tests.CapabilitiesList

