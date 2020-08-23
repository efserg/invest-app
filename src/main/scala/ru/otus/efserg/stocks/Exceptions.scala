package ru.otus.efserg.stocks

case class StockNotFoundException(ticker: String) extends Exception(s"Stock with ticker $ticker was not found")

case class StockAlreadyExistException(ticker: String) extends Exception(s"Stock with ticker $ticker already exists")

case class StockCreateException(ticker: String, reason: String) extends Exception(s"Stock with ticker $ticker was not created. Reason: '$reason'")

case class StockValidateException(ticker: String, reason: String) extends Exception(s"Stock with ticker $ticker was not valid. Reason: '$reason'")