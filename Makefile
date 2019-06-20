export BUILDROOT=$HOME/buildjss
export LD_LIBRARY_PATH=/home/emaldonado/buildjss/jss/build:/usr/share/java

export CLASSPATH=.:/home/emaldonado/buildjss/jss/build/jss4.jar:/usr/share/java/slf4j/api.jar

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



#export BUILDROOT=$HOME/buildjss
#export LD_LIBRARY_PATH=/home/emaldonado/buildjss/jss/build:/usr/share/java
#all:
#	/usr/bin/javac -classpath .:/home/emaldonado/buildjss/jss/build/jss4.jar:/usr/share/java/slf4j/api.jar:/usr/share/java/apache-commons-codec.jar:/usr/share/java/apache-commons-lang.jar:/usr/share/java/jaxb-api.jar:/usr/share/java/junit.jar -sourcepath . -d . Capabilities.java

#clean:
#	rm Capabilities.class

#ListCapabilities:
#	/usr/bin/java -classpath .:/home/emaldonado/buildjss/jss/build/jss4.jar:/home/emaldonado/buildjss/jss/build/tests-jss4.jar:/usr/share/java/slf4j/api.jar:/usr/share/java/apache-commons-codec.jar:/usr/share/java/apache-commons-lang.jar:/usr/share/java/jaxb-api.jar:/usr/share/java/slf4j/jdk14.jar:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar  Capabilities Mozilla-JSS


