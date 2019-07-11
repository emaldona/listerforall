# Change this to where you have build JSS
export BUILDROOT=${HOME}/buildjss
export LD_LIBRARY_PATH=${BUILDROOT}/jss/cmake:/usr/share/java

export CLASSPATH=.:${BUILDROOT}/jss/cmake/jss4.jar:/usr/share/java/slf4j/api.jar
# For Debian 10 change /usr/share/java/slf4j/api.jar to /usr/share/java/slf4j-api.jar unless you have aliases

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


