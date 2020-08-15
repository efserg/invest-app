package ru.otus.efserg.stocks.route.model

case class DeleteStockRequest(ticker: String)

sealed trait DeleteStockResponse

object DeleteStockResponse {

  case class Deleted(ticker: String) extends DeleteStockResponse

  case class NotFound(ticker: String) extends DeleteStockResponse

}