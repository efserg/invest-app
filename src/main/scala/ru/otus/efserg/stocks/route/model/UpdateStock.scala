package ru.otus.efserg.stocks.route.model

case class UpdateStockRequest(ticker: String, newTicker: String)

sealed trait UpdateStockResponse

object UpdateStockResponse {

  case class Updated(ticker: String) extends UpdateStockResponse

  case class NotFound(ticker: String) extends UpdateStockResponse

}