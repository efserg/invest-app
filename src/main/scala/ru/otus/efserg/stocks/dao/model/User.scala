package ru.otus.efserg.stocks.dao.model
import ru.otus.efserg.stocks.dao
import ru.otus.efserg.stocks.dao.ID

case class User(id: ID, firstName: String, lastName: String, email: String, roles: Set[Role])

object User {
  def apply(firstName: String,
            lastName: String,
            email: String,
            roles: Set[Role]): User =
    User(dao.createId, firstName, lastName, email, roles)
}
