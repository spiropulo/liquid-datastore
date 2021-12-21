package com.five9.liquid.pipeline.processor;

import com.five9.liquid.ChangelogFile;
import com.five9.liquid.ChangelogService;
import com.five9.liquid.LiquidConfig;
import com.five9.liquid.LiquidUtil;
import com.five9.liquid.pipeline.PipelineData;
import com.five9.liquid.pipeline.PipelineKeys;
import com.five9.liquid.pipeline.PipelineProcessor;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class LoadUnconsumedFilesFromDisc implements PipelineProcessor {
    private final Gson gson = new Gson();
    private LoadUnconsumedFilesFromDisc() {}

    public static LoadUnconsumedFilesFromDisc get() {
        return new LoadUnconsumedFilesFromDisc();
    }

    @Override
    public void process(PipelineData d) throws Exception {
        List<String> inDbFileNames = d.get(PipelineKeys.FILE_NAMES);
        List<ChangelogFile> result = new ArrayList<>();
        List<String> changeLogFiles = fetchChangeLogFiles(d);
        for (final String l : changeLogFiles) {
            if (!inDbFileNames.contains(l)) {
                result.add(LiquidUtil.buildChangeLogRecord(l));
            }
        }
        d.put("FULL_FILES_PATH", changeLogFiles);
        d.put(PipelineKeys.FILES_TO_PROCESS, result);
    }

    private List<String> fetchChangeLogFiles(PipelineData d) throws Exception {
        LoadUnconsumedFilesFromDisc.Logs logs = gson.fromJson(
                LiquidUtil.readFileAsString(LiquidConfig.changelog), LoadUnconsumedFilesFromDisc.Logs.class);
        List<String> files = logs.getFiles();
        d.put(PipelineKeys.FILE_NAMES_IN_CLASSPATH, files);
        return files.stream().map(l -> {
            return ChangelogService.class.getResource("/" + l).getPath();
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private class Logs {
        private List<String> files;
        public List<String> getFiles() {
            return files;
        }
        public void setFiles(List<String> files) {
            this.files = files;
        }
    }
}
