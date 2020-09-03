package ru.otus.efserg.stocks.dao.model

sealed trait Role

object Role {
  case object Reader extends Role
  case object Manager extends Role
  case object Admin extends Role
}
