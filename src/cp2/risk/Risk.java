package cp2.risk;

import java.awt.Toolkit;

import javax.swing.JFrame;

import cp2.risk.utility.Repainter;

public class Risk extends JFrame {
	
	RiskCanvas riskCanvas;
	
	public Risk() {
		super("Risk: CP2 Style");
		
		riskCanvas = new RiskCanvas();
		
		add(riskCanvas);
		new Repainter(riskCanvas, 15).start();

		setResizable(false);
		pack();
		
		int centerX = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
		int centerY = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
		setLocation(centerX, centerY);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Risk();
	}

}
