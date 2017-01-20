package agent;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;

import java.lang.instrument.Instrumentation;

import static java.lang.String.format;
import static net.bytebuddy.matcher.ElementMatchers.*;

public class AgentEntryPoint {

    private final String ignoredRegex = "cases.to.play.instrumenting.fake.package.*";

    public void start(Instrumentation instrumentation) {
        Boolean useIgnored = useIgnored();
        System.out.println(format ("%s the usage of ignored filter. Expression: %s", useIgnored ? "Enabled" : "Disabled", ignoredRegex));

        AgentBuilder agent = new AgentBuilder.Default()
                .type(not(isInterface()).and(not(isSynthetic()))
                        .and(hasSuperType(named("cases.to.play.instrumenting.Worker"))))
                .transform((builder, typeDescription, classLoader, module) -> builder.method(named("toString"))
                        .intercept(FixedValue.value("transformed")));

        if (useIgnored) {
            agent = agent.ignore(not(nameMatches(ignoredRegex)));
        }

        agent.installOn(instrumentation);
    }

    private Boolean useIgnored() {
        try {
            return Boolean.getBoolean("ignored");
        } catch (Throwable exc) {
            exc.printStackTrace();
            return false;
        }

    }

    public static void premain(String args, Instrumentation instrumentation) throws Exception {
        new AgentEntryPoint().start(instrumentation);
    }
}
