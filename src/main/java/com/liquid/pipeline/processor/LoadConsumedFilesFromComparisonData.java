package com.liquid.pipeline.processor;

import com.liquid.ChangelogDto;
import com.liquid.pipeline.PipelineData;
import com.liquid.pipeline.PipelineKeys;
import com.liquid.pipeline.PipelineProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class LoadConsumedFilesFromComparisonData implements PipelineProcessor {
    private LoadConsumedFilesFromComparisonData() {
    }

    public static LoadConsumedFilesFromComparisonData get() {
        return new LoadConsumedFilesFromComparisonData();
    }

    @Override
    public void process(PipelineData d) throws ClassNotFoundException {
        List<ChangelogDto> changelogDtos = d.get(PipelineKeys.CHANGELOGS);
        List<String> filenames = changelogDtos.stream().map(ChangelogDto::getFilename).
                collect(Collectors.toCollection(ArrayList::new));
        d.put(PipelineKeys.FILE_NAMES, filenames);
    }
}
