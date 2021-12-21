package com.five9.liquid.pipeline.processor;

import com.five9.liquid.ChangelogDto;
import com.five9.liquid.ChangelogFile;
import com.five9.liquid.LiquidConfig;
import com.five9.liquid.pipeline.PipelineData;
import com.five9.liquid.pipeline.PipelineKeys;
import com.five9.liquid.pipeline.PipelineProcessor;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;

import java.util.List;

public final class RecordUncosumedFilesInDatabase implements PipelineProcessor {
    private RecordUncosumedFilesInDatabase() {
    }

    public static RecordUncosumedFilesInDatabase get() {
        return new RecordUncosumedFilesInDatabase();
    }

    @Override
    public void process(PipelineData d) throws Exception {
        List<String> classpathFileNames = d.get(PipelineKeys.FILE_NAMES_IN_CLASSPATH);
        List<ChangelogFile> changelogFiles = d.get(PipelineKeys.FILES_TO_PROCESS);
        KeyFactory keyFactory = d.getDatastore().newKeyFactory().setKind(
                PipelineKeys.DATABASECHANGELOG).
                setProjectId(LiquidConfig.properties.get("projectId")).
                setNamespace(LiquidConfig.properties.get("namespace"));
        Key taskKey = d.getDatastore().add(FullEntity.newBuilder(keyFactory.newKey()).build()).getKey();
        Entity.Builder builder = Entity.newBuilder(taskKey);

        for (int i = 0; i < classpathFileNames.size(); i++) {
            String path = classpathFileNames.get(i);
            builder.set(ChangelogDto.FILENAME, path);
            builder.set(ChangelogDto.AUTHOR, changelogFiles.get(i).getAuthor());
            builder.set(ChangelogDto.DATEEXECUTED, Timestamp.now());
            builder.set(ChangelogDto.DESCRIPTION, changelogFiles.get(i).getDescription());
            builder.set(ChangelogDto.MD5SUM, "");
        }

        d.getDatastore().put(builder.build());
    }
}
