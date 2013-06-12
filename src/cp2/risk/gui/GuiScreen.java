package cp2.risk.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import cp2.risk.RiskCanvas;

public abstract class GuiScreen extends Gui {
	
	RiskCanvas risk;
	protected ArrayList<GuiButton> buttons;
	protected ArrayList<GuiSlider> sliders;
	protected ArrayList<GuiTextField> textFields;
	protected ArrayList<GuiText> texts;
	
	public boolean drawOverlay = false;
	
	public GuiScreen(RiskCanvas risk) {
		super(new Point(0, 0), null);
		this.setSize(risk.getSize());
		
		this.risk = risk;
		this.buttons = new ArrayList<GuiButton>();
		this.sliders = new ArrayList<GuiSlider>();
		this.textFields = new ArrayList<GuiTextField>();
		this.texts = new ArrayList<GuiText>();
	}
	
	public void drawButtons(Graphics screenGraphics, int mouseX, int mouseY) {
		if (buttons.size() != 0) {
			// for each GuiButton button in buttons
			for (GuiButton button : buttons) {
				button.render(screenGraphics, mouseX, mouseY);
			}
		}
	}
	
	public void drawSliders(Graphics screenGraphics, int mouseX, int mouseY) {
		if (sliders.size() != 0) {
			// for each GuiSlider slider in sliders
			for (GuiSlider slider : sliders) {
				slider.render(screenGraphics, mouseX, mouseY);
			}
		}
	}
	
	public void drawTextFields(Graphics screenGraphics, int mouseX, int mouseY) {
		if (textFields.size() != 0) {
			for (GuiTextField textField : textFields) {
				textField.render(screenGraphics, mouseX, mouseY);
			}
		}
	}
	
	public void drawTexts(Graphics screenGraphics, int mouseX, int mouseY) {
		if (texts.size() != 0) {
			for (GuiText text : texts) {
				text.render(screenGraphics, mouseX, mouseY);
			}
		}
	}

	public void buttonClicked(MouseEvent e) {
		if (buttons.size() != 0) {
			// for each GuiButton button in buttons
			for (GuiButton button : buttons) {
				button.clicked(e);
			}
		}
		if (textFields.size() != 0) {
			for (GuiTextField textField : textFields) {
				textField.mouseClicked(e);
			}
		}
		
	}
	
	public void sliderPressed(MouseEvent e) {
		if (sliders.size() != 0) {
			// for each GuiSlider slider in sliders
			for (GuiSlider slider : sliders) {
				slider.pressed(e);
			}
		}
	}
	
	public void sliderReleased(MouseEvent e) {
		if (sliders.size() != 0) {
			// for each GuiSlider slider in sliders
			for (GuiSlider slider : sliders) {
				slider.released(e);
			}
		}
	}
	
	public void sliderDragged(MouseEvent e) {
		if (sliders.size() != 0) {
			// for each GuiSlider slider in sliders
			for (GuiSlider slider : sliders) {
				slider.dragged(e);
			}
		}
	}
	
	public void keyTyped(KeyEvent e) {
		if (textFields.size() != 0) {
			for (GuiTextField textField : textFields) {
				textField.keyTyped(e);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (textFields.size() != 0) {
			for (GuiTextField textField : textFields) {
				textField.keyReleased(e);
			}
		}
	}
	
	public abstract void buttonActionPerformed(GuiButton btn);
	
	public abstract void sliderDragged(GuiSlider slider);

}
