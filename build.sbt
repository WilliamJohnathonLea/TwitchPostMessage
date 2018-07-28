name := "TwitchPostMessage"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  ws,
  guice,
  "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.372",
  "com.lightbend.akka" %% "akka-stream-alpakka-s3" % "0.20"
)

lazy val dockerRepo = System.getenv("DOCKER_REPO")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, DockerPlugin, JavaServerAppPackaging)
  .settings(
    dockerBaseImage := "openjdk:8-jre-slim",
    dockerUpdateLatest := true,
    dockerRepository := Some(dockerRepo),
    dockerExposedPorts := Seq(9000)
  )