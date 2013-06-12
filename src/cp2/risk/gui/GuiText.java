package cp2.risk.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import cp2.risk.gui.Gui;

public class GuiText extends Gui {

	private String text;
	private boolean centered;

	public GuiText(Point location, Dimension size, String text, boolean centered) {
		super(location, size);

		this.text = text;
		this.centered = centered;

		if (size == null) {
			this.setSize(new Dimension(250, 40));
		}

		if (this.text == null) {
			this.text = "";
		}
	}
	
	public static Dimension getDefaultBoxSize() {
		return new Dimension(250, 40);
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void render(Graphics screenGraphics, int mouseX, int mouseY) {
		Font oldFont = screenGraphics.getFont();
		Color oldColor = screenGraphics.getColor();

		screenGraphics.setColor(textColor);

		int x = (int) this.getLocation().getX();
		int width = (int) this.getSize().getWidth();
		int y = (int) this.getLocation().getY();
		int height = (int) this.getSize().getHeight();

		int fontPointSize = 20;
		screenGraphics.setFont(new Font(oldFont.getName(), oldFont.getStyle(), fontPointSize));
		FontMetrics fm = screenGraphics.getFontMetrics();

		while (true) {
			if (fm.stringWidth(this.text) < width) break;
			fontPointSize--;
			screenGraphics.setFont(new Font(oldFont.getName(), oldFont.getStyle(), fontPointSize));
			fm = screenGraphics.getFontMetrics();
			if (fontPointSize < 5) break;
		}

		if (centered) {
			screenGraphics.drawString(this.text, x + width/2 - fm.stringWidth(this.text)/2, y + height/2 + fm.getAscent()/2 - fm.getDescent()/2);
		} else {
			screenGraphics.drawString(this.text, x, y + height/2 + fm.getAscent()/2 - fm.getDescent()/2);
		}

		screenGraphics.setColor(oldColor);
		screenGraphics.setFont(oldFont);
	}

}
