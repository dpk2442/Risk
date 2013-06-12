package cp2.risk.gui;

import java.awt.*;
import java.awt.event.*;

public class GuiButton extends Gui {

	private String text;
	private GuiScreen parent;
	private int id;
	
	private boolean enabled;

	public GuiButton(int id, Point location, Dimension size, String text, GuiScreen parent) {
		super(location, size);

		this.id = id;
		
		this.enabled = true;

		this.text = text;
		this.parent = parent;

		if (size == null) {
			this.setSize(new Dimension(250, 40));
		}

		if (this.text == null) {
			this.text = "";
		}

	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public static Dimension getDefaultSize() {
		return new Dimension(250, 40);
	}

	@Override
	public void render(Graphics screenGraphics, int mouseX, int mouseY) {
		int x = (int) this.getLocation().getX();
		int width = (int) this.getSize().getWidth();
		int y = (int) this.getLocation().getY();
		int height = (int) this.getSize().getHeight();

		boolean mouseIn = (mouseX > x && mouseX < (x + width)) && (mouseY > y && mouseY < (y + height));
		
		Color oldColor = screenGraphics.getColor();
		Font oldFont = screenGraphics.getFont();

		if (!enabled) {
			screenGraphics.setColor(backgroundDisabledColor);
		} else if (mouseIn) {
			screenGraphics.setColor(backgroundHoverColor);
		} else {
			screenGraphics.setColor(backgroundColor);
		}
		screenGraphics.fillRect(x, y, width, height);
		screenGraphics.setColor(borderColor);
		screenGraphics.drawRect(x, y, width, height);
		screenGraphics.fillRect(x - 2, y - 2, 5, 5);
		screenGraphics.fillRect(x + width - 2, y - 2, 5, 5);
		screenGraphics.fillRect(x - 2, y + height - 2, 5, 5);
		screenGraphics.fillRect(x  + width - 2, y + height - 2, 5, 5);

		if (!enabled) {
			screenGraphics.setColor(textDisabledColor);
		} else if (mouseIn) {
			screenGraphics.setColor(textHoverColor);
		} else {
			screenGraphics.setColor(textColor);
		}

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

		screenGraphics.drawString(this.text, x + width/2 - fm.stringWidth(this.text)/2, y + height/2 + fm.getAscent()/2 - fm.getDescent()/2);

		screenGraphics.setFont(oldFont);
		screenGraphics.setColor(oldColor);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void clicked(MouseEvent e) {
		if (!enabled) return;
		int mouseX = e.getX();
		int mouseY = e.getY();
		int x = (int) this.getLocation().getX();
		int width = (int) this.getSize().getWidth();
		int y = (int) this.getLocation().getY();
		int height = (int) this.getSize().getHeight();
		if (mouseX > x && mouseX < (x + width)) {
			if (mouseY > y && mouseY < (y + height)) {
				parent.buttonActionPerformed(this);
			}
		}
	}

}
