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
			Arrays.asList("Juan", "Pedro", "Santiago", "Daniel", "Gasto", "Tomas", "Maximiliano", "Jimena", "Delfina", "Julieta", "Lucia", "Luciana", "Ludmila", "Lujan", "Lourdes")
	);

	private final List<String> surnames = new ArrayList<String>(
			Arrays.asList("Moix", "Capanegra", "Vago", "Ferreiro", "Telpuk", "Kupelian", "Martinez", "Fredes", "Ferrari", "Frank", "Gutierrez", "Fiel")
	);

	private final List<String> provinces = new ArrayList<String>(
			Arrays.asList("Capital Federal")
	);
	
	private final List<String> teams = new ArrayList<String>(
			Arrays.asList("Boca")
	);
	
	private final List<String> sports = new ArrayList<String>(
			Arrays.asList("Futbol", "Hockey", "Rugby", "Polo", "Tenis", "Ajedrez", "Pingpong", "Handball", "Football", "Basquet")
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
		int index = new Random().nextInt(fromList.size());
		return fromList.get(index);
	}
}
