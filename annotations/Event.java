package annotations;

import java.lang.annotation.Repeatable;

@Repeatable(Events.class)
public @interface Event {
    String name() default "";
    int probValue() default 0;
}
