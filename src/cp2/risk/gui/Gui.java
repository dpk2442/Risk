package cp2.risk.gui;

import java.awt.*;

public abstract class Gui {
	
	protected final Color borderColor             = Color.black;
	protected final Color backgroundColor         = new Color(255, 255, 255, 191);
	protected final Color backgroundDisabledColor = new Color(175, 175, 175, 191);
	protected final Color backgroundHoverColor    = new Color(254, 162, 0, 191);
	protected final Color textColor               = Color.black;
	protected final Color textDisabledColor       = Color.black;
	protected final Color textHoverColor          = Color.black;
	protected final Color textOnOverlayColor      = Color.white;
	
	protected Point location;
	protected Dimension size;

	public Gui(Point location, Dimension size) {
		this.location = location;
		this.size = size;
	}

	public abstract void render(Graphics screenGraphics, int mouseX, int mouseY);

	public Image getImage(String src) {
		Image img = null;
		try {
			img = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(src));
		} catch (Exception e) {}
		return img;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

}
