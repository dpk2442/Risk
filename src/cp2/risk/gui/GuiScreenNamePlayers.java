package cp2.risk.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import cp2.risk.RiskCanvas;

public class GuiScreenNamePlayers extends GuiScreen {

	private Image logo;

	private int numPlayers;
	
	ArrayList<GuiColorPicker> colorPickers;
	
	public GuiScreenNamePlayers(RiskCanvas risk, int numPlayers) {
		super(risk);
		
		this.numPlayers = numPlayers;
		
		colorPickers = new ArrayList<GuiColorPicker>();

		logo = getImage("/cp2/risk/images/Risk-logo.png");

		int screenWidth = this.risk.getWidth();
		int screenHeight = this.risk.getHeight();

		buttons.add(new GuiButton(0, new Point(screenWidth/2 - 10 - GuiButton.getDefaultSize().width, screenHeight/2 + 175), null, "Start Game", this));
		buttons.add(new GuiButton(2, new Point(screenWidth/2 + 10, screenHeight/2 + 175), null, "Cancel", this));
		
		int[] playerOffsets = {
			150,
			100,
			50,
			0,
			-50,
			-100
		};
		
		if (numPlayers == 1) {
			playerOffsets[0] = 50;
		} else if (numPlayers == 2) {
			playerOffsets[0] = 50;
			playerOffsets[1] = 0;
		} else if (numPlayers == 3) {
			playerOffsets[0] = 50;
			playerOffsets[1] = 0;
			playerOffsets[2] = -50;
		} else if (numPlayers == 4) {
			playerOffsets[0] = 100;
			playerOffsets[1] = 50;
			playerOffsets[2] = 0;
			playerOffsets[3] = -50;
		} else if (numPlayers == 5) {
			playerOffsets[0] = 150;
			playerOffsets[1] = 100;
			playerOffsets[2] = 50;
			playerOffsets[3] = 0;
			playerOffsets[4] = -50;
		}
			
		
		if (numPlayers > 0) createPlayerInput(playerOffsets[0], 1, Color.blue);
		if (numPlayers > 1) createPlayerInput(playerOffsets[1], 2, Color.red);
		if (numPlayers > 2) createPlayerInput(playerOffsets[2], 3, Color.green);
		if (numPlayers > 3) createPlayerInput(playerOffsets[3], 4, Color.orange);
		if (numPlayers > 4) createPlayerInput(playerOffsets[4], 5, Color.cyan);
		if (numPlayers > 5) createPlayerInput(playerOffsets[5], 6, Color.magenta);

		//		JColorChooser.showDialog(null, "Choose a color", Color.blue);
	}

	private void createPlayerInput(int heightOffset, int playerNum, Color defaultColor) {
		int screenHeight = this.risk.getHeight();
		
		int playerTextX = buttons.get(0).getLocation().x - 10;
		texts.add(new GuiText(new Point(playerTextX, screenHeight/2 - heightOffset), null, "Player " + playerNum + " Name:", false));
		int playerTextFieldX = (buttons.get(1).getLocation().x + buttons.get(1).getSize().width) - GuiTextField.getDefaultSize().width - 25;
		textFields.add(new GuiTextField(new Point(playerTextFieldX, screenHeight/2 - heightOffset), null, "Player " + playerNum));
		colorPickers.add(new GuiColorPicker(new Point(playerTextFieldX + GuiTextField.getDefaultSize().width + 10, screenHeight/2 - heightOffset), null, defaultColor, this));
	}

	@Override
	public void render(Graphics screenGraphics, int mouseX, int mouseY) {
		screenGraphics.drawImage(logo, (risk.getWidth() - logo.getWidth(null))/2, risk.getHeight()/2 - 250, null);
		this.drawButtons(screenGraphics, mouseX, mouseY);
		this.drawTextFields(screenGraphics, mouseX, mouseY);
		this.drawTexts(screenGraphics, mouseX, mouseY);
		for (GuiColorPicker colorPicker : colorPickers)
			colorPicker.render(screenGraphics, mouseX, mouseY);
	}

	@Override
	public void buttonActionPerformed(GuiButton btn) {
		if (btn.getId() == 0) {
			String[] playerNames = new String[numPlayers];
			for (int i=0;i<playerNames.length;i++) {
				playerNames[i] = textFields.get(i).getText();
			}
			Color[] playerColors = new Color[numPlayers];
			for (int i=0;i<playerColors.length;i++) {
				playerColors[i] = colorPickers.get(i).getColor();
			}
			risk.startGame(playerNames, playerColors);
		} else if (btn.getId() == 2) {
			risk.showGui(new GuiScreenMainMenu(risk));
		}
	}
	
	@Override
	public void buttonClicked(MouseEvent e) {
		super.buttonClicked(e);
		for (GuiColorPicker colorPicker : colorPickers)
			colorPicker.mouseClicked(e);
	}

	@Override
	public void sliderDragged(GuiSlider slider) {}

}
