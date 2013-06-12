package cp2.risk;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import cp2.risk.game.GameController;
import cp2.risk.game.World;
import cp2.risk.gui.GuiBlank;
import cp2.risk.gui.GuiScreen;
import cp2.risk.gui.GuiScreenMainMenu;
import cp2.risk.gui.InGameGui;

public class RiskCanvas extends Canvas implements MouseMotionListener, MouseListener, KeyListener {

	private World gameWorld;

	private int mouseX;
	private int mouseY;
	private int gameMouseX;
	private int gameMouseY;

	private GuiScreen currentGuiScreen;
	public InGameGui inGameGui;

	private boolean gamePlaying;

	public RiskCanvas() {
		super();

		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);

		setSize(750, 570);
		setBackground(new Color(239, 214, 141));
		setForeground(Color.BLACK);

		gameWorld = new World(this);

		showGui(new GuiScreenMainMenu(this));

		gamePlaying = false;
		inGameGui = new InGameGui(this);

		GameController.getInstance().setGameWorld(gameWorld);

	}

	public void showGui(GuiScreen gs) {
		currentGuiScreen = gs;
	}

	public void closeGui() {
		currentGuiScreen = new GuiBlank(this);
	}

	public void startGame(String[] playerNames, Color[] playerColors) {
		GameController.getInstance().gameStart(playerNames, playerColors);
		gamePlaying = true;
		closeGui();
	}

	public void endGame(GuiScreen screenToShow) {
		gamePlaying = false;
		showGui(screenToShow);
	}

	public World getWorld() {
		return gameWorld;
	}

	public boolean renderMap() {
		return gamePlaying;
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics screen) {

		if (screen instanceof Graphics2D) {
			// Retrieve the graphics context; this object is used to paint shapes
			Graphics2D g2d = (Graphics2D)screen;

			// Determine if antialiasing is enabled
			RenderingHints rhints = g2d.getRenderingHints();
			boolean antialiasOn = rhints.containsValue(RenderingHints.VALUE_ANTIALIAS_ON);

			if (antialiasOn) {
				// Enable antialiasing for shapes
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				// Enable antialiasing for text
				g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			}
		}

		Image buffer = createImage(this.getWidth(), this.getHeight());
		Graphics g = buffer.getGraphics();
		gameWorld.render(g, inGameGui, gameMouseX, gameMouseY);
		if (currentGuiScreen.drawOverlay) {
			Color oldColor = g.getColor();
			g.setColor(new Color(100, 100, 100, 220));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(oldColor);
		}
		currentGuiScreen.render(g, mouseX, mouseY);
		screen.drawImage(buffer, 0, 0, this);
	}

	public void mouseMoved(MouseEvent e) {
		currentGuiScreen.sliderDragged(e);
		mouseX = e.getX();
		mouseY = e.getY();
		
		if (currentGuiScreen.drawOverlay) return;
		
		gameMouseX = mouseX;
		gameMouseY = mouseY;

		inGameGui.sliderDragged(e);
		if (renderMap())
			gameWorld.mouseMoved(e);
	}

	public void mouseClicked(MouseEvent e) {
		currentGuiScreen.buttonClicked(e);
		if (currentGuiScreen.drawOverlay) return;

		inGameGui.buttonClicked(e);
		if (renderMap())
			gameWorld.mouseClicked(e);
	}
	public void mousePressed(MouseEvent e) {
		currentGuiScreen.sliderPressed(e);
		if (currentGuiScreen.drawOverlay) return;

		inGameGui.sliderPressed(e);
	}
	public void mouseReleased(MouseEvent e) {
		currentGuiScreen.sliderReleased(e);
		if (currentGuiScreen.drawOverlay) return;

		inGameGui.sliderReleased(e);
	}
	public void mouseDragged(MouseEvent e) {
		currentGuiScreen.sliderDragged(e);
		if (currentGuiScreen.drawOverlay) return;

		if (!currentGuiScreen.drawOverlay) { 
			inGameGui.sliderDragged(e);
		}
	}
	public void keyTyped(KeyEvent e) {
		currentGuiScreen.keyTyped(e);
		if (currentGuiScreen.drawOverlay) return;

		inGameGui.keyTyped(e);
	}
	public void keyReleased(KeyEvent e) {
		currentGuiScreen.keyReleased(e);
		if (currentGuiScreen.drawOverlay) return;

		inGameGui.keyReleased(e);
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void keyPressed(KeyEvent e) {}

}
