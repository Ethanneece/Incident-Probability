package annotations;

import java.lang.annotation.Repeatable;

@Repeatable(Outcomes.class)
public @interface Outcome {
    Event[] conditionalEvents();
    String incidentName();
    int probValue();
}
