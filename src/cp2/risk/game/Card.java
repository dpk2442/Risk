package cp2.risk.game;

import java.util.Random;

public class Card {
	
	private static Random rand = new Random();
	
	private CardType type = CardType.NONE;
	
	public Card() {
		switch (rand.nextInt(2)) {
		default:
		case 0:
			type = CardType.INFANTRY;
			break;
		case 1:
			type = CardType.CALVARY;
			break;
		case 2:
			type = CardType.CANNON;
			break;
		}
	}
	
	public Card(CardType type) {
		this.type = type;
	}
	
	public void setType(CardType num) {
		type = num;
	}
	
	public CardType getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Card)) return false;
		Card c = (Card) o;
		return c.getType() == this.getType();
	}
	
}
