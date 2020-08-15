package ru.otus.efserg.stocks.route.model

import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao.ID

sealed trait UpdateDealRequest

object UpdateDealRequest {

  case class Update(id: ID, ticker: String, price: BigDecimal, quantity: Long, commission: BigDecimal, time: LocalDateTime)

}

sealed trait UpdateDealResponse

object UpdateDealResponse {

  case class Updated(id: ID, ticker: String, price: BigDecimal, quantity: Long, commission: BigDecimal, time: LocalDateTime) extends UpdateDealResponse

  case class NotFound(id: ID) extends UpdateDealResponse

}