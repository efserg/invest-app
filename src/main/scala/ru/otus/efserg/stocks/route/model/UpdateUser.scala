package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.ID
import ru.otus.efserg.stocks.dao.model.{Role, User}

case class UpdateUserRequest(id: ID, firstName: Option[String], lastName: Option[String], email: Option[String], roles: Option[Set[Role]])


sealed trait UpdateUserResponse

object UpdateUserResponse {

  case class Updated(user: User) extends UpdateUserResponse

  case class NotFound(reason: String) extends UpdateUserResponse

  case class NotValidUser(reason: String) extends UpdateUserResponse

  case class Failed(reason: String) extends UpdateUserResponse

}