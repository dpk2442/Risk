package cp2.risk.game;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	
	public String name;
	public ArrayList<Country> countries;
	public Color color;
	public ArrayList<Card> cards;
	
	public Player(String name, Color color) {
		this.name = name;
		this.countries = new ArrayList<Country>();
		this.color = color;
		this.cards = new ArrayList<Card>();
	}

}
