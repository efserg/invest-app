package ru.otus.efserg.stocks.dao

import ru.otus.efserg.stocks.dao.model.Stock

import scala.util.Try

trait StockDao {
  def create(stock: Stock): Try[Stock]

  def get(ticker: String): Try[Stock]

  def delete(ticker: String): Try[Stock]

  def update(ticker: String, stock: Stock): Try[Stock]

  def find(condition: Stock => Boolean): Seq[Stock]
}
