package com.streamlearn.wikimedia.service;

import org.springframework.context.annotation.Configuration;

import java.lang.instrument.Instrumentation;


@Configuration
public class InstrumentationAgent {
    private static  Instrumentation globalInstrumentation;

    public static void premain(final String agentArgs, final Instrumentation inst) {
        globalInstrumentation = inst;
    }

    public static long getObjectSize(final Object object) {
        if (globalInstrumentation == null) {
            throw new IllegalStateException("Agent not initialized.");
        }
        return globalInstrumentation.getObjectSize(object);
    }
}
