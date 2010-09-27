package pinsgatherer.data;

import java.util.ArrayList;
import java.util.List;


public class FormGenerator {
	
	public static List<Form> generateForms(int availablePins) {
		DataGenerator gen = new DataGenerator();
		List<Form> forms = new ArrayList<Form>();
		
		for (int i = 0; i < availablePins; i++) {
			Form form = new Form();
			
            int millions = (i%7+28)*1000000;
            int hundreds = (i%1000) * 1000;
            int units = (i%999);
			int randomDni = millions + hundreds + units;

            String dni = String.valueOf(randomDni);
            form.setDni(dni);
            form.setMail("ps"+dni+"@mailinator.com");
            form.setCellPhone("11"+dni);

            form.setBirthdateDay(String.valueOf(i%28 + 1));
            form.setBirthdateMonth(String.valueOf(i%12 + 1));
            form.setBirthdateYear(String.valueOf(i%7 + 1983)); // 28.000.000 -> 1983
            
            form.setName(gen.getRandomName());
            form.setSurname(gen.getRandomSurname());
            form.setFootballTeam(gen.getRandomTeam());
            form.setSport(gen.getRandomSport());
            form.setHobbieSport(gen.getRandomSport());
            form.setProvince(gen.getRandomProvince());

			forms.add(form);
		}
		
		return forms;
	}

}
