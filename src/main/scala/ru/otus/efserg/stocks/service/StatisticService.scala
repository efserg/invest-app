package ru.otus.efserg.stocks.service
import ru.otus.efserg.stocks.route.model.{
  UserStatisticsRequest, StatisticsResponse}

trait StatisticService {
  def stockSummary(request: UserStatisticsRequest): StatisticsResponse
}
