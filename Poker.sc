import com.iks.dojo.pokerHand.Deck

val deck = new Deck
deck.cards.values.toSeq.sorted.foreach(println)