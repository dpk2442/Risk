package cp2.risk.game;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class PlaceCountry {

	private int width, height;
	public int numArmies;
	private Color color;

	public PlaceCountry() {
		this.width = 20;
		this.height = 20;
		this.numArmies = 0;
		this.color = new Color(160, 160, 160);
	}

	public void render(Graphics g, int mouseX, int mouseY) {
		Color oldColor = g.getColor();
		
		int x = mouseX + 10;
		int y = mouseY + 10;
		
		g.setColor(color);
		g.fillOval(x, y, width, height);
		g.setColor(Color.black);
		FontMetrics fm = g.getFontMetrics();
		String num = new Integer(numArmies).toString();
		int strWidth = fm.stringWidth(num);
		g.drawString(num, x + width/2 - strWidth/2, y + height/2 + fm.getHeight()/2 - fm.getDescent());
		
		g.setColor(Color.black);
		g.drawOval(x, y, width, height);

		g.setColor(oldColor);
	}

}
