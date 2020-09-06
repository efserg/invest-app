package ru.otus.efserg.stocks.service.impl
import java.time.LocalDateTime

import ru.otus.efserg.stocks.dao
import ru.otus.efserg.stocks.dao.ID
import ru.otus.efserg.stocks.dao.model.Deal

import scala.util.Random

trait MockDb {
  val userId: ID = dao.createId
  val userId1: ID = dao.createId
  val userId2: ID = dao.createId
  val userId3: ID = dao.createId

  val userDeals: List[Deal] = Random.shuffle(
    Deal("A", -1, 1L, Some(-0.01), Some(LocalDateTime.of(2000, 1, 1, 12, 0, 0)), userId) :: // (-1, 1)
      Deal("A", -3, 2L, Some(-0.02), Some(LocalDateTime.of(2000, 1, 1, 12, 0, 1)), userId) :: // (-4, 3)
      Deal("A", 2, -1L, Some(-0.03), Some(LocalDateTime.of(2000, 1, 1, 12, 1, 0)), userId) :: // (-2, 2)
      Deal("A", 4, -2L, Some(-0.04), Some(LocalDateTime.of(2000, 1, 1, 13, 0, 0)), userId) :: // ( 2, 0)
      Deal("A", -5, 3L, Some(-0.05), Some(LocalDateTime.of(2000, 1, 2, 12, 0, 0)), userId) :: // (-5, 3)
      Deal("A", 2, -1L, Some(-0.06), Some(LocalDateTime.of(2000, 2, 1, 12, 0, 0)), userId) :: // (-3, 2)
      Deal("A", 1, -1L, Some(-0.07), Some(LocalDateTime.of(2001, 1, 1, 12, 0, 0)), userId) :: // (-2, 1)
      Nil)

  val stockDeals: List[Deal] = Random.shuffle(
    Deal("A", -0.1, 1L, Some(-0.01), Some(LocalDateTime.of(2000, 1, 1, 12, 0, 0)), userId1) ::
    Deal("A", -3, 2L, Some(-0.01), Some(LocalDateTime.of(2000, 1, 1, 12, 0, 0)), userId1) ::
    Deal("A", 30, -3L, Some(-0.01), Some(LocalDateTime.of(2010, 1, 1, 12, 0, 0)), userId1) ::
    Deal("A", -2, 2L, Some(-0.01), Some(LocalDateTime.of(1999, 1, 1, 12, 0, 0)), userId2) ::
    Deal("A", 1, -2L, Some(-0.01), Some(LocalDateTime.of(2001, 1, 1, 12, 0, 0)), userId2) ::
    Deal("A", -1, 1L, Some(-0.01), Some(LocalDateTime.of(2000, 1, 1, 12, 0, 0)), userId3) ::
    Deal("A", -3, 2L, Some(-0.01), Some(LocalDateTime.of(2002, 1, 1, 12, 0, 0)), userId3) ::
    Deal("A", -31, 20L, Some(-0.01), Some(LocalDateTime.of(2003, 1, 1, 12, 0, 0)), userId3) ::
      Nil)



}
