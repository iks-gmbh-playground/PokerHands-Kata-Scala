package com.iks.dojo.pokerHand

class ComparisonSpec extends UnitSpec {

  "2H 3D 5S 9C KD 2C 3H 4S 8C AH" should "let second hand win" in {
    val input = "2H 3D 5S 9C KD 2C 3H 4S 8C AH"
    val hands = PokerHand.parseMultipleHands(input).get
    assert(hands.length == 2)
    assert(hands(0) < hands(1))
  }

  "2H 2D 2S 2C KD 3C 3H 3S 8C 8H" should "let first hand win" in {
    val input = "2H 2D 2S 2C KD 3C 3H 3S 8C 8H"
    val hands = PokerHand.parseMultipleHands(input).get
    assert(hands.length == 2)
    assert(hands(0) > hands(1))
  }

  "2H 2D 2S 2C KD 3C 3H 3S 8C KD" should "be a valid game" in {
    val input = "2H 2D 2S 2C KD 3C 3H 3S 8C KD"
    val hands = PokerHand.parseMultipleHands(input)
    assert(hands.isEmpty)
  }

}
