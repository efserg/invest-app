package ru.otus.efserg.stocks.service.impl
import java.time.LocalDateTime

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import ru.otus.efserg.stocks.dao.model.{Currency, Stock}
import ru.otus.efserg.stocks.dao.{DealDao, StockDao}
import ru.otus.efserg.stocks.route.model.{StatisticsRequest, StatisticsResponse}

import scala.util.Success

class StatisticServiceMapDaoTest extends AnyFreeSpec with MockFactory with MockDb {

  "Statistic service" - {
    "user statistics" - {
      "calculate closed and open positions" in {
         val dealDaoStub = stub[DealDao]
         (dealDaoStub.find _).when(*).returns(userDeals)

         val stockDaoStub = stub[StockDao]
         (stockDaoStub.get _).when(*).returns(Success(Stock("A", Currency.USD)))

         val response = new StatisticServiceImpl(dealDaoStub, stockDaoStub)
           .userSummary(StatisticsRequest.UserStatistics(userId))
           .asInstanceOf[StatisticsResponse.UserStatistic]
         response.summary should have size 1
         val summary = response.summary.head
         summary.closePositionProfit shouldBe Some(2)
         summary.closePositionLastDate shouldBe Some(LocalDateTime.of(2000, 1, 1, 13, 0, 0))
         summary.moneyInTicker shouldBe -2
         summary.quantity shouldBe 1
         summary.commission shouldBe -0.28
         summary.avg shouldBe -2
         summary.currency shouldBe Currency.USD
      }
    }

    "stock statistics" - {
      "calculate stock summary" in {
        val dealDaoStub = stub[DealDao]
        (dealDaoStub.find _).when(*).returns(stockDeals)

        val stockDaoStub = stub[StockDao]
        (stockDaoStub.get _).when(*).returns(Success(Stock("A", Currency.USD)))

        val response = new StatisticServiceImpl(dealDaoStub, stockDaoStub)
          .stockSummary(StatisticsRequest.StockStatistics())
          .asInstanceOf[StatisticsResponse.StockStatistic]
        response.summary should have size 1
        val summary = response.summary.head
        summary.dealsCount shouldBe 8
        summary.bought shouldBe 6
        summary.sold shouldBe 2
        summary.maxPrice shouldBe 31
        summary.minPrice shouldBe 0.1
        summary.userCount shouldBe 3
        summary.currency shouldBe Currency.USD
      }
    }
  }
}
