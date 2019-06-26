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


run: listJSS

listAll: Capabilities.class
	$(JVM) -classpath ${CLASSPATH} Capabilities

listJSS: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) Mozilla-JSS

listSUN: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SUN

listSunRsaSign: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunRsaSign

listSunEC: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunEC

listSunJSSE: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunJSSE

listSunJCE: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunJCE

listSunJGSS: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunJGSS

listSunASL: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunSASL

listXMLDsig: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) XMLDSig

listSunPCSC: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunPCSC

listJdkLDAP: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) JdkLDAP

listSunJdkSASL: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) JdkSASL

listSunPKCS11: $(MAIN).class
	$(JVM) -classpath ${CLASSPATH} $(MAIN) SunPKCS11


clean:
	$(RM) *.class


