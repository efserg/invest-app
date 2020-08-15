package ru.otus.efserg.stocks

import java.util.UUID

package object dao {
  type ID = UUID

  def createId: ID = UUID.randomUUID()

  implicit def createId(s: String): ID = UUID.fromString(s)

}
