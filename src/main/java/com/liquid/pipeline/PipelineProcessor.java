package com.liquid.pipeline;

public interface PipelineProcessor<T> {
    void process(PipelineData d) throws Exception;
}
