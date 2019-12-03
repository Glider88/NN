import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.cell.nn"
ThisBuild / organizationName := "cell"

lazy val root = (project in file("."))
  .settings(
    name := "scala-cell-nn",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      scalaBreeze,
      scalaBreezeNative,
      scalaBreezeViz
    )
  )
