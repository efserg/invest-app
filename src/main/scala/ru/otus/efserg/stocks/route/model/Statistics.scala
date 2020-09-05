package ru.otus.efserg.stocks.route.model
import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao.ID

case class UserStatisticsRequest(userId: ID, dateFrom: Option[LocalDateTime] = None, dateTo: Option[LocalDateTime] = None)

sealed trait StatisticsResponse

object StatisticsResponse {
  case class UserStatistic(summary: Iterable[StockSummary]) extends StatisticsResponse
}

case class StockSummary(ticker: String,
                     quantity: Long,
                     moneyInTicker: BigDecimal,
                     commission: BigDecimal,
                     avg: BigDecimal,
                     closePositionLastDate: Option[LocalDateTime],
                     closePositionProfit: Option[BigDecimal])