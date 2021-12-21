package com.five9.liquid.pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class LiquidPipeline {
    private final List<PipelineProcessor> pipelineProcessors = new ArrayList<>();

    public void add(PipelineProcessor... pps) {
        Arrays.stream(pps).sequential().forEach(pipelineProcessors::add);
    }

    public void run(PipelineData d) throws Exception {
        for (PipelineProcessor p : pipelineProcessors) {
            p.process(d);
        }
    }
}
