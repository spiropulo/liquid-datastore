package com.five9.liquid.pipeline;

import com.five9.liquid.ChangelogFile;
import com.five9.liquid.ChangelogRecord;
import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class PipelineDataTest {
    private static final LocalDatastoreHelper HELPER = LocalDatastoreHelper.create(1.0);

    @BeforeAll
    public static void setup() throws IOException, InterruptedException {
        HELPER.start();
    }

    @Test
    public void testTheData() throws ClassNotFoundException {
        PipelineData d = new PipelineData(HELPER.getOptions().toBuilder().setNamespace("test").build().getService());
        ChangelogRecord cr = new ChangelogRecord();
        ChangelogFile cl = new ChangelogFile();
        cl.setAuthor("Jorge");
        d.put("cr", cr);
        d.put("cl", cl);

        ChangelogRecord cr1 = d.get("cr");
        ChangelogFile cl1 = d.get("cl");
        Assertions.assertEquals("Jorge", cl1.getAuthor());
    }
}