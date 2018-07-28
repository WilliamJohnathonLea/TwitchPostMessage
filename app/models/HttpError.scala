package models

sealed trait HttpError

object ClientSideError extends HttpError
object ServerSideError extends HttpError
