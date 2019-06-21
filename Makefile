# Change this to where you have build JSS
export BUILDROOT=${HOME}/buildjss
export LD_LIBRARY_PATH=${BUILDROOT}/jss/build:/usr/share/java

export CLASSPATH=.:${BUILDROOT}/jss/build/jss4.jar:/usr/share/java/slf4j/api.jar

JFLAGS = -g  -classpath ${CLASSPATH} -sourcepath . -d .
JC = javac
JVM= java 
FILE=

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java


CLASSES = \
        Capabilities.java CapabilitiesJL.java 


MAIN = Capabilities


default: classes

classes: $(CLASSES:.java=.class)


run: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN)

runJL:
	$(JVM) -classpath ${CLASSPATH} CapabilitiesJL


clean:
	$(RM) *.class


