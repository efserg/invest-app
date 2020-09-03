package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.ID

case class DeleteUserRequest(id: ID)

sealed trait DeleteUserResponse

object DeleteUserResponse {

  case class Deleted(id: ID) extends DeleteUserResponse

  case class NotFound(message: String) extends DeleteUserResponse

  case class Failed(message: String) extends DeleteUserResponse

}