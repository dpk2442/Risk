package cp2.risk.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JOptionPane;

import cp2.risk.RiskCanvas;
import cp2.risk.game.GameController;

public class InGameGui extends GuiScreen {
	
	public InGameGui(RiskCanvas risk) {
		super(risk);
		
		buttons.add(new GuiButton(0, new Point(risk.getWidth() - GuiButton.getDefaultSize().width + 40, risk.getHeight() - GuiButton.getDefaultSize().height - 5), new Dimension(GuiButton.getDefaultSize().width - 50, GuiButton.getDefaultSize().height), "Quit to Main Menu", this));
		buttons.add(new GuiButton(1, new Point(risk.getWidth() - GuiButton.getDefaultSize().width*2 + 80, risk.getHeight() - GuiButton.getDefaultSize().height - 5), new Dimension(GuiButton.getDefaultSize().width - 50, GuiButton.getDefaultSize().height), "Fortify and End Turn", this));
		enableFortifyButton(false);
		
		texts.add(new GuiText(new Point(40, risk.getHeight() - GuiText.getDefaultBoxSize().height), null, "Current Player", false));
	}
	
	public void render(Graphics screenGraphics, int mouseX, int mouseY) {
		GameController gameControl = GameController.getInstance();
		
		drawButtons(screenGraphics, mouseX, mouseY);
		
		Color beforePlayer = screenGraphics.getColor();
		screenGraphics.setColor(gameControl.currentPlayer().color);
		screenGraphics.fillRect(10, risk.getHeight() - 30, 20, 20);
		screenGraphics.setColor(beforePlayer);
		
		String action = (gameControl.currentTurnState() == GameController.TurnState.PLACE) ? ": Placing armies." :
			(gameControl.currentTurnState() == GameController.TurnState.ATTACK) ? ": Attacking." :
				(gameControl.currentTurnState() == GameController.TurnState.CARDS) ? ": Choosing cards." :
					(gameControl.currentTurnState() == GameController.TurnState.FORTIFY) ? ": Fortifying." : ".";
		texts.get(0).setText(gameControl.currentPlayer().name + "'s turn" + action);
		drawTexts(screenGraphics, mouseX, mouseY);
	}
	
	public void enableFortifyButton(boolean enabled) {
		buttons.get(1).setEnabled(enabled);
	}
	
	public void buttonActionPerformed(GuiButton btn) {
		if (btn.getId() == 0) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				risk.endGame(new GuiScreenMainMenu(risk));
		} else if (btn.getId() == 1) {
			GameController.getInstance().setTurnState(GameController.TurnState.FORTIFY);
			btn.setEnabled(false);
		}
	}
	
	public void sliderDragged(GuiSlider slider) {}

}
