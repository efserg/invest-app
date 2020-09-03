package ru.otus.efserg.stocks.dao.impl

import ru.otus.efserg.stocks.dao.StockDao
import ru.otus.efserg.stocks.dao.model.Stock
import ru.otus.efserg.stocks.{StockAlreadyExistException, StockNotFoundException, StockValidateException}

import scala.util.{Failure, Success, Try}

class StockDaoMapImpl extends StockDao {

  private var db: Map[String, Stock] = Map.empty

  private val validTickerRegex = "[A-Z]+".r

  override def create(stock: Stock): Try[Stock] =
    validate(stock, Seq(isExistAlready, isValidTicker)) match {
      case Success(v) =>
        db += (v.ticker -> v)
        Success(v)
      case f @ Failure(_) => f
    }

  override def get(ticker: String): Try[Stock] =
    db.get(ticker) match {
      case Some(s) => Success(s)
      case None => Failure(StockNotFoundException(ticker))
    }

  override def delete(ticker: String): Try[Stock] =
    db.get(ticker) match {
      case Some(s) =>
        db -= ticker
        Success(s)
      case None => Failure(StockNotFoundException(ticker))
    }

  override def update(ticker: String, stock: Stock): Try[Stock] =
    validate(stock, Seq(isExist, isValidTicker)) match {
      case Success(_) =>
        db += (ticker -> stock)
        Success(stock)
      case f@Failure(_) => f
    }

  override def find(condition: Stock => Boolean): Seq[Stock] = db.values.filter(s => condition(s)).toVector

  private def isExist(stock: Stock): Try[Stock] =
    get(stock.ticker) match {
      case Failure(_) => Failure(StockNotFoundException(stock.ticker))
      case _ => Success(stock)
    }

  private def isExistAlready(stock: Stock): Try[Stock] =
    get(stock.ticker) match {
      case Success(_) => Failure(StockAlreadyExistException(stock.ticker))
      case _ => Success(stock)
    }

  private def isValidTicker(stock: Stock): Try[Stock] =
    if (!validTickerRegex.matches(stock.ticker))
      Failure(StockValidateException(stock.ticker, "Ticker must contain only upper case letters"))
    else Success(stock)

  private def validate(stock: Stock, conditions: Seq[Stock => Try[Stock]]): Try[Stock] =
    conditions.map(_ (stock))
      .find(_.isFailure)
      .getOrElse(Success(stock))

}
