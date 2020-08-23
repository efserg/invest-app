package ru.otus.efserg.stocks.route.model

import ru.otus.efserg.stocks.dao.ID
import ru.otus.efserg.stocks.dao.model.Deal

sealed trait UpdateDealRequest

object UpdateDealRequest {

  case class Update(id: ID, deal: Deal)

}

sealed trait UpdateDealResponse

object UpdateDealResponse {

  case class Updated(deal: Deal) extends UpdateDealResponse

  case class NotFound(id: ID) extends UpdateDealResponse

}