package com.liquid.pipeline;

import com.google.cloud.datastore.Datastore;
import com.google.gson.internal.Primitives;

import java.util.HashMap;
import java.util.Map;

public class PipelineData {
    private final Datastore datastore;
    private final Map<String, Object> data = new HashMap<>();
    private final Map<String, String> keyClass = new HashMap<>();

    public PipelineData(Datastore d) {
        datastore = d;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    private <T> T get(String key, Class<T> classOfT) {
        return Primitives.wrap(classOfT).cast(data.get(key));
    }

    public <T> T get(String key) throws ClassNotFoundException {
        String val = keyClass.get(key);

        if (val == null) {
            throw new IllegalArgumentException("No value found for key " + key);
        }

        return (T) get(key, Class.forName(val));
    }

    public void put(String key, Object pData) {
        data.put(key, pData);
        keyClass.put(key, pData.getClass().getName());
    }
}
