package com.five9.liquid;

import com.five9.liquid.pipeline.PipelineData;
import com.google.cloud.datastore.Datastore;

public class StartLiquid {
    public static void start(Datastore d) throws Exception {
        new ChangelogPipeline().run(new PipelineData(d));
    }
}
