package com.five9.liquid.pipeline.processor;

import com.five9.liquid.ChangelogDto;
import com.five9.liquid.pipeline.PipelineData;
import com.five9.liquid.pipeline.PipelineKeys;
import com.five9.liquid.pipeline.PipelineProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class LoadConsumedFilesFromComparisonData implements PipelineProcessor {
    private LoadConsumedFilesFromComparisonData(){};

    @Override
    public void process(PipelineData d) throws ClassNotFoundException {
        List<ChangelogDto> changelogDtos = d.get(PipelineKeys.CHANGELOGS);
        List<String> filenames = changelogDtos.stream().map(ChangelogDto::getFilename).
                collect(Collectors.toCollection(ArrayList::new));
        d.put(PipelineKeys.FILE_NAMES, filenames);
    }

    public static LoadConsumedFilesFromComparisonData get() {
        return new LoadConsumedFilesFromComparisonData();
    }
}
