package ru.otus.efserg.stocks.service.impl
import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao.model.Deal
import ru.otus.efserg.stocks.dao.{DealDao, StockDao, UserDao}
import ru.otus.efserg.stocks.route.model.{
  StockSummary, StatisticsResponse, UserStatisticsRequest}
import ru.otus.efserg.stocks.service.StatisticService

class StatisticServiceImpl(dealDao: DealDao)
    extends StatisticService {

  private val ZERO = BigDecimal(0)

  override def stockSummary(
    request: UserStatisticsRequest
  ): StatisticsResponse = {

    val deals: Map[String, Seq[Deal]] = dealDao
      .find(
        d =>
          d.userId == request.userId &&
            request.dateFrom.forall(_.isAfter(d.time)) &&
            request.dateTo.forall(_.isBefore(d.time))
      )
      .sortBy(_.time)
      .groupBy(d => d.ticker)

    val accumulatedDeals: Map[String, Seq[Accumulator]] = deals.map({
        case (ticker: String, ds: Seq[Deal]) =>
          ticker -> ds
            .scanLeft(Accumulator(ticker, 0L, ZERO, ZERO, LocalDateTime.MIN))(
              (acc, deal) =>
                Accumulator(
                  acc.ticker,
                  acc.quantity + deal.quantity,
                  acc.price + deal.price,
                  acc.commission + deal.commission,
                  deal.time
              )
            )
      })

    val closedPosition: Map[String, Option[Accumulator]] =
      accumulatedDeals.map({
        case (ticker: String, ds: Seq[Accumulator]) =>
          ticker -> ds.filter(_.quantity == 0).sortBy(_.time).lastOption
      })

    val openPosition: Map[String, Option[Accumulator]] = accumulatedDeals.map({
      case (ticker: String, ds: Seq[Accumulator]) =>
        ticker -> ds.sortBy(_.time).lastOption
    })

    val summary = accumulatedDeals.keys.map(ticker => {
      val openDeal = openPosition(ticker)
      val closeDeal = closedPosition(ticker)
      val moneyInTicker = openDeal.map(_.price).getOrElse(ZERO) -
        closeDeal.map(_.price).getOrElse(ZERO)
      val count = openDeal.map(_.quantity).getOrElse(0L)
      StockSummary(
        ticker,
        count,
        moneyInTicker,
        openDeal.map(_.commission).getOrElse(ZERO),
        moneyInTicker / count,
        closeDeal.map(_.time),
        closeDeal.map(_.price)
      )
    })
    StatisticsResponse.UserStatistic(summary)
  }

  private case class Accumulator(ticker: String,
                         quantity: Long,
                         price: BigDecimal,
                         commission: BigDecimal,
                         time: LocalDateTime)
}
