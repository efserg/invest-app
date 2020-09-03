package ru.otus.efserg.stocks.dao.impl

import ru.otus.efserg.stocks.dao.model.User
import ru.otus.efserg.stocks.dao.{ID, UserDao}
import ru.otus.efserg.stocks.{
  UserValidateException, UserAlreadyExistException, UserNotFoundException, dao}

import scala.util.{Failure, Success, Try}

class UserDaoMapImpl extends UserDao {

  private var db: Map[ID, User] = Map.empty

  private val emailRegex = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}".r
  
  override def create(user: User): Try[User] =
    validate(user, Seq(isExistAlready, isValidEmail)) match {
      case Success(v) =>
        db += (dao.createId -> v)
        Success(v)
      case f @ Failure(_) => f
    }

  override def get(id: ID): Try[User] =
    db.get(id) match {
      case Some(s) => Success(s)
      case None => Failure(UserNotFoundException(id))
    }

  override def delete(id: ID): Try[User] =
    db.get(id) match {
      case Some(s) =>
        db -= id
        Success(s)
      case None => Failure(UserNotFoundException(id))
    }

  override def update(id: ID, user: User): Try[User] =
    validate(user, Seq(isExist, isValidEmail)) match {
      case Success(_) =>
        db += (id -> user)
        Success(user)
      case f@Failure(_) => f
    }

  override def find(condition: User => Boolean): Seq[User] =
    db.values.filter(s => condition(s)).toVector

  private def isExist(user: User): Try[User] =
    get(user.id) match {
      case Failure(_) => Failure(UserNotFoundException(user.id))
      case _          => Success(user)
    }

  private def isValidEmail(user: User): Try[User] =
    if (emailRegex.matches(user.email)) Success(user)
    else Failure(UserValidateException(user.email))


  private def isExistAlready(user: User): Try[User] =
    get(user.id) match {
      case Success(_) => Success(user)
      case _          => Failure(UserAlreadyExistException(user.id))
    }

  private def validate(user: User,
                       conditions: Seq[User => Try[User]]): Try[User] =
    conditions
      .map(_(user))
      .find(_.isFailure)
      .getOrElse(Success(user))
}
