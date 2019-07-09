# Change this to where you have build JSS
export BUILDROOT=${HOME}/buildjss
export LD_LIBRARY_PATH=${BUILDROOT}/jss/cmake:/usr/share/java

export CLASSPATH=.:${BUILDROOT}/jss/cmake/jss4.jar:${BUILDROOT}/jss/cmake/tests-jss4.jar:/usr/share/java/slf4j/api.jar
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


run: JSS

#
# Targets to list capabilities of a provider
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
# List capabilities for all the providers
#
listAll: ListerForAll.class
	$(JVM) -classpath ${CLASSPATH} ListerForAll

#
# List capabilities for JSS using the ListerForJSS app
#
listForJSS: ListerForJSS.class
	$(JVM) -classpath ${CLASSPATH} ListerForJSS


clean:
	$(RM) *.class


