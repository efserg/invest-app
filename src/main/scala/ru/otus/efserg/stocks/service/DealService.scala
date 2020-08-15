package ru.otus.efserg.stocks.service

import ru.otus.efserg.stocks.route.model._

trait DealService {
  def get(request: GetDealRequest): GetDealResponse

  def delete(request: DeleteDealRequest): DeleteDealResponse

  def update(request: UpdateDealRequest): UpdateDealResponse

  def create(request: CreateDealRequest): CreateDealResponse

}
