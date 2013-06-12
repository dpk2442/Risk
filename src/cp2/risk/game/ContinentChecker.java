package cp2.risk.game;

public class ContinentChecker {

	private static final Continent[] continents = {
		new Continent(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8}, 5, "North America"),
		new Continent(new int[] {9, 10, 11, 13, 14, 15}, 5, "Europe"),
		new Continent(new int[] {16, 17, 18, 19}, 2, "South America"),
		new Continent(new int[] {20, 21, 22, 23, 24, 25}, 3, "Africa"),
		new Continent(new int[] {26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37}, 7, "Asia"),
		new Continent(new int[] {38, 39, 40, 41}, 2, "Australia")
	};
	
	public static int getContinentBonus(Player player) {
		int totalBonus = 0;
		for (Continent continent : continents) {
			totalBonus += continent.getBonusArmies(player);
		}
		return totalBonus;
	}

}
