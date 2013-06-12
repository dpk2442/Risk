package cp2.risk.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import cp2.risk.RiskCanvas;
import cp2.risk.gui.GuiScreen;
import cp2.risk.gui.GuiScreenEndGame;
import cp2.risk.utility.Die;

public class World {

	RiskCanvas rc;
	
	ImageIcon map;
	ImageIcon cardBg;
	
	public ArrayList<Country> countries;
	
	private Country selectedCountry;
	private boolean moveFromSelected = false;
	private int countriesTaken = 0;

	public World(RiskCanvas rc) {
		
		this.rc = rc;
		
		map = new ImageIcon(RiskCanvas.class.getResource("/cp2/risk/images/Board.png"));
		cardBg = new ImageIcon(RiskCanvas.class.getResource("/cp2/risk/images/CardBackground.png"));
		
		countries = new ArrayList<Country>();
		countries.add(new Country(48, 70, 0, "Alaska"));
		countries.add(new Country(105, 72, 1, "Northwest Territory"));
		countries.add(new Country(107, 111, 2, "Alberta"));
		countries.add(new Country(108, 165, 3, "Western US"));
		countries.add(new Country(108, 212, 4, "Central America"));
		countries.add(new Country(166, 180, 5, "Eastern US"));
		countries.add(new Country(207, 117, 6, "Quebec"));
		countries.add(new Country(152, 123, 7, "Ontario"));
		countries.add(new Country(254, 45, 8, "Greenland"));
		countries.add(new Country(312, 96, 9, "Iceland"));
		countries.add(new Country(307, 161, 10, "Great Britain"));
		countries.add(new Country(308, 228, 11, "Western Europe"));
		countries.add(new Country(380, 204, 12, "Souther Europe"));
		countries.add(new Country(369, 162, 13, "Northern Europe"));
		countries.add(new Country(438, 122, 14, "Ukraine"));
		countries.add(new Country(380, 78, 15, "Scandinavia"));
		countries.add(new Country(223, 317, 16, "Brazil"));
		countries.add(new Country(165, 271, 17, "Venezuela"));
		countries.add(new Country(180, 338, 18, "Peru"));
		countries.add(new Country(189, 387, 19, "Argentina"));
		countries.add(new Country(336, 298, 20, "North Africa"));
		countries.add(new Country(394, 278, 21, "Egypt"));
		countries.add(new Country(396, 362, 22, "Congo"));
		countries.add(new Country(425, 330, 23, "East Africa"));
		countries.add(new Country(400, 425, 24, "South Africa"));
		countries.add(new Country(466, 429, 25, "Madagascar"));
		countries.add(new Country(450, 242, 26, "Middle East"));
		countries.add(new Country(533, 252, 27, "India"));
		countries.add(new Country(591, 272, 28, "Siam"));
		countries.add(new Country(575, 214, 29, "China"));
		countries.add(new Country(492, 175, 30, "Afghanistan"));
		countries.add(new Country(504, 111, 31, "Ural"));
		countries.add(new Country(544, 77, 32, "Siberia"));
		countries.add(new Country(597, 59, 33, "Yakutsk"));
		countries.add(new Country(590, 116, 34, "Irkutsk"));
		countries.add(new Country(592, 169, 35, "Mongolia"));
		countries.add(new Country(673, 174, 36, "Japan"));
		countries.add(new Country(653, 63, 37, "Kamchatka"));
		countries.add(new Country(606, 352, 38, "Indonesia"));
		countries.add(new Country(669, 339, 39, "New Guinea"));
		countries.add(new Country(632, 424, 40, "Western Australia"));
		countries.add(new Country(679, 405, 41, "Eastern Australia"));
	}
	
	public void selectCountry(Country country) {
		if (selectedCountry != null)
			selectedCountry.deselect();
		selectedCountry = country;
		selectedCountry.select();
	}
	
	public void deselectAll() {
		if (selectedCountry != null) {
			selectedCountry.deselect();
			selectedCountry = null;
		}
	}
	
	public boolean isCountrySelected() {
		return selectedCountry != null;
	}
	
