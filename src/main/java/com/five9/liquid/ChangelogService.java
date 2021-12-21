package com.five9.liquid;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChangelogService {
    private final static Gson gson = new Gson();

//    public static List<ChangelogDto> readChangelogs() {
//        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//        Entity liquidOwner = new Entity("Liquid", "owner");
//        Key liquidOwnerKey = liquidOwner.getKey();
//
//        Query changelogQuery = new Query("DATABASECHANGELOG").setAncestor(liquidOwnerKey);
//
//        List<Entity> changelogs =
//                datastore.prepare(changelogQuery).asList(FetchOptions.Builder.withDefaults());
//        return changelogs.stream().map(c -> {
//            ChangelogDto changelogDto = new ChangelogDto();
//            changelogDto.setFilename((String) c.getProperty("filename"));
//            changelogDto.setAuthor((String) c.getProperty("author"));
//            changelogDto.setDateExecuted((Date) c.getProperty("dateexecuted"));
//            changelogDto.setDescription((String) c.getProperty("description"));
//            changelogDto.setMd5sum((String) c.getProperty("md5sum"));
//            return changelogDto;
//        }).collect(Collectors.toCollection(ArrayList<ChangelogDto>::new));
//    }

//    public static List<String> fetchInDbFileNames() {
//        List<ChangelogDto> changelogDtos = readChangelogs();
//        return changelogDtos.stream().map(ChangelogDto::getFilename).collect(Collectors.toCollection(ArrayList::new));
//    }

//    public static List<ChangelogFile> fetchRecordsForInsert() throws Exception {
//        List<String> inDbFileNames = fetchInDbFileNames();
//        List<ChangelogFile> result = new ArrayList<>();
//
//        final File folder = new File(LiquidConfig.liquidData);
//        List<String> changeLogFiles = fetchChangeLogFiles();
//        for (final String l : changeLogFiles) {
//            if (!inDbFileNames.contains(l)){
//                result.add(buildChangeLogRecord(l));
//            }
//        }
//        return result;
//    }

//    private static List<String> fetchChangeLogFiles() throws Exception {
//        Logs logs = gson.fromJson(readFileAsString(LiquidConfig.changelog), Logs.class);
//        return logs.getFiles().stream().map(l -> {
//            return ChangelogService.class.getResource("/" + l).getPath();
//        }).collect(Collectors.toCollection(ArrayList::new));
//    }

//    private class Logs{
//        private List<String> files;
//
//        public List<String> getFiles() {
//            return files;
//        }
//
//        public void setFiles(List<String> files) {
//            this.files = files;
//        }
//    }

//    private static ChangelogFile buildChangeLogRecord(String l) throws Exception {
//        String content = readFileAsString(l);
//        return gson.fromJson(content, ChangelogFile.class);
//    }
//
//    private static String readFileAsString(String file)throws Exception {
//        return new String(Files.readAllBytes(Paths.get(file)));
//    }
}
