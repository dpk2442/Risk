package cp2.risk.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GuiTextField extends Gui {

	String myText;

	private boolean inFocus;
	private boolean focusCaret;
	private int focusCaretCounter;

	public GuiTextField(Point location, Dimension size, String text) {
		super(location, size);

		if (size == null) {
			this.setSize(new Dimension(350, 40));
		}

		myText = (text == null) ? "" : text;
		inFocus = false;
		focusCaret = false;
		focusCaretCounter = 0;
	}

	public static Dimension getDefaultSize() {
		return new Dimension(350, 40);
	}


	public void render(Graphics screenGraphics, int mouseX, int mouseY) {
		Color oldColor = screenGraphics.getColor();

		if (inFocus) {
			focusCaretCounter++;
			if (focusCaretCounter >= 13) {
				focusCaret = !focusCaret;
				focusCaretCounter = 0;
			}
		}

		if (inFocus) {
			screenGraphics.setColor(backgroundHoverColor);
		} else {
			screenGraphics.setColor(backgroundColor);
		}
		screenGraphics.fillRect(location.x, location.y, size.width, size.height);
		screenGraphics.setColor(borderColor);
		screenGraphics.drawRect(location.x, location.y, size.width, size.height);
		if (inFocus) {
			screenGraphics.setColor(textHoverColor);
		} else {
			screenGraphics.setColor(textColor);
		}
		String drawString = myText + ((inFocus && focusCaret) ? "_" : "");


		int x = (int) this.getLocation().getX();
		int width = (int) this.getSize().getWidth();
		int y = (int) this.getLocation().getY();
		int height = (int) this.getSize().getHeight();
		Font oldFont = screenGraphics.getFont();
		int fontPointSize = 20;
		screenGraphics.setFont(new Font(oldFont.getName(), oldFont.getStyle(), fontPointSize));
		FontMetrics fm = screenGraphics.getFontMetrics();

		while (true) {
			if (fm.stringWidth(drawString) < width) break;
			fontPointSize--;
			screenGraphics.setFont(new Font(oldFont.getName(), oldFont.getStyle(), fontPointSize));
			fm = screenGraphics.getFontMetrics();
			if (fontPointSize < 5) break;
		}

		screenGraphics.drawString(drawString, x + 10, y + height/2 + fm.getAscent()/2 - fm.getDescent()/2);


		screenGraphics.setColor(oldColor);
	}

	public String getText() {
		return myText;
	}

	public void setText(String string) {
		myText = string;
	}
	
	public void focus() {
		inFocus = true;
	}
	
	public void blur() {
		inFocus = false;
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (x >= location.x && x <= (location.x + size.width)) {
			if (y >= location.y && y < (location.y + size.height)) {
				inFocus = true;
				return;
			}
		}
		inFocus = false;
	}

	public void keyTyped(KeyEvent e) {
		if (inFocus) {
			char keyChar = e.getKeyChar();
			if (keyChar == '\b') {
				if (myText.length() > 0) {
					myText = myText.substring(0, myText.length() - 1);
				}
			} else if (keyChar == '\n') {
				// enter pressed
			} else {
				myText += keyChar;
			}
		}
	}

	public void keyReleased(KeyEvent e) {}

}
