package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.ID

case class DeleteDealRequest(id: ID)

sealed trait DeleteDealResponse

object DeleteDealResponse {

  case class Deleted(id: ID) extends DeleteDealResponse

  case class NotFound(message: String) extends DeleteDealResponse

  case class Failed(message: String) extends DeleteDealResponse

}