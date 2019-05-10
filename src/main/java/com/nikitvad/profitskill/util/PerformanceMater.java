package com.nikitvad.profitskill.util;

import org.springframework.util.StopWatch;

public class PerformanceMater {
    public static <R> R mater(Measurable<R> measurable) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(measurable.getClass().getName());

        R result = measurable.measure();

        stopWatch.stop();
        System.out.println("PerformanceMater " + stopWatch.toString());

        return result;
    }

}
