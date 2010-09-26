package pinsgatherer.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * 
 * Utility class to generate random values for the forms. 
 * 
 */

public class DataGenerator {
	
	private final List<String> names = new ArrayList<String>(
			Arrays.asList(new String[] { 
					"Diego", "Martin", "Carlos" 
			})
	);
	
	private final List<String> surnames = new ArrayList<String>(
			Arrays.asList(new String[] { 
					"Perez", "Donofilio", "Arota" 
			})
	);

	private final List<String> provinces = new ArrayList<String>(
			Arrays.asList(new String[] { 
					"Buenos Aires", "Formosa", "Salta" 
			})
	);
	
	private final List<String> teams = new ArrayList<String>(
			Arrays.asList(new String[] { 
					"River", "Boca", "Arsenal" 
			})
	);
	
	private final List<String> sports = new ArrayList<String>(
			Arrays.asList(new String[] { 
					"Futbol", "Hockey", "Rugby" 
			})
	);
	
	public String getRandomName() {
		return getRandomValue(names);
	}
	
	public String getRandomSurname() {
		return getRandomValue(surnames);
	}
	
	public String getRandomProvince() {
		return getRandomValue(provinces);
	}
	
	public String getRandomTeam() {
		return getRandomValue(teams);
	}
	
	public String getRandomSport() {
		return getRandomValue(sports);
	}
	
	private String getRandomValue(List<String> fromList) {
		int index = new Random().nextInt(fromList.size() - 1);
		return fromList.get(index);
	}
}
