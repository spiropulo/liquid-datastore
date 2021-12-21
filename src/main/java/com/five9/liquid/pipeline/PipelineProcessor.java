package com.five9.liquid.pipeline;

public interface PipelineProcessor<T> {
    public void process(PipelineData d) throws Exception;
}
