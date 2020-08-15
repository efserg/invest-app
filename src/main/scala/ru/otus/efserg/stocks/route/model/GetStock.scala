package ru.otus.efserg.stocks.route.model

case class GetStockRequest(ticker: String)

sealed trait GetStockResponse

object GetStockResponse {

  case class Found(ticker: String) extends GetStockResponse

  case class NotFound(ticker: String) extends GetStockResponse

}