package com.iks.dojo.pokerHand

class PokerRulesSpec extends UnitSpec {
  "2H" should "be a valid card" in {
    assert((new Deck).getCard("2H").isDefined)
  }

  "2T" should "be an invalid card" in {
    assert((new Deck).getCard("2T").isEmpty)
  }

  "2H" should "have the value 1" in {
    assertResult(1) {
      Card(Faces.TWO, Suites.HEARTS).faceValue
    }
  }

  "2H 3D 5S 9C KD" should "be a valid PokerHand" in {
    assert(PokerHand.parse("2H 3D 5S 9C KD").isDefined)
  }

  "2H 3D 5S 9C" should "be an invalid PokerHand" in {
    assert(PokerHand.parse("2H 3D 5S 9C").isEmpty)
  }

  "2H 3D XX 5S 9C" should "be an invalid PokerHand" in {
    assert(PokerHand.parse("2H 3D XX 5S 9C").isEmpty)
  }

  "2H 3D 2H 5S 9C" should "be an invalid PokerHand" in {
    assert(PokerHand.parse("2H 3D 2H 5S 9C").isEmpty)
  }

  "2H 3D 5S 9C KD" should "have a valid value" in {
    assertResult((((12*14 + 8)*14 + 4)*14 + 2)*14 + 1) {
      PokerHand.parse("2H 3D 5S 9C KD").get.value
    }
  }

  "2H 3D AS 9C KD" should "have a valid value" in {
    assertResult((((13*14 + 12)*14 + 8)*14 + 2)*14 + 1) {
      PokerHand.parse("2H 3D AS 9C KD").get.value
    }
  }

  "2H 2D AS 9C KD" should "have a valid value" in {
    assertResult(540548) {
      PokerHand.parse("2H 2D AS 9C KD").get.value
    }
  }

  "2H 3D 3S 9C KD" should "have valid single pair value" in {
    assertResult(1078113) {
      PokerHand.parse("2H 3D 3S 9C KD").get.value
    }
  }

  "2H 2D 3S 3C KD" should "have a valid two pair value" in {
    assertResult(15596908) {
      PokerHand.parse("2H 2D 3S 3C KD").get.value
    }
  }

  "2H 2D 2S 3C KD" should "have a valid three of a kind value" in {
    assertResult(105413674) {
      PokerHand.parse("2H 2D 2S 3C KD").get.value
    }
  }

  "JH JD 2S JC KD" should "have a valid three of a kind value" in {
    assertResult(1054135209) {
      PokerHand.parse("JH JD 2S JC KD").get.value
    }
  }

  "KH KD 2S 3C KS" should "have a valid three of a kind value" in {
    assertResult(1264962077) {
      PokerHand.parse("KH KD 2S 3C KS").get.value
    }
  }

  "KH KD KS 3C KC" should "have a valid four of kind value" in {
    assertResult(48594782035968L) {
      PokerHand.parse("KH KD KS 3C KC").get.value
    }
  }

  "7H 8D 9S TC JD" should "have a valid straight value" in {
    assertResult(1476199584) {
      PokerHand.parse("7H 8D 9S TC JD").get.value
    }
  }

  "7H 8H 9H TH QH" should "have a valid flush value" in {
    assertResult(20661046823L) {
      PokerHand.parse("7H 8H 9H TH QH").get.value
    }
  }

  "7H 7S 7C TH TD" should "have a valid full house value" in {
    assertResult(1735527929856L) {
      PokerHand.parse("7H 7S 7C TH TD").get.value
    }
  }

  "7H 8H 9H TH JH" should "have a valid straight flush value" in {
    assertResult(793714773254182L) {
      PokerHand.parse("7H 8H 9H TH JH").get.value
    }
  }

}
