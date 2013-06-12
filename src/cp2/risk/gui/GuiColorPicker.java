package cp2.risk.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;

public class GuiColorPicker extends Gui {

	private Color color;

	public GuiColorPicker(Point location, Dimension size, Color color, GuiScreen parent) {
		super(location, size);

		this.color = color;

		if (size == null) {
			this.setSize(new Dimension(40, 40));
		}

	}
	
	public Color getColor() {
		return color;
	}
	
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		if (mouseX > location.x && mouseX < location.x + size.width) {
			if (mouseY > location.y && mouseY < location.y + size.height) {
				Color selectedColor = JColorChooser.showDialog(null, "Please select the team color.", color);
				if (selectedColor != null) {
					color = selectedColor;
				}
			}
		}
	}

	@Override
	public void render(Graphics screenGraphics, int mouseX, int mouseY) {
		Color oldColor = screenGraphics.getColor();
		screenGraphics.setColor(color);
		int xOff = 0, yOff = 0, wOff = 0, hOff = 0;
		if (mouseX > location.x && mouseX < location.x + size.width) {
			if (mouseY > location.y && mouseY < location.y + size.height) {
				xOff = -3;
				yOff = -3;
				wOff = 6;
				hOff = 6;
				
			}
		}
		screenGraphics.fillRect(location.x + xOff, location.y + yOff, size.width + wOff, size.height + hOff);
		screenGraphics.setColor(oldColor);
	}

}
