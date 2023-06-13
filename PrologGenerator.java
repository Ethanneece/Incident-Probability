import java.io.*;
import annotations.Event;
import java.util.ArrayList;

public class PrologGenerator {
	/**
	This method generates cplint.pl
	@return true if the generation succeeded 	
	*/
	public boolean generate(ArrayList<String> events, ArrayList<String> outcomes, ArrayList<String> probs) {

		try {
		PrintWriter pw = new PrintWriter(new File("cplint.pl"));
		pw.println(":- use_module(library(pita)).");
		pw.println(":- pita.");
		pw.println(":- begin_lpad.");
		pw.println();
		

		for (String event : events) {
			pw.println(event);
		}

		for (String outcome : outcomes) {
			pw.println(outcome);
		}
		
		pw.println();
		pw.println(":- end_lpad.");
		pw.println();
	
		// Generate Final Inquiry
		for (String prob : probs) {
			pw.println(prob);
		}


		pw.close();

		return true;
		} catch (IOException e) {
			return false;
		}
	}
}
