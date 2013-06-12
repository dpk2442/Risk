package cp2.risk.gui;

import java.awt.*;

import javax.swing.JOptionPane;

import cp2.risk.RiskCanvas;

public class GuiScreenMainMenu extends GuiScreen {
	
	private Image bg;

	public GuiScreenMainMenu(RiskCanvas risk) {
		super(risk);
		
		bg = getImage("/cp2/risk/images/Background.png");

		int screenWidth = this.risk.getWidth();
		int screenHeight = this.risk.getHeight();

		buttons.add(new GuiButton(0, new Point(screenWidth/2 - 10 - GuiButton.getDefaultSize().width, screenHeight/2 + 150), null, "Name Your Players", this));
		buttons.add(new GuiButton(2, new Point(screenWidth/2 + 10, screenHeight/2 + 150), null, "Exit", this));
	}

	@Override
	public void render(Graphics screenGraphics, int mouseX, int mouseY) {
		screenGraphics.drawImage(bg, 0, 0, null);
		this.drawButtons(screenGraphics, mouseX, mouseY);
		this.drawTextFields(screenGraphics, mouseX, mouseY);
		this.drawTexts(screenGraphics, mouseX, mouseY);
	}

	@Override
	public void buttonActionPerformed(GuiButton btn) {
		if (btn.getId() == 0) {
			int numPlayers = getNumUsers(6, "How many players would you like to have?", "How many players?");
			if (numPlayers == -1) return;
			risk.showGui(new GuiScreenNamePlayers(risk, numPlayers));
		} else if (btn.getId() == 2) {
			System.exit(0);
		}
	}

	private int getNumUsers(int max, String message, String title) {
		Integer[] options = new Integer[max-1];
		for (int i = 0; i < options.length; i++) {
			options[i] = new Integer(i+2);
		}
		Object selection = JOptionPane.showInputDialog(
				null,
				message,
				title,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options, 
				options[0]);
		if (!(selection instanceof Integer)) { // If cancel clicked
			return -1;
		}
		return (Integer) selection;
	}

	@Override
	public void sliderDragged(GuiSlider slider) {}

}
