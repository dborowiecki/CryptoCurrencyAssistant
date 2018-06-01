name := """CryptoCurrency"""
organization := "dmn"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.0"
libraryDependencies += "org.webjars" % "bootstrap" % "4.0.0"
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.1"