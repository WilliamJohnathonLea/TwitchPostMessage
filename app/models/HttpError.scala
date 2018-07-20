package models

sealed trait HttpError

object ClientError extends HttpError
object ServerError extends HttpError