	public void render(Graphics g, GuiScreen inGameGui, int mouseX, int mouseY) {
		Color oldColor = g.getColor();
		
		if (!rc.renderMap())
			return;
		
		inGameGui.render(g, mouseX, mouseY);
		
		g.drawImage(map.getImage(), 0, 0, null);
		g.drawImage(cardBg.getImage(), 15, map.getIconHeight() - 40 - cardBg.getIconHeight(), null);
		int infNum = 0;
		int calNum = 0;
		int canNum = 0;
		for (Card card : GameController.getInstance().currentPlayer().cards) {
			if (card.getType() == CardType.INFANTRY)
				infNum++;
			else if (card.getType() == CardType.CALVARY)
				calNum++;
			else if (card.getType() == CardType.CANNON)
				canNum++;
		}
		g.setColor(Color.black);
		Font oldFont = g.getFont();
		g.setFont(new Font(oldFont.getName(), oldFont.getStyle(), 40));
		g.drawString("" + infNum, 100, map.getIconHeight() - cardBg.getIconHeight() + 5);
		g.drawString("" + calNum, 100, map.getIconHeight() - cardBg.getIconHeight() + 50);
		g.drawString("" + canNum, 100, map.getIconHeight() - cardBg.getIconHeight() + 95);
		g.setFont(oldFont);
		
		for (Country country : countries) {
			country.render(g, rc);
		}
		
		if (GameController.getInstance().currentTurnState() == GameController.TurnState.PLACE ||
				(GameController.getInstance().currentTurnState() == GameController.TurnState.FORTIFY && moveFromSelected)) {
			GameController.getInstance().placeCountry.render(g, mouseX, mouseY);
		}
		
		g.setColor(oldColor);
	}
	
