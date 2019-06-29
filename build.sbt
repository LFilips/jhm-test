name := "scala-performance"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.8.1"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.8",
)


// build.sbt
enablePlugins(JmhPlugin)
