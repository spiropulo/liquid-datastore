package com.liquid;

import com.google.gson.Gson;

import java.nio.file.Files;
import java.nio.file.Paths;

public class LiquidUtil {
    public static ChangelogFile buildChangeLogRecord(String l) throws Exception {
        final Gson gson = new Gson();
        String content = readFileAsString(l);
        return gson.fromJson(content, ChangelogFile.class);
    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
