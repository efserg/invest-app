package ru.otus.efserg.stocks.service

import ru.otus.efserg.stocks.dao.ID
import ru.otus.efserg.stocks.route.model._

trait UserService {
  def get(request: GetUserRequest): GetUserResponse

  def delete(request: DeleteUserRequest): DeleteUserResponse

  def update(id: ID, request: UpdateUserRequest): UpdateUserResponse

  def create(request: CreateUserRequest): CreateUserResponse

}
