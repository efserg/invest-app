package ru.otus.efserg.stocks.dao.impl

import ru.otus.efserg.stocks.{DealAlreadyExistException, DealNotExistException, DealNotFoundException, dao}
import ru.otus.efserg.stocks.dao.{DealDao, ID}
import ru.otus.efserg.stocks.dao.model.Deal

import scala.util.{Failure, Success, Try}

class DealDaoMapImpl extends DealDao {

  private var db: Map[ID, Deal] = Map.empty

  override def create(deal: Deal): Try[Deal] =
    validate(deal, Seq(isExistAlready)) match {
      case Success(v) =>
        db += (dao.createId -> v)
        Success(v)
      case f @ Failure(_) => f
    }

  override def get(id: ID): Try[Deal] =
    db.get(id) match {
      case Some(s) => Success(s)
      case None => Failure(DealNotFoundException(id))
    }

  override def delete(id: ID): Try[Deal] =
    db.get(id) match {
      case Some(s) =>
        db -= id
        Success(s)
      case None => Failure(DealNotFoundException(id))
    }

  override def update(id: ID, deal: Deal): Try[Deal] =
    validate(deal, Seq(isExist)) match {
      case Success(_) =>
        db += (id -> deal)
        Success(deal)
      case f@Failure(_) => f
    }

  override def find(condition: Deal => Boolean): Seq[Deal] =
    db.values.filter(s => condition(s)).toVector

  private def isExist(deal: Deal): Try[Deal] =
    get(deal.id) match {
      case Failure(_) => Failure(DealNotFoundException(deal.id))
      case _          => Success(deal)
    }

  private def isExistAlready(deal: Deal): Try[Deal] =
    get(deal.id) match {
      case Success(_) => Success(deal)
      case _          => Failure(DealAlreadyExistException(deal.id))
    }

  private def validate(deal: Deal,
                       conditions: Seq[Deal => Try[Deal]]): Try[Deal] =
    conditions
      .map(_(deal))
      .find(_.isFailure)
      .getOrElse(Success(deal))
}
