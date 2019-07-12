# Invoked by build.sh script where the user sets
# the BUILDROOT and SLF4JPATH for the makefile

export LD_LIBRARY_PATH=${BUILDROOT}/jss/cmake:/usr/share/java
export CLASSPATH=.:${BUILDROOT}/jss/cmake/jss4.jar:${SLF4JPATH}


JFLAGS = -g  -classpath ${CLASSPATH} -sourcepath . -d .
JC = javac
JVM= java 
FILE=

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
	ListerForAll.java ListerForJSS.java


MAIN = ListerForAll


default: classes

classes: $(CLASSES:.java=.class)

#
# List capabilities of all providers
#
run: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN)

clean:
	$(RM) *.class


