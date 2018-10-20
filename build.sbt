import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.5",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++=
      Seq(
        scalaTest % Test,
        "org.mockito" % "mockito-core" % "2.23.0" % Test,
        "com.google.inject" % "guice" % "4.2.1"
      )
  )
