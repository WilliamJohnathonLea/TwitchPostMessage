name := "TwitchPostMessage"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  ws,
  guice
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, DockerPlugin, JavaServerAppPackaging)
  .settings(
    dockerBaseImage := "openjdk:8-jre-slim",
    dockerUpdateLatest := true,
    dockerExposedPorts := Seq(9000)
  )