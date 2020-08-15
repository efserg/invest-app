package ru.otus.efserg.stocks.route.model

import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao.ID

sealed trait CreateDealRequest

object CreateDealRequest {

  case class Buy(ticker: String, price: BigDecimal, quantity: Long, commission: Option[BigDecimal], time: Option[LocalDateTime]) extends CreateDealRequest

  case class Sell(ticker: String, price: BigDecimal, quantity: Long, commission: Option[BigDecimal], time: Option[LocalDateTime]) extends CreateDealRequest

}

sealed trait CreateDealResponse

object CreateDealResponse {

  case class Created(id: ID) extends CreateDealResponse

  case class ImpossibleDeal(reason: String) extends CreateDealResponse

}