package cp2.risk.game;

import java.util.ArrayList;

public class Continent {
	
	public int[] countries;
	public int bonus;
	
	public Continent(int[] countries, int bonus, String name) {
		this.countries = countries;
		this.bonus = bonus;
	}
	
	public int getBonusArmies(Player player) {
		ArrayList<Country> pCountries = player.countries;
		boolean giveBonus = true;
		for (int country : countries) {
			boolean found = false;
			for (Country pCountry : pCountries) {
				if (pCountry.getId() == country) {
					found = true;
					break;
				}
			}
			if (!found) {
				giveBonus = false;
				break;
			}
		}
		
		if (giveBonus)
			return bonus;
		else
			return 0;
	}

}
