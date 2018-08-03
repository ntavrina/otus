package agent;

import java.lang.instrument.Instrumentation;

public class Agent {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation instrumentation) {
        Agent.instrumentation = instrumentation;
    }

    public static long getSize(Object o) {
        if(instrumentation == null)
            throw new IllegalStateException("Instrumentation not initialized");
        return instrumentation.getObjectSize(o);
    }
}
