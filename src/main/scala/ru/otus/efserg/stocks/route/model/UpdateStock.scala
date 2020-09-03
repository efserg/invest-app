package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.model.{Currency, Stock}

case class UpdateStockRequest(ticker: String, currency: Currency)

sealed trait UpdateStockResponse

object UpdateStockResponse {

  case class Updated(stock: Stock) extends UpdateStockResponse

  case class NotFound(message: String) extends UpdateStockResponse

  case class NotValid(message: String) extends UpdateStockResponse

  case class Failed(message: String) extends UpdateStockResponse

}