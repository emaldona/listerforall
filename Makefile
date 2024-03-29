# Invoked by build.sh script where the user can set
# the BUILDROOT and SLF4JPATH for the makefile and the target

export LD_LIBRARY_PATH=${BUILDROOT}/jss/build:/usr/share/java
export CLASSPATH=.:${BUILDROOT}/jss/build/jss.jar:${SLF4JPATH}


JFLAGS = -g  -classpath ${CLASSPATH} -sourcepath . -d .
JC = javac
JVM= java
FILE=

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
	ListerForAll.java


MAIN = ListerForAll


default: classes

classes: $(CLASSES:.java=.class)

#
# List capabilities of all providers
#
run: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN)

#
# For the next ones you must invoke make directly
# as from the build shell script they won't work
#
clean:
	$(RM) *.class

remove-nssdb:
	$(RM) -r ${BUILDROOT}/jss/build/results/nssdb

remove-listings:
	$(RM) -r listings

