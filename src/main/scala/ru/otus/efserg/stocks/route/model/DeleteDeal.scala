package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.ID

case class DeleteDealRequest(ticker: String)

sealed trait DeleteDealResponse

object DeleteDealResponse {

  case class Deleted(id: ID) extends DeleteDealResponse

  case class NotFound(id: ID) extends DeleteDealResponse

}