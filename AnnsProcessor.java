import javax.annotation.processing.*;
import java.util.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import annotations.*;
import java.util.ArrayList;

@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnsProcessor extends AbstractProcessor {
	@Override
	public synchronized void init(ProcessingEnvironment env) {
		super.init(env);
	}

	@Override
	public boolean process(Set<? extends TypeElement> set,
			RoundEnvironment roundEnv) {
		if (set.size() == 0) return true;
		ArrayList<String> events = ProcessEvent(roundEnv);
		ArrayList<String> outcomes = ProcessOutcome(roundEnv);
		ArrayList<String> probs = ProcessGetProbability(roundEnv);
		GeneratePrologFile(events, outcomes, probs);
		return true;
	}

	private ArrayList<String> ProcessEvent(RoundEnvironment roundEnv) {

		ArrayList<String> rtn = new ArrayList<String>(); 

		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(Events.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				ExecutableElement el = (ExecutableElement) element;
				Event[] evs = el.getAnnotationsByType(Event.class);
				
				// Grab probability from event and add to list. 
				for (Event event : evs) {
					System.out.println(event);
					int oppositeProps = 100 - event.probValue(); 
					String probs = event.name() + "(t): " + (event.probValue()/100.0) + "; " + event.name() + "(f):" + (oppositeProps/100.0) + ".";
					rtn.add(probs);
				}
			}
		}

		return rtn; 
	}

	private ArrayList<String> ProcessOutcome(RoundEnvironment roundEnv) {

		ArrayList<String> rtn = new ArrayList<String>(); 

		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(Events.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				ExecutableElement el = (ExecutableElement) element;
				Outcome[] ots = el.getAnnotationsByType(Outcome.class);
				
				// Grab incident name from outcome
				for (Outcome outcome : ots) {
					
					int oppositeProps = 100 - outcome.probValue(); 
					String result = outcome.incidentName() + "(t): " + (outcome.probValue()/100.0) + " ; " + outcome.incidentName() + "(f):" + (oppositeProps/100.0) + ":-";

					Event event1 = outcome.conditionalEvents()[0];
					Event event2 = outcome.conditionalEvents()[1];

					// ! means we are doing the opposite
					if (event1.name().contains("!")) {
						result += event1.name().substring(1) + "(f),";
					}
					else {
						result += event1.name() + "(t),";
					}

					if (event2.name().contains("!")) {
						result += event2.name().substring(1) + "(f).";
					}
					else {
						result += event2.name() + "(t).";
					}

					rtn.add(result);
				}
			}
		}

		return rtn; 
	}

	private ArrayList<String> ProcessGetProbability(RoundEnvironment roundEnv) {

		ArrayList<String> rtn = new ArrayList<String>(); 

		Set<? extends Element> annotatedElements =
			roundEnv.getElementsAnnotatedWith(GetProbability.class);
		for (Element element : annotatedElements) {
			if (element.getKind() == ElementKind.METHOD) {
				ExecutableElement el = (ExecutableElement) element;
				GetProbability gp = el.getAnnotation(GetProbability.class);
				
				// gets the probability of the incidentName. 
				rtn.add("outcome(Prob) :- prob(" + gp.incidentName() + "(t), Prob).");
			}
		}

		return rtn; 
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> anns = new HashSet<>();
		anns.add("annotations.Event");
		anns.add("annotations.Events");
		anns.add("annotations.Outcome");
		anns.add("annotations.Outcomes");
		return anns;
	}

	private void GeneratePrologFile(ArrayList events, ArrayList outcomes, ArrayList props) {
		PrologGenerator gen = new PrologGenerator();
		gen.generate(events, outcomes, props);
	}
}
