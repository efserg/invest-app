package ru.otus.efserg.stocks.route.model

import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao.ID
import ru.otus.efserg.stocks.dao.model.Deal

case class UpdateDealRequest(id: ID, ticker: Option[String], price: Option[BigDecimal], quantity: Option[Long], commission: Option[BigDecimal], time: Option[LocalDateTime])


sealed trait UpdateDealResponse

object UpdateDealResponse {

  case class Updated(deal: Deal) extends UpdateDealResponse

  case class NotFound(reason: String) extends UpdateDealResponse

  case class ImpossibleDeal(reason: String) extends UpdateDealResponse

  case class Failed(reason: String) extends UpdateDealResponse

}