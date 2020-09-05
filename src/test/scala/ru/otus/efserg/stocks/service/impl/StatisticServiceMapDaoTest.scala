package ru.otus.efserg.stocks.service.impl
import java.time.LocalDateTime

import org.scalamock.scalatest.MockFactory
import org.scalatest.freespec.AnyFreeSpec
import ru.otus.efserg.stocks.dao
import ru.otus.efserg.stocks.dao.{DealDao, ID}
import ru.otus.efserg.stocks.dao.model.Deal
import ru.otus.efserg.stocks.route.model.{StatisticsResponse, UserStatisticsRequest}
import org.scalatest.matchers.should.Matchers._

import scala.util.Random

class StatisticServiceMapDaoTest extends AnyFreeSpec with MockFactory {

  private val userId: ID = dao.createId

  private val deals = Random.shuffle(
    Deal("A", -1, 1L, Some(-0.01), Some(LocalDateTime.of(2000, 1, 1, 12, 0, 0)), userId) :: // (-1, 1)
    Deal("A", -3, 2L, Some(-0.02), Some(LocalDateTime.of(2000, 1, 1, 12, 0, 1)), userId) :: // (-4, 3)
    Deal("A", 2, -1L, Some(-0.03), Some(LocalDateTime.of(2000, 1, 1, 12, 1, 0)), userId) :: // (-2, 2)
    Deal("A", 4, -2L, Some(-0.04), Some(LocalDateTime.of(2000, 1, 1, 13, 0, 0)), userId) :: // ( 2, 0)
    Deal("A", -5, 3L, Some(-0.05), Some(LocalDateTime.of(2000, 1, 2, 12, 0, 0)), userId) :: // (-5, 3)
    Deal("A", 2, -1L, Some(-0.06), Some(LocalDateTime.of(2000, 2, 1, 12, 0, 0)), userId) :: // (-3, 2)
    Deal("A", 1, -1L, Some(-0.07), Some(LocalDateTime.of(2001, 1, 1, 12, 0, 0)), userId) :: // (-2, 1)
      Nil)

  private val dealDaoStub = stub[DealDao]
  (dealDaoStub.find _).when(*).returns(deals).anyNumberOfTimes()

  "Statistic service" - {
    "stock service" - {
      "calculate closed and open positions" in {
        val response = new StatisticServiceImpl(dealDaoStub)
          .stockSummary(UserStatisticsRequest(userId))
          .asInstanceOf[StatisticsResponse.UserStatistic]
        response.summary should have size 1
        val summary = response.summary.head
        summary.closePositionProfit shouldBe Some(2)
        summary.closePositionLastDate shouldBe Some(LocalDateTime.of(2000, 1, 1, 13, 0, 0))
        summary.moneyInTicker shouldBe -2
        summary.quantity shouldBe 1
        summary.commission shouldBe -0.28
        summary.avg shouldBe -2
      }
    }
  }
}
