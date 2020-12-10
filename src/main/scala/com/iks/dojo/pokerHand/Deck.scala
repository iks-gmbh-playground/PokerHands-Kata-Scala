package com.iks.dojo.pokerHand

import com.iks.dojo.pokerHand.Faces.Face
import com.iks.dojo.pokerHand.Suites.Suite

class Deck {
  var cards: Map[String, Card] = (for {
    s <- Suites.values.toSeq
    v <- Faces.values.toSeq
  } yield s"${v.toString}${s.toString}" -> Card(v, s)).toMap

  def getCard(symbol: String): Option[Card] =
    cards.get(symbol) match {
      case None => None
      case Some(card) =>
        cards = cards.filter { case (_, c) => c != card }
        Some(card)
    }
}

case class Card(face: Face, suite: Suite) extends Ordered[Card] {
  val faceValue: Int = face.id
  val cardValue: Int = face.id * 10 + suite.id
  val symbol: String = s"${face.toString}${suite.toString}"
  val cardName: String = s"${Faces.asString(face)} of ${Suites.asString(suite)}"

  override def toString: String = s"$symbol: $cardName"

  override def compare(that: Card): Int = this.cardValue - that.cardValue
}
