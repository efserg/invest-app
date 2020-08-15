package ru.otus.efserg.stocks.dao.model

import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao._

case class Deal(id: ID, ticker: String, price: BigDecimal, quantity: Long, commission: BigDecimal, time: LocalDateTime)

object Deal {
  val brokerInterest = BigDecimal(0.0005)

  def apply(ticker: String, price: BigDecimal, quantity: Long, commission: BigDecimal): Deal = Deal(createId, ticker, price, quantity, commission, LocalDateTime.now)

  def apply(ticker: String, price: BigDecimal, quantity: Long): Deal = Deal(ticker, price, quantity, brokerInterest * price * quantity)
}
