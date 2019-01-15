#!/bin/sh
echo '  Start compile all members in this directory : '
      pwd
echo ' wait please .......'
JNEAT_LOCATION=/home/peter/projects/deep-engine/jes
JAVA_LOCATION=/usr/java/jre1.8.0_171
CLASSPATH=$JNEAT_LOCATION
CLASSPATH=$CLASSPATH:$JAVA_LOCATION'/jre/lib/rt.jar'
CLASSPATH=$CLASSPATH:$JAVA_LOCATION'/lib/tools.jar'
export CLASSPATH
echo '  Curr location of java :  ' $JAVA_LOCATION
echo '  Curr classpath :  ' $CLASSPATH
echo '  Compile Generation....'
/usr/bin/javac  Generation.java
echo '  Compile Grafi....'
/usr/bin/javac  Grafi.java
echo '  Compile MainGui....'
/usr/bin/javac  MainGui.java
echo '  Compile Parameter....'
/usr/bin/javac  Parameter.java
echo '  Compile Session....'
/usr/bin/javac  Session.java
echo '  Compile Execution....'
/usr/bin/javac  Execution.java
echo '  Compilazione terminata '
