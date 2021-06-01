name := """bbs"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.5"

libraryDependencies += guice
// libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies +=  "mysql" % "mysql-connector-java" % "5.1.41"
libraryDependencies += evolutions
libraryDependencies += jdbc
// To provide an implementation of JAXB-API, which is required by Ebean.
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.1"
libraryDependencies += "javax.activation" % "activation" % "1.1.1"
libraryDependencies += "org.glassfish.jaxb" % "jaxb-runtime" % "2.3.2"

libraryDependencies += "org.webjars" %% "webjars-play" % "2.8.0"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"
libraryDependencies += "org.webjars" % "requirejs" % "2.2.0"

libraryDependencies += javaJdbc % Test