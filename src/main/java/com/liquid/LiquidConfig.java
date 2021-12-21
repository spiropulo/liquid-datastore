package com.liquid;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.net.URL;
import java.util.Map;

public class LiquidConfig {
    public static Map<String, String> properties;
    public static String liquidData;
    public static String changelog;

    static {
        //setup application variables
        properties = new Yaml().load(LiquidConfig.class.getClassLoader().getResourceAsStream("application.yml"));

        liquidData = buildPath("liquidData");
        changelog = buildPath("changelog");
    }

    private static String buildPath(String key) {
        String result = properties.get(key);
        if (StringUtils.isEmpty(result)) {
            throw new IllegalArgumentException(String.format("%s must be set", key));
        }
        URL resource = LiquidConfig.class.getResource("/" + result);
        return resource.getPath();
    }

}
