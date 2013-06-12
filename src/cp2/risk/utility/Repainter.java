package cp2.risk.utility;

import java.awt.Canvas;

public class Repainter extends Thread {
	
	private Canvas repaintCanvas;
	private long delay;
	
	public Repainter(Canvas repaintCanvas, long delay) {
		this.repaintCanvas = repaintCanvas;
		this.delay = delay;
	}
	
	public void run() {
		while (true) {
			repaintCanvas.repaint();
			repaintCanvas.requestFocus();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
