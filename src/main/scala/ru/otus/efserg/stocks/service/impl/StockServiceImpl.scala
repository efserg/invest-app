package ru.otus.efserg.stocks.service.impl

import ru.otus.efserg.stocks.dao.StockDao
import ru.otus.efserg.stocks.route.model._
import ru.otus.efserg.stocks.service.StockService
import ru.otus.efserg.stocks.{StockAlreadyExistException, StockNotFoundException, StockValidateException}

import scala.util.{Failure, Success}

class StockServiceImpl(dao: StockDao) extends StockService {
  override def get(request: GetStockRequest): GetStockResponse =
    dao.get(request.ticker) match {
      case Success(s) => GetStockResponse.Found(s)
      case Failure(ex) => ex match {
        case _: StockNotFoundException => GetStockResponse.NotFound(ex.getMessage)
        case _ => GetStockResponse.Failed(ex.getMessage)
      }
    }

  override def delete(request: DeleteStockRequest): DeleteStockResponse =
    dao.delete(request.ticker) match {
      case Success(s) => DeleteStockResponse.Deleted(s.ticker)
      case Failure(ex) => ex match {
        case _: StockNotFoundException => DeleteStockResponse.NotFound(ex.getMessage)
        case _ => DeleteStockResponse.Failed(ex.getMessage)
      }
    }

  override def update(request: UpdateStockRequest): UpdateStockResponse =
    dao.update(request.ticker, request.stock) match {
      case Success(s) => UpdateStockResponse.Updated(s)
      case Failure(ex) => ex match {
        case _: StockNotFoundException => UpdateStockResponse.NotFound(ex.getMessage)
        case _: StockValidateException => UpdateStockResponse.NotValid(ex.getMessage)
        case _ => UpdateStockResponse.Failed(ex.getMessage)
      }
    }

  override def create(request: CreateStockRequest): CreateStockResponse =
    dao.create(request.stock) match {
      case Success(s) => CreateStockResponse.Created(s)
      case Failure(ex) => ex match {
        case _: StockAlreadyExistException => CreateStockResponse.AlreadyExist(ex.getMessage)
        case _: StockValidateException => CreateStockResponse.NotValid(ex.getMessage)
        case _ => CreateStockResponse.Failed(ex.getMessage)
      }
    }
}
