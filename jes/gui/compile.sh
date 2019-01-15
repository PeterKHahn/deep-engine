#!/bin/sh
echo ' Compilazione '   $1.java
echo ' wait please .......'
JNEAT_LOCATION=/home/jes
JAVA_LOCATION=/usr/java/j2sdk1.4.1
CLASSPATH=$JNEAT_LOCATION
CLASSPATH=$CLASSPATH:$JAVA_LOCATION'/jre/lib/rt.jar'
CLASSPATH=$CLASSPATH:$JAVA_LOCATION'/lib/tools.jar'
export CLASSPATH
echo '  Curr location of java :  ' $JAVA_LOCATION
echo '  Curr classpath :  ' $CLASSPATH
$JAVA_LOCATION/bin/javac  $1.java
echo '  Compilazione terminata '
