ThisBuild / version      := "0.1.0"
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / organization := "com.iks"

val scalactic = "org.scalactic" %% "scalactic" % "3.2.2"
val scalaTest = "org.scalatest" %% "scalatest" % "3.2.2"
val flatSpecTest = "org.scalatest" %% "scalatest-flatspec" % "3.2.2"

lazy val pokerHand = (project in file("."))
  .settings(
    name := "PokerHand",
    libraryDependencies += scalactic,
    libraryDependencies += scalaTest % Test,
  )
