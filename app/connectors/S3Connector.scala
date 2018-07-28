package connectors

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.alpakka.s3.scaladsl.S3Client
import akka.stream.scaladsl.Source
import akka.util.ByteString
import config.AppConfig
import javax.inject.{Inject, Singleton}

@Singleton
class S3Connector @Inject()(appConfig: AppConfig)(implicit sys: ActorSystem, mat: Materializer) {

  private lazy val s3Client = S3Client()

  def downloadFile(name: String): Source[ByteString, NotUsed] = {
    s3Client.download(appConfig.s3Bucket, name)._1
  }

}
