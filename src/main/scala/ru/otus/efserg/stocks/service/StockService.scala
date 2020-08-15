package ru.otus.efserg.stocks.service

import ru.otus.efserg.stocks.route.model._

trait StockService {
  def get(request: GetStockRequest): GetStockResponse

  def delete(request: DeleteStockRequest): DeleteStockResponse

  def update(request: UpdateStockRequest): UpdateStockResponse

  def create(request: CreateStockRequest): CreateStockResponse
}
