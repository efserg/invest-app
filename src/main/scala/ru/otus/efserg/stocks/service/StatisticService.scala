package ru.otus.efserg.stocks.service
import ru.otus.efserg.stocks.route.model.{StatisticsRequest, StatisticsResponse}

trait StatisticService {
  def userSummary(request: StatisticsRequest.UserStatistics): StatisticsResponse
  def stockSummary(request: StatisticsRequest.StockStatistics): StatisticsResponse
}
