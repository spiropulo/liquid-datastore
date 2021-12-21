package com.five9.liquid;

import com.five9.liquid.pipeline.LiquidPipeline;
import com.five9.liquid.pipeline.processor.*;

public class ChangelogPipeline extends LiquidPipeline {
    public ChangelogPipeline() {
        add(LoadComparisonDataFromDatabase.get(),
            LoadConsumedFilesFromComparisonData.get(),
            LoadUnconsumedFilesFromDisc.get(),
            UpsertDatabaseWithUnconsumedFiles.get(),
            RecordUncosumedFilesInDatabase.get()
        );
    }
}
