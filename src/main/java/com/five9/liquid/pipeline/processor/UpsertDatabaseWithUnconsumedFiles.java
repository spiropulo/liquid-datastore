package com.five9.liquid.pipeline.processor;

import com.five9.liquid.*;
import com.five9.liquid.pipeline.PipelineData;
import com.five9.liquid.pipeline.PipelineKeys;
import com.five9.liquid.pipeline.PipelineProcessor;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;

import java.util.List;

public final class UpsertDatabaseWithUnconsumedFiles implements PipelineProcessor {
    private UpsertDatabaseWithUnconsumedFiles() {
    }

    public static UpsertDatabaseWithUnconsumedFiles get() {
        return new UpsertDatabaseWithUnconsumedFiles();
    }

    @Override
    public void process(PipelineData d) throws Exception {
        List<ChangelogFile> inserts = d.get(PipelineKeys.FILES_TO_PROCESS);
        for (ChangelogFile i : inserts) {
            for (ChangelogRecord r : i.getObjects()) {
                if (!SupportedOperations.INSERT.equals(r.getOperation())) {
                    continue;
                }
                KeyFactory keyFactory = d.getDatastore().newKeyFactory().setKind(r.getObjectType()).
                        setProjectId(LiquidConfig.properties.get("projectId")).
                        setNamespace(LiquidConfig.properties.get("namespace"));
                Key taskKey = d.getDatastore().add(FullEntity.newBuilder(keyFactory.newKey()).build()).getKey();
                Entity.Builder builder = Entity.newBuilder(taskKey);
                for (EntryCell c : r.getPayload()) {
                    String fieldName = c.getFieldName();
                    Object fieldValue = c.getFieldValue();
                    EntryCell.Types dataType = c.getDataType();

                    if (EntryCell.Types.STRING.equals(dataType))
                        builder.set(fieldName, (String) fieldValue);
                    else if (EntryCell.Types.BOOLEAN.equals(dataType)) {
                        if (!"true".equals(fieldValue) && !"false".equals(fieldValue))
                            throw new IllegalArgumentException("Value not a boolean: " + fieldValue);
                        builder.set(fieldName, Boolean.parseBoolean((String) fieldValue));
                    } else if (EntryCell.Types.INTEGER.equals(dataType))
                        builder.set(fieldName, Integer.parseInt((String) fieldValue));
                    else if (EntryCell.Types.TIMESTAMP.equals(dataType)) {
                        builder.set(fieldName, (Timestamp) fieldValue);
                    } else if (EntryCell.Types.DOUBLE.equals(dataType)) {
                        builder.set(fieldName, (Double) fieldValue);
                    }
                }
                d.getDatastore().put(builder.build());
            }
        }
    }
}
