package com.iks.dojo.pokerHand

import scala.annotation.tailrec
import scala.language.postfixOps

case class PokerHand(cards: Seq[Card]) extends Ordered[PokerHand] {
  assert(cards.size == 5)

  private val groupedByFace = cards.groupBy(_.face)
  private val groupedBySuite = cards.groupBy(_.suite)

  import PokerHand._

  val highestFaceValue: Long =
    cards.maxBy(_.faceValue).faceValue

  val smallestFaceValue: Long =
    cards.minBy(_.faceValue).faceValue

  val isFlush: Boolean =
    groupedBySuite.size == 1

  val hasASinglePair: Boolean =
    groupedByFace.size == 4

  val hasPair: Boolean =
    groupedByFace.exists(_._2.length == 2)

  val hasTwoPairs: Boolean =
    groupedByFace.size == 3 && groupedByFace.map(_._2.length).max == 2

  val hasThreeOfAKind: Boolean =
    groupedByFace.map(p => p._2.length).max == 3

  val hasFourOfAKind: Boolean =
    groupedByFace.map(p => p._2.length).max == 4

  val allDifferentFaceValues: Boolean =
    groupedByFace.size == 5

  val isStraight: Boolean =
    allDifferentFaceValues && (highestFaceValue - smallestFaceValue == 4)

  val isFullHouse: Boolean =
    hasThreeOfAKind && hasPair

  val hasStraightFlush: Boolean =
    isFlush && isStraight

  def singlePairValue: Long = {
    assert(hasASinglePair)
    val pairValue = groupedByFace.find { case (_, cs) => cs.length == 2 } .get ._1.id
    pairValue * pairBase + valueOfCards(cards.filter(_.faceValue != pairValue))
  }

  def twoPairValue: Long = {
    assert(hasTwoPairs)
    val valuesOfPairs = groupedByFace.filter(_._2.length == 2).map(p => p._1.id).toSeq.sorted
    valuesOfPairs.last * twoPairBase +
      valuesOfPairs.head * pairBase +
      valueOfCards(Seq(groupedByFace.find(_._2.length == 1).get._2.head))
  }

  def threeOfAKindValue: Long = {
    assert(hasThreeOfAKind)
    val valueOfTriple = groupedByFace.find(p => p._2.length == 3).get._1.id
    valueOfTriple * threeOfAKindBase +
      valueOfCards(cards.filter(_.faceValue != valueOfTriple))
  }

  def straightValue: Long =
    straightBase + valueOfCards(cards)

  def flushValue: Long =
    flushBase + highestFaceValue + cards.head.suite.id * base

  def straightFlushValue: Long =
    straightFlushBase + highestFaceValue + cards.head.suite.id * base

  def fullHouseValue: Long = {
    assert(isFullHouse)
    val valueOfTriple = groupedByFace.find(_._2.length == 3).get._1.id
    fullHouseBase * valueOfTriple
  }

  def fourOfAKindValue: Long = {
    assert(hasFourOfAKind)
    val valueOfFour = groupedByFace.find(_._2.length == 4).get._1.id
    fourOfAKindBase * valueOfFour
  }

  def highCardValue: Long = valueOfCards(cards)

  val value: Long = cards match {
    case _ if hasStraightFlush => straightFlushValue
    case _ if hasFourOfAKind => fourOfAKindValue
    case _ if isFullHouse => fullHouseValue
    case _ if isFlush => flushValue
    case _ if isStraight => straightValue
    case _ if hasThreeOfAKind => threeOfAKindValue
    case _ if hasTwoPairs => twoPairValue
    case _ if hasASinglePair => singlePairValue
    case _ => highCardValue
  }

  override def compare(that: PokerHand): Int = this.value - that.value match {
    case diff if diff > 0 => 1
    case diff if diff == 0 => 0
    case _ => -1
  }
}

object PokerHand {
  private val base = 14
  private val pairBase = math.pow(base, 5).toLong
  private val twoPairBase = math.pow(base, 6).toLong
  private val threeOfAKindBase = math.pow(14, 7).toLong
  private val straightBase = math.pow(base, 8).toLong
  private val flushBase = math.pow(base, 9).toLong
  private val fullHouseBase = math.pow(base, 10).toLong
  private val fourOfAKindBase = math.pow(base, 11).toLong
  private val straightFlushBase = math.pow(base, 13).toLong

  def parse(input: String): Option[PokerHand] = {
    val deck = new Deck
    input.trim.split(" +") flatMap deck.getCard match {
      case cards if cards.length == 5 => Some(PokerHand(cards))
      case _ => None
    }
  }

  def parseMultipleHands(input: String): Option[Seq[PokerHand]] = {
    @tailrec
    def createHands(cards: Seq[Card], hands: Seq[PokerHand] = Seq.empty): Seq[PokerHand] = cards match {
      case _ if cards.isEmpty => hands
      case _ => createHands(cards.drop(5), hands :+ PokerHand(cards.take(5)))
    }
    val deck = new Deck
    input.trim.split(" +") flatMap deck.getCard match {
      case cards if cards.length % 5 == 0 => Some(createHands(cards))
      case _ => None
    }

  }

  def valueOfCards(cards: Seq[Card]): Long = cards.map(_.faceValue)
    .sorted.reverse
    .foldLeft(0)((acc, v) => acc * base + v)
}
