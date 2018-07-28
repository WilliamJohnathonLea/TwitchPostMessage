package connectors

import com.amazonaws.ClientConfiguration
import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.Message
import config.AppConfig
import javax.inject.{Inject, Singleton}

import scala.concurrent.{ExecutionContext, Future}
import scala.collection.JavaConverters.iterableAsScalaIterable

@Singleton
class SqsConnector @Inject()(appConfig: AppConfig) {

  private lazy val sqsClient = AmazonSQSClientBuilder
    .standard()
    .withRegion(Regions.EU_WEST_2)
    .withClientConfiguration(new ClientConfiguration())
    .build()

  def getMessage(implicit ec: ExecutionContext): Future[Option[Message]] = Future {
    val result = sqsClient.receiveMessage(appConfig.queueUrl).getMessages
    iterableAsScalaIterable(result).headOption
  }

  def removeMessage(handle: String)(implicit ec: ExecutionContext) = Future {
    sqsClient.deleteMessage(appConfig.queueUrl, handle)
  }

}
