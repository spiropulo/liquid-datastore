package com.five9.liquid;

import com.five9.liquid.pipeline.PipelineKeys;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StartLiquidTest {
    private static final LocalDatastoreHelper HELPER = LocalDatastoreHelper.create(1.0);

    // Maximum eventual consistency.
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()
                    .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

    @Test
    public void testStart() throws Exception {
        helper.setUp();
        HELPER.start();
        Datastore ds = HELPER.getOptions().toBuilder().setNamespace("datastoretest").build().getService();
        StartLiquid.start(ds);
        List<Entity> vars = findVars(ds);

        Assertions.assertEquals("tea", vars.get(0).getString("key"));
        Assertions.assertEquals("coffee", vars.get(1).getString("key"));
        helper.tearDown();
    }

    @Test
    public void testConnectingToCloud() throws Exception {
        Datastore ds = DatastoreOptions.getDefaultInstance().getService();
        StartLiquid.start(ds);
        List<Entity> vars = findVars(ds);

//        Assertions.assertEquals("tea", vars.get(0).getString("key"));
//        Assertions.assertEquals("coffee", vars.get(1).getString("key"));
    }

    private List<Entity> findVars(Datastore ds) {
        List<Entity> result = new ArrayList<>();
        Query<Entity>  query = Query.newEntityQueryBuilder().setKind(PipelineKeys.VARIABLE).build();
        QueryResults<Entity> run = ds.run(query);
        while(run.hasNext()){
            result.add(run.next());
        }
        return result;
    }
}
