package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.model.Stock

case class CreateStockRequest(stock: Stock)

sealed trait CreateStockResponse

object CreateStockResponse {

  case class Created(stock: Stock) extends CreateStockResponse

  case class AlreadyExist(message: String) extends CreateStockResponse

  case class NotValid(message: String) extends CreateStockResponse

  case class Failed(reason: String) extends CreateStockResponse

}