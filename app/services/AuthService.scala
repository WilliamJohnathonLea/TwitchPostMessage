package services

import java.net.URL
import java.security.interfaces.RSAPublicKey

import cats.implicits._
import cats.data.EitherT
import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import connectors.TwitchConnector
import javax.inject.{Inject, Singleton}
import models.{HttpError, ServerSideError, TwitchTokenResponse}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class AuthService @Inject()(twitchConnector: TwitchConnector) {

  private val jwkProvider = new UrlJwkProvider(new URL("https://id.twitch.tv/oauth2/keys"))

  def requestTokens(code: String)(implicit ec: ExecutionContext): EitherT[Future, HttpError, TwitchTokenResponse] = {
    for {
      tokens <- EitherT(twitchConnector.requestTokens(code))
      _ <- EitherT(verifyIdToken(tokens.id_token))
    } yield tokens
  }

  private def verifyIdToken(idToken: String): Future[Either[HttpError, DecodedJWT]] = {
    val tryVerify = Try(JWT.decode(idToken)) map { idt =>
      val pubKey = jwkProvider.get(idt.getKeyId).getPublicKey.asInstanceOf[RSAPublicKey]
      val verifier = JWT.require(Algorithm.RSA256(pubKey, null))
      Right(verifier.build().verify(idt.getToken))
    } recover { case _ => Left(ServerSideError) }

    Future.fromTry(tryVerify)
  }

}
