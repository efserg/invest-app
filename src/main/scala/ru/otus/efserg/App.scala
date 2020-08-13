package ru.otus.efserg

trait App {
}

object App {
  private class AppImpl() extends App {
  }

  def apply(): App = {
    new AppImpl()
  }
}
