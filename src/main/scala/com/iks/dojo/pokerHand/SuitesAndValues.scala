package com.iks.dojo.pokerHand

object Suites extends Enumeration {
  type Suite = Suites.Value
  val DIAMONDS: Suite = Value(1, "D")
  val HEARTS: Suite = Value(2, "H")
  val SPADES: Suite = Value(3, "S")
  val CLUBS: Suite = Value(4, "C")

  def asString(suite: Suite): String = suite match {
    case DIAMONDS => "Diamonds"
    case HEARTS => "Hearts"
    case SPADES => "Spades"
    case CLUBS => "Clubs"
  }
}

object Faces extends Enumeration {
  type Face = Value
  val TWO: Face = Value(1, "2")
  val THREE: Face = Value(2, "3")
  val FOUR: Face = Value(3, "4")
  val FIVE: Face = Value(4, "5")
  val SIX: Face = Value(5, "6")
  val SEVEN: Face = Value(6, "7")
  val EIGHT: Face = Value(7, "8")
  val NINE: Face = Value(8, "9")
  val TEN: Face = Value(9, "T")
  val JACK: Face = Value(10, "J")
  val QUEEN: Face = Value(11, "Q")
  val KING: Face = Value(12, "K")
  val ACE: Face = Value(13, "A")

  def asString(face: Face): String = face match {
    case face if Seq(TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE).contains(face) => face.toString
    case TEN => "10"
    case JACK => "Jack"
    case QUEEN => "Queen"
    case KING => "King"
    case ACE => "Ace"
  }
}
