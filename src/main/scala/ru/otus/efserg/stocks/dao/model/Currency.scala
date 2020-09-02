package ru.otus.efserg.stocks.dao.model

sealed trait Currency

object Currency {
  case object EUR extends Currency
  case object RUB extends Currency
  case object USD extends Currency
}

