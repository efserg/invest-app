package ru.otus.efserg.stocks.dao

import ru.otus.efserg.stocks.dao.model.Deal

import scala.util.Try

trait DealDao {

  def create(deal: Deal): Try[Deal]

  def get(id: ID): Try[Deal]

  def delete(id: ID): Try[Deal]

  def update(id: ID, deal: Deal): Try[Deal]

  def find(condition: Deal => Boolean): Seq[Deal]
}