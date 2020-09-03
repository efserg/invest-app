package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.model.{Role, User}

case class CreateUserRequest(firstName: String, lastName: String, email: String, roles: Set[Role])

sealed trait CreateUserResponse

object CreateUserResponse {

  case class Created(user: User) extends CreateUserResponse

  case class UserNotFound(reason: String) extends CreateUserResponse

  case class InvalidUser(reason: String) extends CreateUserResponse

  case class Failed(reason: String) extends CreateUserResponse

}
