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
	Capabilities.java CapabilitiesJL.java Lister.java


MAIN = Lister


default: classes

classes: $(CLASSES:.java=.class)


run: JSS

#
# Targets to list capabities of a provider
#
JSS: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) Mozilla-JSS

SUN: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SUN

SunRsaSign: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunRsaSign

SunEC: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunEC

SunJSSE: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunJSSE

SunJCE: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunJCE

SunJGSS: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunJGSS

SunASL: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunSASL

XMLDsig: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) XMLDSig

SunPCSC: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunPCSC

JdkLDAP: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) JdkLDAP

SunJdkSASL: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) JdkSASL

SunPKCS11: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunPKCS11

#
# This one invokes Capabilities with no arguments
# so it lists capabilities for all the providers
#
listAll: Capabilities.class
	$(JVM) -classpath ${CLASSPATH} Capabilities

clean:
	$(RM) *.class


