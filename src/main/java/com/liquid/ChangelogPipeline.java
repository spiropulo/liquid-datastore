package com.liquid;

import com.liquid.pipeline.LiquidPipeline;
import com.liquid.pipeline.processor.*;

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
