package ru.otus.efserg.stocks.route.model
import ru.otus.efserg.stocks.dao.ID
import ru.otus.efserg.stocks.dao.model.User

case class GetUserRequest(id: ID)

sealed trait GetUserResponse

object GetUserResponse {

  case class Found(user: User) extends GetUserResponse

  case class NotFound(message: String) extends GetUserResponse

  case class Failed(message: String) extends GetUserResponse

}