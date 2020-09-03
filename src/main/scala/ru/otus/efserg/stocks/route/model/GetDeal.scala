package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.ID
import ru.otus.efserg.stocks.dao.model.Deal

case class GetDealRequest(id: ID)

sealed trait GetDealResponse

object GetDealResponse {

  case class Found(deal: Deal) extends GetDealResponse

  case class NotFound(message: String) extends GetDealResponse

  case class Failed(message: String) extends GetDealResponse

}