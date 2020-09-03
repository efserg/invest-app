package ru.otus.efserg.stocks.route.model

import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao.model.Deal

case class CreateDealRequest(ticker: String, price: BigDecimal, quantity: Long, commission: Option[BigDecimal], time: Option[LocalDateTime])


sealed trait CreateDealResponse

object CreateDealResponse {

  case class Created(deal: Deal) extends CreateDealResponse

  case class ImpossibleDeal(reason: String) extends CreateDealResponse

  case class Failed(reason: String) extends CreateDealResponse

}
