package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.ID
import ru.otus.efserg.stocks.dao.model.Deal

case class GetDealRequest(ticker: String)

sealed trait GetDealResponse

object GetDealResponse {

  case class Found(deal: Deal) extends GetDealResponse

  case class NotFound(id: ID) extends GetDealResponse

}