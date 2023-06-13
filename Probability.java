import annotations.*;

public class Probability {
	private final static float probabilityValue;
	static {
		System.loadLibrary("PrologFromCpp");	
		probabilityValue = probIncident();
	}
	// Input 1
	@Event(name = "burglary", probValue = 10)
	@Event(name = "earthquake", probValue = 20)
	@Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue = 100)
	@Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 80)
	@Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue = 80)
	@Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 10)
	@GetProbability(incidentName="alarm")
	

	// Input 2
	// @Event(name = "burglary", probValue = 35)
    //     @Event(name = "earthquake", probValue = 15)
    //     @Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue = 100)
    //     @Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 90)
    //     @Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue =75)
    //     @Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 8)
    //     @GetProbability(incidentName="alarm")

	// Input 3
	// @Event(name = "burglary", probValue = 30)
    //     @Event(name = "earthquake", probValue = 10)
    //     @Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue = 100)
    //     @Outcome(conditionalEvents = {@Event(name = "burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 60)
    //     @Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "earthquake")}, incidentName = "alarm", probValue = 90)
    //     @Outcome(conditionalEvents = {@Event(name = "!burglary"), @Event(name = "!earthquake")}, incidentName = "alarm", probValue = 5)
    //     @GetProbability(incidentName="alarm")

	public static native float probIncident();

	public static void main(String ... args) {
		System.out.println("The incident probability: " + probabilityValue);
	}
}
