name := "TwitchPostMessage"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  ws,
  guice,
  "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.372",
  "com.lightbend.akka" %% "akka-stream-alpakka-s3" % "0.20",
  "com.auth0" % "java-jwt" % "3.4.0",
  "com.auth0" % "jwks-rsa" % "0.6.0",
  "org.typelevel" %% "cats-core" % "1.3.1"
)

lazy val dockerRepo = System.getenv("DOCKER_REPO")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, DockerPlugin, JavaServerAppPackaging)
  .settings(
    dockerBaseImage := "openjdk:8-jre-slim",
    dockerUpdateLatest := true,
    dockerRepository := Some(dockerRepo),
    dockerExposedPorts := Seq(9000),
    scalacOptions += "-Ypartial-unification"
  )