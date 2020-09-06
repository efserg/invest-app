package ru.otus.efserg.stocks.service.impl
import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao.model.Deal
import ru.otus.efserg.stocks.dao.{DealDao, StockDao}
import ru.otus.efserg.stocks.route.model.{
  StatisticsRequest,
  StatisticsResponse,
  StockSummary,
  UserSummary
}
import ru.otus.efserg.stocks.service.StatisticService

class StatisticServiceImpl(dealDao: DealDao, stockDao: StockDao)
    extends StatisticService {

  private val ZERO = BigDecimal(0)

  override def userSummary(
    request: StatisticsRequest.UserStatistics
  ): StatisticsResponse = {

    val accumulatedDeals: Map[String, Seq[Accumulator]] = dealDao
      .find(
        d =>
          d.userId == request.userId &&
            request.dateFrom.forall(_.isAfter(d.time)) &&
            request.dateTo.forall(_.isBefore(d.time))
      )
      .sortBy(_.time)
      .groupBy(d => d.ticker)
      .map({
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
      val stock = stockDao.get(ticker).get // todo: implement error handling
      val currency = stock.currency

      UserSummary(
        ticker,
        count,
        moneyInTicker,
        openDeal.map(_.commission).getOrElse(ZERO),
        moneyInTicker / count,
        closeDeal.map(_.time),
        closeDeal.map(_.price),
        currency
      )
    })
    StatisticsResponse.UserStatistic(summary)
  }

  override def stockSummary(
    request: StatisticsRequest.StockStatistics
  ): StatisticsResponse = {
    val summary: Map[String, StockSummary] = dealDao
      .find(
        d =>
          request.dateFrom.forall(_.isAfter(d.time)) &&
            request.dateTo.forall(_.isBefore(d.time))
      )
      .groupBy(d => d.ticker)
      .map({
        case (ticker: String, ds: Seq[Deal]) =>
          ticker -> StockSummary(
            ticker,
            ds.length,
            ds.count(_.price > 0),
            ds.count(_.price < 0),
            ds.map(_.price.abs).max,
            ds.map(_.price.abs).min,
            ds.map(_.userId).distinct.length,
            stockDao.get(ticker).get.currency // todo
          )
      })

    StatisticsResponse.StockStatistic(summary.values)
  }

  private case class Accumulator(ticker: String,
                                 quantity: Long,
                                 price: BigDecimal,
                                 commission: BigDecimal,
                                 time: LocalDateTime)

}
