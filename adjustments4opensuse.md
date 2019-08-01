OpenSUSE builds against the built JSS aren't working,
 only builds against the system jss work and some manual adjustemnst
 adjustments are needed.
 
 For openSUSE builds against system installed jss
 In Makefile change
 export CLASSPATH=.:${BUILDROOT}/jss/cmake/jss4.jar:${SLF4JPATH}
  to
 export CLASSPATH=.:${BUILDROOT}/jss/build/jss4.jar:${SLF4JPATH}
 
 and in systemjssMakefile change:
 
 export LD_LIBRARY_PATH=${LIBRARYPATH4FEDORA}
 export CLASSPATH=${CLASSPATH4FEDORA}
 to
 export LD_LIBRARY_PATH=${LIBRARYPATH4OPENSUSE}
 export CLASSPATH=${CLASSPATH4OPENSUSE}
 
 as stated there, strictly local changes.

