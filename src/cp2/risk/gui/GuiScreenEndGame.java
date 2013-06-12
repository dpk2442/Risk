package cp2.risk.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import cp2.risk.RiskCanvas;
import cp2.risk.game.Player;

public class GuiScreenEndGame extends GuiScreen {

	private Player winner;
	
	public GuiScreenEndGame(RiskCanvas risk, Player winner) {
		super(risk);
		
		this.drawOverlay = true;
		
		this.winner = winner;
		
		int screenWidth = (int) this.risk.getWidth();
		int screenHeight = (int) this.risk.getHeight();
		int buttonX = screenWidth/2 - 125;
		
		buttons.add(new GuiButton(0, new Point(buttonX, screenHeight/2 + 50), null, "Return to Menu", this));
		buttons.add(new GuiButton(1, new Point(buttonX, screenHeight/2 + 100), null, "Exit", this));
	}

	@Override
	public void buttonActionPerformed(GuiButton btn) {
		if (btn.getId() == 0) {
			risk.endGame(new GuiScreenMainMenu(risk));
		} else if (btn.getId() == 1) {
			System.exit(0);
		}
	}

	@Override
	public void render(Graphics screenGraphics, int mouseX, int mouseY) {
		int screenWidth = (int) this.risk.getWidth();
		int screenHeight = (int) this.risk.getHeight();
		
		Font oldFont = screenGraphics.getFont();
		Color oldColor = screenGraphics.getColor();
		
		screenGraphics.setColor(textOnOverlayColor);
		String line1 = "Congrats " + winner.name + "!";
		String line2 = "You Won!";
		
		screenGraphics.setFont(new Font(oldFont.getName(), oldFont.getStyle(), 50));
		FontMetrics fm = screenGraphics.getFontMetrics();
		screenGraphics.drawString(line1, screenWidth/2 - fm.stringWidth(line1)/2, screenHeight/4 - 50);

		screenGraphics.setFont(new Font(oldFont.getName(), oldFont.getStyle(), 40));
		fm = screenGraphics.getFontMetrics();
		screenGraphics.drawString(line2, screenWidth/2 - fm.stringWidth(line2)/2, screenHeight/4);
		
		screenGraphics.setColor(oldColor);
		screenGraphics.setFont(oldFont);
		
		this.drawButtons(screenGraphics, mouseX, mouseY);
	}

	@Override
	public void sliderDragged(GuiSlider slider) {}

}
