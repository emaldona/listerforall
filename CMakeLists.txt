# This is the CMakeLists.txt for the capabilities project.
project(capabilities)

# Required cmake version; set a 3.0.2 since that's the version of the
# documentation I referenced.
cmake_minimum_required(VERSION 3.0.2)

# Source our custom CMake modules; this includes NSS and NSPR modules from
# PKI and the JSSConfig and JSSCommon modules.
set(CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake)

# Define optional variables and conditionals.
#if (DEFINED ENV{CHECK_DEPRECATION})
#    list(APPEND JSS_JAVAC_FLAGS "-Xlint:deprecation")
#endif()

# Build a debug build by default when no type is specified on the command line
if(NOT (DEFINED CMAKE_BUILD_TYPE))
    set(CMAKE_BUILD_TYPE "Debug")
endif()

# Find NSPR and NSS Libraries.
find_package(NSPR REQUIRED)
find_package(NSS REQUIRED)
find_package(JSS REQUIRED)

# Find Java and JNI packages; this sets CMake wariables like
# Java_JAVAC_EXECUTABLE.
find_package(Java REQUIRED)
find_package(JNI REQUIRED)

# Since we found Java, include UseJava to provide the find_jar function.
include(UseJava)

# Load capabilitiesConfig module; this defines the capabilities_config() macro which defines
# CAPABILITIES-specific configuration values.
include(capabilitiesConfig)

# Load capabilitiesCommon module; this defines the capabilities_build() macros which defines
# the JSS build procedure.
include(capabilitiesCommon)

# Load capabilitiesTests module; this defines the capabilities_tests() macro which defines the
# capabilities test procedure.
#include(capabilitiesTests)

capabilities_config()
capabilities_build()
#capabilities_tests()
