package ru.otus.efserg.stocks.dao

import ru.otus.efserg.stocks.dao.model.User

import scala.util.Try

trait UserDao {

  def create(user: User): Try[User]

  def get(id: ID): Try[User]

  def delete(id: ID): Try[User]

  def update(id: ID, deal: User): Try[User]

  def find(condition: User => Boolean): Seq[User]
}