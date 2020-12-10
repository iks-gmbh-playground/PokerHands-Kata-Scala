package com.iks.dojo.pokerHand

class helperSpec extends UnitSpec {
  "2H 4H TH QH KH" should "be a flush" in {
    assert(PokerHand.parse("2H 4H TH QH KH").get.isFlush)
  }

  "2D 4H TH QH KH" should "not be a flush" in {
    val input = "2D 4H TH QH KH"
    assert(!PokerHand.parse(input).get.isFlush)
  }

  it should "should have a high card value" in {
    val hand = PokerHand.parse("2D 4H TH QH KH").get
    val expected = hand
      .cards.map(_.faceValue)
      .sorted
      .reverse
      .foldLeft(0)((acc, v) => acc * 14 + v)
    assertResult(expected)(hand.value)
  }

  "TH QH KH" should "have a high card value" in {
    val deck = new Deck
    val cards = "TH QH KH".trim.split(" ") flatMap deck.getCard
    assertResult(2515)(PokerHand.valueOfCards(cards))
  }

  "KH" should "have a high card value" in {
    val deck = new Deck
    val cards = "KH".trim.split(" ") flatMap deck.getCard
    assertResult(12)(PokerHand.valueOfCards(cards))
  }

  "2D 2H TH QH KH" should "have a single pair" in {
    val hand = PokerHand.parse("2D 2H TH QH KH").get
    assert(hand.hasASinglePair)
  }

  it should "have a single pair value" in {
    val hand = PokerHand.parse("2D 2H TH QH KH").get
    assertResult(540339)(hand.singlePairValue)
  }

  "2D JH QH QS KH" should "have a single pair value" in {
    val hand = PokerHand.parse("2D JH QH QS KH").get
    assertResult(5918557)(hand.singlePairValue)
  }

  "2D 3H TH QH KH" should "does not have a single pair" in {
    val hand = PokerHand.parse("2D 3H TH QH KH").get
    assert(!hand.hasASinglePair)
  }

  it should "throw an exception" in {
    val hand = PokerHand.parse("2D 3H TH QH KH").get
    assertThrows[AssertionError](hand.singlePairValue)
  }

  "2D 2H 2S QH KH" should "not have a single pair" in {
    val hand = PokerHand.parse("2D 2H 2S QH KH").get
    assert(!hand.hasASinglePair)
  }

  it should "not have two pairs" in {
    val hand = PokerHand.parse("2D 2H 2S QH KH").get
    assert(!hand.hasTwoPairs)
  }

  "2D 3H TH QH KH" should "is not a flush" in {
    val hand = PokerHand.parse("2D 3H TH QH KH").get
    assert(!hand.isFlush)
  }

  "2H 3H TH QH KH" should "is a flush" in {
    val hand = PokerHand.parse("2H 3H TH QH KH").get
    assert(hand.isFlush)
  }

  "2H 2D TC TH KH" should "have two pairs " in {
    val hand = PokerHand.parse("2H 2D TC TH KH").get
    assert(hand.hasTwoPairs)
  }

  "2H 2D TC 3H KH" should "not have two pairs " in {
    val hand = PokerHand.parse("2H 2D TC 3H KH").get
    assert(!hand.hasTwoPairs)
  }

  "2H 2D 2C 3H KH" should "have three of a kind" in {
    val hand = PokerHand.parse("2H 2D 2C 3H KH").get
    assert(hand.hasThreeOfAKind)
  }

  "2H 2D 2C 3H 2S" should "have four of a kind" in {
    val hand = PokerHand.parse("2H 2D 2C 3H 2S").get
    assert(hand.hasFourOfAKind)
  }

  "7H 8D 9C TH JS" should "have a straight" in {
    val hand = PokerHand.parse("7H 8D 9C TH JS").get
    assert(hand.isStraight)
  }

  "7H 8D 9C TH QS" should "not have a straight" in {
    val hand = PokerHand.parse("7H 8D 9C TH QS").get
    assert(!hand.isStraight)
  }

  "7H 7S 7C TH TD" should "have a full house" in {
    val hand = PokerHand.parse("7H 7S 7C TH TD").get
    assert(hand.isFullHouse)
  }

  "7H" should "result in card with an informative string value" in {
    val sevenOfHearts = Card(Faces.SEVEN, Suites.HEARTS)
    assertResult("7H: 7 of Hearts")(sevenOfHearts.toString)
  }

  "two cards" should "be compared correctly" in {
    val queenOfClubs = Card(Faces.QUEEN, Suites.CLUBS)
    val queenOfSpades = Card(Faces.QUEEN, Suites.SPADES)
    assert(queenOfClubs > queenOfSpades)
  }
}
