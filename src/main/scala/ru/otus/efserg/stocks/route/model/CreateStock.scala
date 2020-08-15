package ru.otus.efserg.stocks.route.model

case class CreateStockRequest(ticker: String)

sealed trait CreateStockResponse

object CreateStockResponse {

  case class Created(ticker: String) extends CreateStockResponse

  case class AlreadyExist(ticker: String) extends CreateStockResponse

}