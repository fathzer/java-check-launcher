[![Maven Central](https://img.shields.io/maven-central/v/com.fathzer/java-check-launcher)](https://central.sonatype.com/artifact/com.fathzer/java-check-launcher)
[![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)](https://github.com/fathzer/java-check-launcher/blob/master/LICENSE)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fathzer_java-check-launcher&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fathzer_java-check-launcher)
[![javadoc](https://javadoc.io/badge2/com.fathzer/java-check-launcher/javadoc.svg)](https://javadoc.io/doc/com.fathzer/java-check-launcher)


# java-check-launcher
A java application launcher that checks if used JRE is compatible with a launched application.


## How to use it?

This library requires Java 1.2+.

1 - Add The library to your project.
Add the following dependency to your project.
```
<dependency>
	<groupId>com.fathzer</groupId>
	<artifactId>java-check-launcher</artifactId>
	<version>1.0.0</version>
</dependency>
```

2 - Create settings resources.
  Create a resource file in `com/fathzer/java-check-launcher/settings.properties` with the following content:  
```
min.java.version=1.8
main.class=com.me.MyApp
gui=swing
```
Replace the `min.java.version` value with the minimum java version required by your application and `main.class` value by the name of your main class.  
`gui` is optional. If your application is a Swing application, leave the line, if it is a console application, remove the line.

3 - Launch the application.
Run the command `java com.fathzer.launcher.Launcher arg1 arg2`.  
The launcher will check the java version and will display a comprehensive message if application is not compatible with installed java version (not the usual `unsupported class file major version` exception).

## How to compile it?
Recent Java JDK are not able to compile anymore java 1.2 code. *You should use a Java 8 JDK*.

Run the command `mvn clean compile`
