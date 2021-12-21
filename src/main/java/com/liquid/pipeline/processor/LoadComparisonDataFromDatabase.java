package com.liquid.pipeline.processor;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.liquid.ChangelogDto;
import com.liquid.LiquidConfig;
import com.liquid.pipeline.PipelineData;
import com.liquid.pipeline.PipelineKeys;
import com.liquid.pipeline.PipelineProcessor;

import java.util.ArrayList;
import java.util.List;

public class LoadComparisonDataFromDatabase implements PipelineProcessor {
    private LoadComparisonDataFromDatabase() {
    }

    public static LoadComparisonDataFromDatabase get() {
        return new LoadComparisonDataFromDatabase();
    }

    @Override
    public void process(PipelineData d) {
        Query<Entity> query = Query.newEntityQueryBuilder().setKind(PipelineKeys.DATABASECHANGELOG).
                setNamespace(LiquidConfig.properties.get("namespace")).build();
        QueryResults<Entity> run = d.getDatastore().run(query);

        List<ChangelogDto> logs = new ArrayList<>();

        while (run.hasNext()) {
            Entity next = run.next();
            ChangelogDto changelogDto = new ChangelogDto();
            changelogDto.setFilename(next.getString("filename"));
            changelogDto.setAuthor(next.getString("author"));
            changelogDto.setDateExecuted(next.getTimestamp("dateexecuted"));
            changelogDto.setDescription(next.getString("description"));
            changelogDto.setMd5sum(next.getString("md5sum"));
        }

        d.put(PipelineKeys.CHANGELOGS, logs);
    }
}
