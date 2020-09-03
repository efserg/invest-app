package ru.otus.efserg.stocks.dao.model

import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao._

case class Deal(id: ID, ticker: String, price: BigDecimal, quantity: Long, commission: BigDecimal, time: LocalDateTime)

object Deal {
  private val brokerInterest = BigDecimal(0.0005)
  private val minInterest = BigDecimal(0.01)

  def apply(ticker: String, price: BigDecimal, quantity: Long, commission: Option[BigDecimal], time: Option[LocalDateTime]): Deal = Deal(createId, ticker, price, quantity, commission.getOrElse(interest(price, quantity)), time.getOrElse(LocalDateTime.now()))

  private def interest(price: BigDecimal, quantity: Long) = {
    val interest = brokerInterest * price * quantity
    if (interest < minInterest) minInterest else interest
  }
}
