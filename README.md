CUI Java Web App
================

Basic webapp that wraps the CUI download.  The maven project has the servlet dependency and maven plugins for tomcat and jetty.

"Sitraka" is a fake company which has licensed an email migration tool made by Dell. This application is strictly for illustrating the lack of dependency of CUI on a specific back-end technology, and should be used only as a reference.


Requirements
============

[Installing Java 7 on Yosemite beta](https://www.youtube.com/watch?v=Av_K3tV_m5E)

The `JAVA_HOME` environment variable must be set.


Links: 

[Java 1.7 JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

[Maven 3.2+](http://maven.apache.org/)


Install/Running
===============

Execute the following commands from a command prompt in this directory.  Maven is driven by the pom.xml file.

```
mvn clean install
mvn jetty:run
```

Substitute `mvn tomcat7:run` for `mvn jetty:run` to run under tomcat.

Browse to:

http://localhost:8080/
