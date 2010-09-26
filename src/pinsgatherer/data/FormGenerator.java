package pinsgatherer.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FormGenerator {
	
	private static final Integer MIN_DNI = 11000000;
	private static final Integer MAX_DNI = 34000000;

	public static List<Form> generateForms(int availablePins) {
		DataGenerator gen = new DataGenerator();
		List<Form> forms = new ArrayList<Form>();
		
		for (int i = 0; i < availablePins; i++) {
			Form form = new Form();
			
			form.setName(gen.getRandomName());
			form.setSurname(gen.getRandomSurname());
			
			int randomDni = MIN_DNI + (new Random().nextInt(MAX_DNI) - MIN_DNI);
			form.setDni(String.valueOf(randomDni));
			
			// TODO finish
			
			forms.add(new Form());
		}
		
		return forms;
	}

}
