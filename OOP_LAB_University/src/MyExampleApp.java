import java.util.logging.Level;
import java.util.logging.Logger;

import university.UniversityExt;

public class MyExampleApp {

	public static void main(String[] args) {
		final String universityName = "Politecnico di Torino";
		UniversityExt poli;
		Logger ul = Logger.getLogger("University");		
		ul.setLevel(Level.OFF);
		
		poli = new UniversityExt(universityName);
		poli.setRector("Guido", "Saracco");
		
		poli.enroll("Mario","Rossi");
		poli.enroll("Francesca","Verdi");
		poli.enroll("Filippo","Neri");
		poli.enroll("Laura","Bianchi");
		
		
		poli.activate("Macro Economics", "Paul Krugman");
		poli.activate("Object Oriented Programming", "James Gosling");
		poli.activate("Virology", "Roberto Burioni");
		
		poli.register(10000, 10);
		poli.register(10001, 10);
		poli.register(10001, 11);
		poli.register(10001, 12);
		poli.register(10002, 11);
		poli.register(10003, 10);
		poli.register(10003, 11);
		
		poli.exam(10000, 10, 27);
		poli.exam(10002, 11, 26);

		String top = poli.topThreeStudents();		
		System.out.println(top);


	}

}
