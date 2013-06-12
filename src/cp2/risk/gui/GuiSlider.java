package cp2.risk.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class GuiSlider extends Gui {

	private int id;
	private String text;
	private String drawText;
	private GuiScreen parent;

	private int sliderX;
	private boolean dragging = false;

	public GuiSlider(int id, Point location, Dimension size, String text, GuiScreen parent) {
		super(location, size);

		this.id = id;

		this.text = text;
		this.parent = parent;

		if (size == null) {
			this.setSize(new Dimension(250, 40));
		}

		if (this.text == null) {
			this.text = "";
		}

		this.updateText();

		sliderX = (int) this.getLocation().getX();
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

		screenGraphics.setColor(backgroundColor);
		screenGraphics.fillRect(x, y, width, height);
		screenGraphics.setColor(borderColor);
		screenGraphics.drawRect(x, y, width, height);
		screenGraphics.fillRect(x - 2, y - 2, 5, 5);
		screenGraphics.fillRect(x + width - 2, y - 2, 5, 5);
		screenGraphics.fillRect(x - 2, y + height - 2, 5, 5);
		screenGraphics.fillRect(x  + width - 2, y + height - 2, 5, 5);

		screenGraphics.fillRect(sliderX, y, 20, (int) this.getSize().getHeight());

		if (mouseIn) {
			screenGraphics.setColor(Color.white);
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

		
		screenGraphics.drawString(this.drawText, x + width/2 - fm.stringWidth(this.drawText)/2, y + height/2 + fm.getAscent()/2 - fm.getDescent()/2);

		screenGraphics.setFont(oldFont);
		screenGraphics.setColor(oldColor);
	}

	public double getValue() {
		double x = this.getLocation().getX();
		double width = this.getSize().getWidth();
		double value = (sliderX - x) / (width - 20);
		value = (value < 0) ? 0 : (value > 1) ? 1 : value;
		return value;
	}
	
	public void setValue(double value) {
		value = (value < 0) ? 0 : (value > 1) ? 1 : value;
		double x = this.getLocation().getX();
		double width = this.getSize().getWidth();
		sliderX = (int) ((value * (width - 20)) + x);
		this.updateText();
	}

	public void updateText() {
		this.drawText = this.text;
		if (this.drawText.contains("<value>")) {
			int percentage = (int) (getValue() * 100);
			String valueString = (percentage == 0) ? "Off" : percentage + "%";
			this.drawText = this.drawText.replaceAll("<value>", valueString);
		}
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

	public void pressed(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		int x = (int) this.getLocation().getX();
		int width = (int) this.getSize().getWidth();
		int y = (int) this.getLocation().getY();
		int height = (int) this.getSize().getHeight();
		if (mouseX > x && mouseX < (x + width)) {
			if (mouseY > y && mouseY < (y + height)) {
				dragging = true;
			}
		}
	}

	public void released(MouseEvent e) {
		if (dragging) {
			parent.sliderDragged(this);
		}
		dragging = false;
	}

	public void dragged(MouseEvent e) {
		if (dragging) {
			int mouseX = e.getX();
			int mouseY = e.getY();
			int x = (int) this.getLocation().getX();
			int width = (int) this.getSize().getWidth();
			int y = (int) this.getLocation().getY();
			int height = (int) this.getSize().getHeight();
			if (mouseX > x && mouseX < (x + width)) {
				if (mouseY > y && mouseY < (y + height)) {
					sliderX = e.getX() - 2 - 10;
					if (sliderX < x) sliderX = x;
					if (sliderX + 20 > x + width) sliderX = x + width - 20;
				}
			} else {
				if (mouseX < x) sliderX = x;
				if (mouseX > x + width) sliderX = x + width - 20;
			}
			this.updateText();
		}
	}

}
