package ru.otus.efserg.stocks.route.model

import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao.ID

case class GetDealRequest(ticker: String)

sealed trait GetDealResponse

object GetDealResponse {

  case class Found(id: ID, ticker: String, price: BigDecimal, quantity: Long, commission: BigDecimal, time: LocalDateTime) extends GetDealResponse

  case class NotFound(id: ID) extends GetDealResponse

}