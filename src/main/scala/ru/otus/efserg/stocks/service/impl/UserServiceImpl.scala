package ru.otus.efserg.stocks.service.impl

import ru.otus.efserg.stocks.dao.model.User
import ru.otus.efserg.stocks.dao.{ID, UserDao}
import ru.otus.efserg.stocks.route.model._
import ru.otus.efserg.stocks.service.UserService
import ru.otus.efserg.stocks.{UserNotFoundException, UserValidateException}

import scala.util.{Failure, Success}

class UserServiceImpl(dao: UserDao, userDao: UserDao) extends UserService {

  override def get(request: GetUserRequest): GetUserResponse =
    dao.get(request.id) match {
      case Success(s) => GetUserResponse.Found(s)
      case Failure(ex) => ex match {
          case _: UserNotFoundException => GetUserResponse.NotFound(ex.getMessage)
          case _ => GetUserResponse.Failed(ex.getMessage)
        }
    }

  override def delete(request: DeleteUserRequest): DeleteUserResponse =
    dao.delete(request.id) match {
      case Success(s) => DeleteUserResponse.Deleted(s.id)
      case Failure(ex) => ex match {
          case _: UserNotFoundException => DeleteUserResponse.NotFound(ex.getMessage)
          case _ => DeleteUserResponse.Failed(ex.getMessage)
        }
    }

  override def update(id: ID, request: UpdateUserRequest): UpdateUserResponse =
    dao.get(id) match {
      case Success(d) => updateExist(d, request)
      case Failure(ex) => ex match {
          case _: UserNotFoundException => UpdateUserResponse.NotFound(ex.getMessage)
          case _ => UpdateUserResponse.Failed(ex.getMessage)
        }
    }

  private def updateExist(d: User, request: UpdateUserRequest) =
    dao.update(d.id,
      d.copy(firstName = request.firstName.getOrElse(d.firstName),
        lastName = request.lastName.getOrElse(d.lastName),
        email = request.email.getOrElse(d.email),
        roles = request.roles.getOrElse(d.roles)
      )
    ) match {
      case Success(user) => UpdateUserResponse.Updated(user)
      case Failure(ex) => ex match {
          case _: UserValidateException => UpdateUserResponse.NotValidUser(ex.getMessage)
          case _ => UpdateUserResponse.Failed(ex.getMessage)
        }
    }

  override def create(request: CreateUserRequest): CreateUserResponse =
    dao.create(
      User(request.firstName, request.lastName, request.email, request.roles)
    ) match {
      case Success(d) => CreateUserResponse.Created(d)
      case Failure(ex) =>
        ex match {
          case _: UserValidateException =>
            CreateUserResponse.InvalidUser(ex.getMessage)
          case _ => CreateUserResponse.Failed(ex.getMessage)
        }
    }
}
