package config

import javax.inject.{Inject, Singleton}
import play.api.Configuration

@Singleton
class AppConfig @Inject()(configuration: Configuration) {

  lazy val gatewayUrl: String = configuration.get[String]("api-gateway.url")
  lazy val gatewayKey: String = configuration.get[String]("api-gateway.key")

  lazy val queueUrl: String = configuration.get[String]("sqs.url")

}
