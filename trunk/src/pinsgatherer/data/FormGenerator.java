package pinsgatherer.data;

import java.util.ArrayList;
import java.util.List;


public class FormGenerator {
	
	public static List<Form> generateForms(int availablePins) {
		DataGenerator gen = new DataGenerator();
		List<Form> forms = new ArrayList<Form>();
		
		for (int i = 0; i < availablePins; i++) {
			Form form = new Form();
			
            int millions = (availablePins%7+28)*1000000;
            int hundreds = (availablePins%1000) * 1000;
            int units = (availablePins%999);
			int randomDni = millions + hundreds + units;

            String dni = String.valueOf(randomDni);
            form.setDni(dni);
            form.setMail("ps"+dni+"@mailinator.com");
            form.setCellPhone("15"+dni);

            form.setBirthdateDay(String.valueOf(availablePins%28 + 1));
            form.setBirthdateMonth(String.valueOf(availablePins%12 + 1));
            form.setBirthdateYear(String.valueOf(availablePins%7 + 1983)); // 28.000.000 -> 1983
            
            form.setName(gen.getRandomName());
            form.setSurname(gen.getRandomSurname());
            form.setFootballTeam(gen.getRandomTeam());
            form.setSport(gen.getRandomSport());
            form.setHobbieSport(gen.getRandomSport());
            form.setProvince(gen.getRandomProvince());

			forms.add(new Form());
		}
		
		return forms;
	}

}
