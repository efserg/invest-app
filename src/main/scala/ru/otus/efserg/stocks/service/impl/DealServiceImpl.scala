package ru.otus.efserg.stocks.service.impl

import ru.otus.efserg.stocks.{DealNotFoundException, DealValidateException}
import ru.otus.efserg.stocks.dao.{DealDao, ID}
import ru.otus.efserg.stocks.dao.model.Deal
import ru.otus.efserg.stocks.route.model._
import ru.otus.efserg.stocks.service.DealService

import scala.util.{Failure, Success}

class DealServiceImpl(dao: DealDao) extends DealService {

  override def get(request: GetDealRequest): GetDealResponse =
    dao.get(request.id) match {
      case Success(s) => GetDealResponse.Found(s)
      case Failure(ex) => ex match {
          case _: DealNotFoundException => GetDealResponse.NotFound(ex.getMessage)
          case _ => GetDealResponse.Failed(ex.getMessage)
        }
    }

  override def delete(request: DeleteDealRequest): DeleteDealResponse =
    dao.delete(request.id) match {
      case Success(s) => DeleteDealResponse.Deleted(s.id)
      case Failure(ex) => ex match {
          case _: DealNotFoundException => DeleteDealResponse.NotFound(ex.getMessage)
          case _ => DeleteDealResponse.Failed(ex.getMessage)
        }
    }

  override def update(id: ID, request: UpdateDealRequest): UpdateDealResponse =
    dao.get(id) match {
      case Success(d) => updateExist(d, request)
      case Failure(ex) => ex match {
          case _: DealNotFoundException => UpdateDealResponse.NotFound(ex.getMessage)
          case _ => UpdateDealResponse.Failed(ex.getMessage)
        }
    }

  private def updateExist(d: Deal, request: UpdateDealRequest) =
    dao.update(d.id,
      d.copy(ticker = request.ticker.getOrElse(d.ticker),
        price = request.price.getOrElse(d.price),
        quantity = request.quantity.getOrElse(d.quantity),
        commission = request.commission.getOrElse(d.commission),
        time = request.time.getOrElse(d.time))
    ) match {
      case Success(deal) => UpdateDealResponse.Updated(deal)
      case Failure(ex) => ex match {
          case _: DealValidateException => UpdateDealResponse.ImpossibleDeal(ex.getMessage)
          case _ => UpdateDealResponse.Failed(ex.getMessage)
        }
    }
  override def create(request: CreateDealRequest): CreateDealResponse =
    dao.create(Deal(request.ticker, request.price, request.quantity, request.commission, request.time)) match {
      case Success(d) => CreateDealResponse.Created(d)
      case Failure(ex) => ex match {
          case _: DealValidateException => CreateDealResponse.ImpossibleDeal(ex.getMessage)
          case _ => CreateDealResponse.Failed(ex.getMessage)
        }
    }
}
