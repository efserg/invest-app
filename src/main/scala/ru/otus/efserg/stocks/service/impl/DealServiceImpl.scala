package ru.otus.efserg.stocks.service.impl

import ru.otus.efserg.stocks.dao.DealDao
import ru.otus.efserg.stocks.route.model._
import ru.otus.efserg.stocks.service.DealService

class DealServiceImpl(dao: DealDao) extends DealService{
  override def get(request: GetDealRequest): GetDealResponse = ???

  override def delete(request: DeleteDealRequest): DeleteDealResponse = ???

  override def update(request: UpdateDealRequest): UpdateDealResponse = ???

  override def create(request: CreateDealRequest): CreateDealResponse = ???
}
