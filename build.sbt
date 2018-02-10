name := "ScalaNetty"

version := "0.1"

scalaVersion := "2.12.4"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)


libraryDependencies ++= Seq(
  "io.netty" % "netty-all" % "4.0.9.Final",
  "com.google.protobuf" % "protobuf-java" % "3.5.1",
  "com.trueaccord.scalapb" %% "scalapb-runtime" % com.trueaccord.scalapb.compiler.Version.scalapbVersion % "protobuf",
)


