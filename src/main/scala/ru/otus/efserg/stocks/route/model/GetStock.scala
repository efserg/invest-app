package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.model.Stock

case class GetStockRequest(ticker: String)

sealed trait GetStockResponse

object GetStockResponse {

  case class Found(stock: Stock) extends GetStockResponse

  case class NotFound(message: String) extends GetStockResponse

  case class Failed(message: String) extends GetStockResponse

}