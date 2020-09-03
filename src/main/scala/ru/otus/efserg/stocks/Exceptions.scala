package ru.otus.efserg.stocks
import ru.otus.efserg.stocks.dao.ID

case class StockNotFoundException(ticker: String) extends Exception(s"Stock with ticker $ticker was not found")

case class StockAlreadyExistException(ticker: String) extends Exception(s"Stock with ticker $ticker already exists")

case class StockCreateException(ticker: String, reason: String) extends Exception(s"Stock with ticker $ticker was not created. Reason: '$reason'")

case class StockValidateException(ticker: String, reason: String) extends Exception(s"Stock with ticker $ticker was not valid. Reason: '$reason'")

case class DealNotFoundException(id: ID) extends Exception(s"Deal with id $id was not found")

case class DealNotExistException() extends Exception("Looks like deal haven't save yet")

case class DealCreateException(id: ID, reason: String) extends Exception(s"Deal with id $id was not created. Reason: '$reason'")

case class DealAlreadyExistException(id: ID) extends Exception(s"Deal with id $id already exists")

case class DealValidateException(id: ID, reason: String) extends Exception(s"Deal with id $id was not valid. Reason: '$reason'")