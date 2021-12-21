package com.liquid;

import com.google.cloud.datastore.Datastore;
import com.liquid.pipeline.PipelineData;

public class StartLiquid {
    public static void start(Datastore d) throws Exception {
        new ChangelogPipeline().run(new PipelineData(d));
    }
}
