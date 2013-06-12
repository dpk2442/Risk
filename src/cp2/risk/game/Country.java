package cp2.risk.game;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import cp2.risk.RiskCanvas;

public class Country {

	private int x, y, width, height, id;
	private boolean mouseIn = false;
	private boolean selected = false;
	public int numArmies;

	public Country(int x, int y, int id, String name) {
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 20;
		this.id = id;
		this.numArmies = 0;
	}

	public boolean isSelected() {
		return selected;
	}

	public void select() {
		this.selected = true;
	}
	
	public void deselect() {
		this.selected = false;
	}
	
	public int getId() {
		return id;
	}

	public void render(Graphics g, RiskCanvas screen) {
		Color oldColor = g.getColor();

		if (selected) g.setColor(Color.black);
		else if (mouseIn) g.setColor(Color.white);
		else g.setColor(new Color(0, 0, 0, 0));
		g.fillOval(x - 5, y - 5, width + 10, height + 10);
		
		g.setColor(getOwner().color);
		g.fillOval(x, y, width, height);
		g.setColor(getTextColor());
		FontMetrics fm = g.getFontMetrics();
		String num = new Integer(numArmies).toString();
		int strWidth = fm.stringWidth(num);
		g.drawString(num, x + width/2 - strWidth/2, y + height/2 + fm.getHeight()/2 - fm.getDescent());

		g.setColor(oldColor);
	}

	public void mouseMoved(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		if (mouseX >= x && mouseX <= (x + width)) {
			if (mouseY >= y && mouseY <= (y + height)) {
				mouseIn = true;
				return;
			}
		}
		mouseIn = false;
	}
	
	public Color getTextColor() {
		int bg = getOwner().color.getRGB();
		if (bg > Color.black.getRGB()/2) return Color.black;
		else return Color.white;
	}

	public boolean mouseClicked(MouseEvent e) {
		if (mouseIn) {
			GameController.getInstance().getGameWorld().countryClicked(this);
		}
		return mouseIn;
	}
	
	public Player getOwner() {
		Player[] players = GameController.getInstance().players;
		for (int i = 0; i < players.length; i++) {
			if (players[i].countries.contains(this))
				return players[i];
		}
		return new Player("Unowned country.", Color.pink);
	}

}
