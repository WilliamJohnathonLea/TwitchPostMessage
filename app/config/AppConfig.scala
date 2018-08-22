package config

import javax.inject.{Inject, Singleton}
import play.api.Configuration

@Singleton
class AppConfig @Inject()(configuration: Configuration) {

  lazy val clientId: String = configuration.get[String]("twitch.client-id")
  lazy val clientSecret: String = configuration.get[String]("twitch.client-secret")

  lazy val gatewayUrl: String = configuration.get[String]("api-gateway.url")
  lazy val gatewayKey: String = configuration.get[String]("api-gateway.key")

  lazy val queueUrl: String = configuration.get[String]("sqs.url")

  lazy val s3Bucket: String = configuration.get[String]("s3.bucket")

}
