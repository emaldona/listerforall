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
slf4jpath=/usr/share/java/slf4j/api.jar

# For Debian 10 use /usr/share/java/slf4j-api.jar

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
echo "BUILDROOT=${buildroot} SLF4JPATH=${slf4jpath} make -f Makefile ${target4make}"

BUILDROOT=${buildroot} SLF4JPATH=${slf4jpath} make -f Makefile ${target4make}




