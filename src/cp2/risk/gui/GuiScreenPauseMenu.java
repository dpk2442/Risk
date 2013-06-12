package cp2.risk.gui;

import java.awt.*;

import cp2.risk.RiskCanvas;

public class GuiScreenPauseMenu extends GuiScreen {

	public GuiScreenPauseMenu(RiskCanvas risk) {
		super(risk);
		
		drawOverlay = true;

		int numButtons = 4;
		int screenWidth = (int) risk.getWidth();
		int screenHeight = (int) risk.getHeight();
		int buttonX = screenWidth/2 - 125;
		int firstButtonY = screenHeight/2 - (numButtons * 50) / 2;

		buttons.add(new GuiButton(0, new Point(buttonX, firstButtonY), null, "Options", this));
		buttons.add(new GuiButton(1, new Point(buttonX, firstButtonY + 100), null, "Return to game", this));
		buttons.add(new GuiButton(2, new Point(buttonX, firstButtonY + 150), null, "Quit to Menu", this));
	}
	
	@Override
	public void render(Graphics screenGraphics, int mouseX, int mouseY) {
		this.drawButtons(screenGraphics, mouseX, mouseY);
	}

	@Override
	public void buttonActionPerformed(GuiButton btn) {
		if (btn.getId() == 0) {
//			risk.showMenu(new GuiScreenOptions(risk));
		} else if (btn.getId() == 1) {
//			risk.setPaused(false);
		} else if (btn.getId() == 2) {
//			risk.endGame();
//			risk.showMenu(new GuiScreenMainMenu(risk));
		}
	}

	@Override
	public void sliderDragged(GuiSlider slider) {}

}
