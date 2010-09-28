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
            int hundreds = (i%999) * 1000;
            int units = i%1000;
			int randomDni = millions + hundreds + (999 - units);
            int randomDni2 = millions-23 + (999000 - hundreds) + units;

            form.setName(gen.getRandomName());
            form.setSurname(gen.getRandomSurname());

            String dni = String.valueOf(randomDni);
            String dni2 = String.valueOf(randomDni2);
            form.setDni(dni);
            form.setMail(form.getName().charAt(0)+dni2.substring(2,5)+form.getSurname().charAt(0)+dni2.substring(5,8)+dni2.substring(0,2)+"@mailinator.com");
            form.setCellPhone("11"+dni2);

            form.setBirthdateDay(String.valueOf(i%28 + 1));
            form.setBirthdateMonth(String.valueOf(i%12 + 1));
            form.setBirthdateYear(String.valueOf(i%7 + 1983)); // 28.000.000 -> 1983
            
            form.setFootballTeam(gen.getRandomTeam());
            form.setSport(gen.getRandomSport());
            form.setHobbieSport(gen.getRandomSport());
            form.setProvince(gen.getRandomProvince());

			forms.add(form);
		}
		
		return forms;
	}

}
