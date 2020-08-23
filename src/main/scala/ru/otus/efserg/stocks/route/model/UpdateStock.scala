package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.model.Stock

case class UpdateStockRequest(ticker: String, stock: Stock)

sealed trait UpdateStockResponse

object UpdateStockResponse {

  case class Updated(stock: Stock) extends UpdateStockResponse

  case class NotFound(message: String) extends UpdateStockResponse

  case class NotValid(message: String) extends UpdateStockResponse

  case class Failed(message: String) extends UpdateStockResponse

}