	public void mouseMoved(MouseEvent e) {
		for (Country country : countries) {
			country.mouseMoved(e);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		boolean hasCountryBeenClicked = false;
		for (Country country : countries) {
			if (country.mouseClicked(e))
				hasCountryBeenClicked = true;
		}
		if (!hasCountryBeenClicked)
			countryClicked(null);
	}
	
	public void countryClicked(Country clicked) {
		GameController game = GameController.getInstance();
		
		//**** NULL CHECK - clicked outside, but only if not fortifying ****//
		if (clicked == null) {
			if (game.currentTurnState() != GameController.TurnState.FORTIFY)
				deselectAll();
			return;
		}
		
		//**** FIRST SELECT ****//
		if (!isCountrySelected()) {
			//**** NOT YOUR COUNTRY ****//
			if (clicked.getOwner() != game.currentPlayer()) {
				JOptionPane.showMessageDialog(null, "You cannot select the other player's country!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			//**** YOUR COUNTRY ****//
			
			//**** CHECK IF PLACING ****//
			if (game.currentTurnState() == GameController.TurnState.PLACE) {
				clicked.numArmies++;
				game.placeCountry.numArmies--;
				if (game.placeCountry.numArmies <= 0) {
					game.setTurnState(GameController.TurnState.ATTACK);
					rc.inGameGui.enableFortifyButton(true);
				}
			} else { //**** NOT PLACING, attacking or fortifying ****//			
				selectCountry(clicked);
				if (game.currentTurnState() == GameController.TurnState.FORTIFY) {
					game.placeCountry.numArmies = clicked.numArmies - 1;
					clicked.numArmies = 1;
					moveFromSelected = true;
					if (countriesTaken > 0) {
						game.currentPlayer().cards.add(new Card());
					}
					countriesTaken = 0;
				}
			}
		//**** SECOND SELECT - TRY ATTACK or FORTIFY ****//
		} else {
			//**** FORTIFYING? ****//
			if (game.currentTurnState() == GameController.TurnState.FORTIFY) {
				if (canFortify(selectedCountry, clicked)) {
					if (selectedCountry.getOwner() == clicked.getOwner()) {
						clicked.numArmies++;
						game.placeCountry.numArmies--;
						if (game.placeCountry.numArmies <= 0) {
							moveFromSelected = false;
							game.nextPlayer();
							JOptionPane.showMessageDialog(null, "It is now " + game.currentPlayer().name + "'s turn.", "Next Turn", JOptionPane.INFORMATION_MESSAGE);
							deselectAll();
						}
					}
				}
				return;
			}
			//**** CAN ATTACK ****//
			if (canAttack(selectedCountry, clicked)) {
				
				//**** Get number of attack die ****//
				int maxAttackDie = selectedCountry.numArmies - 1;
				maxAttackDie = (maxAttackDie >= 3) ? 3 : maxAttackDie;
				//***** CAN'T ATTACK: NOT ENOUGH COUNTRIES ****//
				if (maxAttackDie < 1) {
					JOptionPane.showMessageDialog(null, "You must have at least 2 armies to attack!", "Error", JOptionPane.ERROR_MESSAGE);
					cancelAttack();
					return;
				}
				int attackDieNum = getNumFromUser(maxAttackDie, "How many dice would you like to attack with?", "How many dice?");
				if (attackDieNum == -1) { // Cancel clicked, stop attack
					cancelAttack();
					return;
				}
				
				//**** Get number of defense die ****//
				int maxDefendDie = clicked.numArmies;
				if (maxDefendDie > 2) maxDefendDie = 2;
				int defendDieNum = getNumFromUser(maxDefendDie, "How many dice would you like to defend with?", "How many dice?");
				if (defendDieNum == -1) { // Cancel clicked, stop attack
					cancelAttack();
					return;
				}
				
//				System.out.println("Attack with: " + attackDieNum);
//				System.out.println("Defend with: " + defendDieNum);
				
				//**** ROLL DIE ****//
				int[] attackDie = new int[attackDieNum];
				for (int i = 0; i < attackDieNum; i++) {
					attackDie[i] = Die.getInstance().rollDie();
				}
				int[] defendDie = new int[defendDieNum];
				for (int i = 0; i < defendDieNum; i++) {
					defendDie[i] = Die.getInstance().rollDie();
				}
				
				//**** SORT DIE (ascending) ****//
				Arrays.sort(attackDie);
				Arrays.sort(defendDie);
				
//				System.out.println("Attack: " + Arrays.toString(attackDie));
//				System.out.println("Defend: " + Arrays.toString(defendDie));
				
				//**** How many die should we compare? ****//
				int dieToCompare = 0;
				switch (attackDieNum) {
				case 3:
				case 2:
					switch (defendDieNum) {
					case 1:
						dieToCompare = 1;
						break;
					case 2:
						dieToCompare = 2;
						break;
					}
					break;
				case 1:
					dieToCompare = 1;
					break;
				}
				
				//**** COMPARE DIE ****//
				int attackerIndex = attackDieNum - 1;
				int defenderIndex = defendDieNum - 1;
				for (int i = 0; i < dieToCompare; i++) {
					if (attackDie[attackerIndex] > defendDie[defenderIndex]) {
						clicked.numArmies--; // Attacker wins, take one from defender
					} else if (attackDie[attackerIndex] < defendDie[defenderIndex]) {
						selectedCountry.numArmies--; // Defender wins, take one from attacker
					} else { // Tie, defender wins
						selectedCountry.numArmies--;
					}
					attackerIndex--;
					defenderIndex--;
				}
				
				boolean attackerWon = false;
				//**** CALCULATE ATTACKER WIN ****//
				if (clicked.numArmies <= 0) {
					int moveNum = -1;
					while (moveNum == -1)
						moveNum = getNumFromUser(selectedCountry.numArmies - 1, "How many armies would you like to move?", "Move how many?");
					clicked.getOwner().countries.remove(clicked);
					selectedCountry.getOwner().countries.add(clicked);
					selectedCountry.numArmies = selectedCountry.numArmies - moveNum;
					clicked.numArmies = moveNum;
					countriesTaken++;
					attackerWon = selectedCountry.getOwner().countries.size() == this.countries.size();
				}
				
				if (attackerWon) {
					rc.showGui(new GuiScreenEndGame(rc, selectedCountry.getOwner()));
				}
				
			}
			//**** FAILED ****//
			else {
				if (clicked != selectedCountry) JOptionPane.showMessageDialog(null, "Attack Failed!");
			}
			
			deselectAll();
		}
	}
	
	private int getNumFromUser(int maxDie, String message, String title) {
		Integer[] options = new Integer[maxDie];
		for (int i = 0; i < options.length; i++) {
			options[i] = new Integer(i+1);
		}
		Object attackSelection = JOptionPane.showInputDialog(
				null,
				message,
				title,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options, 
				options[options.length - 1]);
		if (!(attackSelection instanceof Integer)) { // If cancel clicked
			return -1;
		}
		return (Integer) attackSelection;
	}
	
	private void cancelAttack() {
		deselectAll();
	}
	
	public boolean canAttack(Country attacker, Country defender) {
		if (attacker.getOwner() == defender.getOwner()) return false;
		if (!MoveChecker.isLegalMove(attacker.getId(), defender.getId())) return false;
		return true;
	}
	
	public boolean canFortify(Country first, Country second) {
		if (first.getOwner() != second.getOwner()) return false;
		if (!MoveChecker.isLegalMove(first.getId(), second.getId()) && first != second) return false;
		return true;
	}

}
