name := "polygon"
version := "1.0"
organization := "moon"

//scalaVersion := "2.12.6"
//libraryDependencies ++= {
//  val akkaVersion = "2.4.14"
//  Seq(
//    "com.typesafe.akka"       %% "akka-actor"                        % akkaVersion,
//    "com.typesafe.akka"       %% "akka-slf4j"                        % akkaVersion,
//    "com.typesafe.akka"       %% "akka-remote"                       % akkaVersion,
//    "com.typesafe.akka"       %% "akka-cluster"                      % akkaVersion,
//    "com.typesafe.akka"       %% "akka-multi-node-testkit"           % akkaVersion   % "test",
//    "com.typesafe.akka"       %% "akka-testkit"                      % akkaVersion   % "test",
//    "org.scalatest"           %% "scalatest"                         % "3.0.1"       % "test" exclude("org.scala-lang.modules", "scala-xml_2.11"),
//    "com.typesafe.akka"       %% "akka-slf4j"                        % akkaVersion,
//    "ch.qos.logback"          %  "logback-classic"                   % "1.1.7"
//  )
//}


libraryDependencies  ++= Seq(
  // other dependencies here
  "org.scalanlp" %% "breeze" % "0.12",
  // native libraries are not included by default. add this if you want them (as of 0.7)
  // native libraries greatly improve performance, but increase jar sizes.
  // It also packages various blas implementations, which have licenses that may or may not
  // be compatible with the Apache License. No GPL code, as best I know.
  "org.scalanlp" %% "breeze-natives" % "0.12",
  // the visualization library is distributed separately as well.
  // It depends on LGPL code.
  "org.scalanlp" %% "breeze-viz" % "0.12",

  "org.scalafx" %% "scalafx" % "8.0.144-R12"
)

resolvers ++= Seq(
  // other resolvers here
  // if you want to use snapshot builds (currently 0.12-SNAPSHOT), use this.
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

// or 2.11.8
scalaVersion := "2.11.8"