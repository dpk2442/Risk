package cp2.risk.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class GameController {

	private static final GameController instance = new GameController();
	public static GameController getInstance() {
		return instance;
	}

	private World gameWorld;

	public Player[] players;
	private int curPlayerIndex = 0;

	public static enum TurnState {
		PLACE, CARDS, ATTACK, FORTIFY
	}
	private TurnState currentTurnState = TurnState.PLACE;
	private int cardValueIndex = 0;

	public PlaceCountry placeCountry = new PlaceCountry();

	private ArrayList<Country> shuffledCountries;

	private Random rand;

	private GameController() {
		rand = new Random();
	}

	public void setGameWorld(World gameWorld) {
		this.gameWorld = gameWorld;
	}

	public World getGameWorld() {
		return gameWorld;
	}

	public void gameStart(String[] playerNames, Color[] playerColors) {
		if (gameWorld == null) return;

		shuffledCountries = gameWorld.countries;

		players = new Player[playerNames.length];
		for (int i = 0; i < playerNames.length; i++) {
			players[i] = new Player(playerNames[i], playerColors[i]);
		}

		shuffleCountries(500);
		distributeCountries();

		//		players[0].countries.add(shuffledCountries.get(0));
		//		for (Country country : shuffledCountries)
		//			players[1].countries.add(country);
		//		players[1].countries.remove(shuffledCountries.get(0));
		//		shuffledCountries.get(0).numArmies = 1;

		int armiesPerPlayer = 0;
		switch (players.length) {
		case 2:
			armiesPerPlayer = 40;
			break;
		case 3:
			armiesPerPlayer = 35;
			break;
		case 4:
			armiesPerPlayer = 30;
			break;
		case 5:
			armiesPerPlayer = 25;
			break;
		case 6:
			armiesPerPlayer = 20;
			break;
		}

		for (int i = 0; i < players.length; i++) {
			int availableArmies = armiesPerPlayer;
			for (int j = 0; j < players[i].countries.size(); j++) {
				players[i].countries.get(j).numArmies++;
				availableArmies--;
			}
			while (availableArmies > 0) {
				players[i].countries.get(rand.nextInt(players[i].countries.size())).numArmies++;
				availableArmies--;
			}
		}

		cardValueIndex = 0;
		curPlayerIndex = -1;
		nextPlayer();

	}

	public TurnState currentTurnState() {
		return currentTurnState;
	}

	public void setTurnState(TurnState state) {
		currentTurnState = state;
	}

	public Player currentPlayer() {
		return players[curPlayerIndex];
	}

	public void nextPlayer() {
		curPlayerIndex++;
		if (curPlayerIndex >= players.length)
			curPlayerIndex = 0;
		Player curPlayer = players[curPlayerIndex];
		int cardArmies = 0;
		if (curPlayer.cards.size() >= 3) {
			int infNum = 0;
			int calNum = 0;
			int canNum = 0;
			for (Card card : curPlayer.cards) {
				if (card.getType() == CardType.INFANTRY)
					infNum++;
				else if (card.getType() == CardType.CALVARY)
					calNum++;
				else if (card.getType() == CardType.CANNON)
					canNum++;
			}
			boolean canCancel = curPlayer.cards.size() < 5;
			if (infNum >= 3) {
				cardArmies = promptPlayerToUseCards(canCancel);
				curPlayer.cards.remove(new Card(CardType.INFANTRY));
				curPlayer.cards.remove(new Card(CardType.INFANTRY));
				curPlayer.cards.remove(new Card(CardType.INFANTRY));
			}
			else if (calNum >= 3) {
				cardArmies = promptPlayerToUseCards(canCancel);
				curPlayer.cards.remove(new Card(CardType.CALVARY));
				curPlayer.cards.remove(new Card(CardType.CALVARY));
				curPlayer.cards.remove(new Card(CardType.CALVARY));
			}
			else if (canNum >= 3) {
				cardArmies = promptPlayerToUseCards(canCancel);
				curPlayer.cards.remove(new Card(CardType.CANNON));
				curPlayer.cards.remove(new Card(CardType.CANNON));
				curPlayer.cards.remove(new Card(CardType.CANNON));
			}
			else if (infNum >= 1 && calNum >= 1 && canNum >= 1) {
				cardArmies = promptPlayerToUseCards(canCancel);
				curPlayer.cards.remove(new Card(CardType.INFANTRY));
				curPlayer.cards.remove(new Card(CardType.CALVARY));
				curPlayer.cards.remove(new Card(CardType.CANNON));
			}
		}
		placeCountry.numArmies = getPlayerPlaceArmies(cardArmies);
		currentTurnState = TurnState.PLACE;
	}

	private int getPlayerPlaceArmies(int cardArmies) {
		int countryArmies = currentPlayer().countries.size()/3;
		if (countryArmies < 3) countryArmies = 3;
		int continentArmies = ContinentChecker.getContinentBonus(currentPlayer());
		return cardArmies + countryArmies + continentArmies;
	}

	private int promptPlayerToUseCards(boolean canCancel) {
		String mustUse = (canCancel) ? "\nWould you like to use them?" : "\nYou must use them.";
		String message = players[curPlayerIndex].name + ", you have a card match!\nCards are currently worth " + getCardValue() + " armies." + mustUse;
		String title = "Use Cards?";
		if (canCancel) {
			if (JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				cardValueIndex++;
				return getCardValue();
			} else return 0;
		} else {
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
			return getCardValue();
		}
	}

	private int getCardValue() {
		if (cardValueIndex == 0) return 4;
		else if (cardValueIndex == 1) return 6;
		else if (cardValueIndex == 2) return 8;
		else if (cardValueIndex == 3) return 10;
		else if (cardValueIndex == 4) return 12;
		else if (cardValueIndex == 5) return 15;
		else return 15 + 5 * (cardValueIndex - 5);
	}

	private void shuffleCountries(int times) {
		Random rand = new Random();
		for (int i = 0; i < times; i++) {
			int index = rand.nextInt(shuffledCountries.size());
			Country country = shuffledCountries.get(index);
			shuffledCountries.remove(index);
			shuffledCountries.add(country);
		}
	}

	private void distributeCountries() {
		int size = shuffledCountries.size();
		int cardNum = 0;
		int NOP = players.length;
		int playerNum = 1;
		while (cardNum < size) {
			players[playerNum - 1].countries.add(shuffledCountries.get(cardNum));
			playerNum++;
			cardNum++;
			if (playerNum > NOP) playerNum = 1;
		}
	}
}