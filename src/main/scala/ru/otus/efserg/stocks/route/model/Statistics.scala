package ru.otus.efserg.stocks.route.model
import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao.ID
import ru.otus.efserg.stocks.dao.model.Currency

sealed trait StatisticsRequest

object StatisticsRequest {

  case class UserStatistics(userId: ID,
                            dateFrom: Option[LocalDateTime] = None,
                            dateTo: Option[LocalDateTime] = None)
      extends StatisticsRequest

  case class StockStatistics(dateFrom: Option[LocalDateTime] = None,
                             dateTo: Option[LocalDateTime] = None)
      extends StatisticsRequest
}
sealed trait StatisticsResponse

object StatisticsResponse {
  case class UserStatistic(summary: Iterable[UserSummary])
      extends StatisticsResponse

  case class StockStatistic(summary: Iterable[StockSummary])
      extends StatisticsResponse
}

case class UserSummary(ticker: String,
                       quantity: Long,
                       moneyInTicker: BigDecimal,
                       commission: BigDecimal,
                       avg: BigDecimal,
                       closePositionLastDate: Option[LocalDateTime],
                       closePositionProfit: Option[BigDecimal],
                       currency: Currency)

case class StockSummary(ticker: String,
                        dealsCount: Long,
                        sold: Long,
                        bought: Long,
                        maxPrice: BigDecimal,
                        minPrice: BigDecimal,
                        userCount: Long,
                        currency: Currency)
