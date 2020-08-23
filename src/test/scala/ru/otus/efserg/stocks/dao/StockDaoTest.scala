package ru.otus.efserg.stocks.dao

import org.scalacheck.Gen
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import ru.otus.efserg.stocks.dao.model.Stock
import ru.otus.efserg.stocks.{StockAlreadyExistException, StockNotFoundException, StockValidateException}

import scala.util.{Failure, Success}

abstract class StockDaoTest(name: String, createDao: () => StockDao)
  extends AnyFreeSpec
    with ScalaCheckDrivenPropertyChecks {

  private val regex = "[A-Z]+".r

  private val validTicker: Gen[String] = Gen.alphaUpperStr.suchThat(!_.isEmpty)

  val validStock: Gen[Stock] = for {
    ticker <- validTicker
  } yield Stock(ticker)

  val invalidStock: Gen[Stock] = for {
    ticker <- Gen.alphaNumStr.suchThat(!regex.matches(_))
  } yield Stock(ticker)

  name - {
    "create" - {
      "create any number of stocks" in {
        forAll(validStock) { stock =>
          val dao = createDao()
          val createdStock = dao.create(stock)
          createdStock shouldBe Success(stock)
          createdStock.get shouldBe stock
        }
      }

      "fail for invalid ticker" in {
        forAll(invalidStock) { stock =>
          val dao = createDao()
          val createdStock = dao.create(stock)
          createdStock shouldBe Failure(StockValidateException(stock.ticker, "Ticker must contain only upper case letters"))
        }
      }

      "fail for already exist ticker" in {
        forAll(validStock) { stock =>
          val dao = createDao()
          val createdStock = dao.create(stock)
          val alreadyCreatedStock = dao.create(stock)
          createdStock shouldBe Success(stock)
          alreadyCreatedStock shouldBe Failure(StockAlreadyExistException(stock.ticker))
        }
      }
    }

    "get" - {
      "get unknown stock" in {
        forAll(validTicker) { ticker =>
          val dao = createDao()
          dao.get(ticker) shouldBe Failure(StockNotFoundException(ticker))
        }
      }

      "get known stock" in {
        forAll(validStock) { stock =>
          val dao = createDao()
          dao.create(stock)
          dao.get(stock.ticker) shouldBe Success(stock)
        }
      }
    }

    "delete" - {
      "delete known stock" in {
        forAll(validStock) { stock =>
          val dao = createDao()
          dao.create(stock)
          dao.get(stock.ticker) shouldBe Success(stock)
          dao.delete(stock.ticker) shouldBe Success(stock)
          dao.get(stock.ticker) shouldBe Failure(StockNotFoundException(stock.ticker))
        }
      }

      "delete unknown stock" in {
        forAll(validTicker) { ticker =>
          val dao = createDao()
          dao.delete(ticker) shouldBe Failure(StockNotFoundException(ticker))
        }
      }
    }
  }
}
