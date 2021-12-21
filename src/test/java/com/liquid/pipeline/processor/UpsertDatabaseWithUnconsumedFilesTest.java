package com.liquid.pipeline.processor;

import com.liquid.ChangelogFile;
import com.liquid.ChangelogRecord;
import com.liquid.EntryCell;
import com.liquid.pipeline.PipelineData;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

public class UpsertDatabaseWithUnconsumedFilesTest {
    private final UpsertDatabaseWithUnconsumedFiles underTest = UpsertDatabaseWithUnconsumedFiles.get();
    private PipelineData d;
    private List<ChangelogFile> changelogFiles;
    private Datastore ds;

    @BeforeEach
    public void setup() {
        ds = mock(Datastore.class);
        d = new PipelineData(ds);
        KeyFactory keyFactory = mock(KeyFactory.class);
        KeyFactory projectId = mock(KeyFactory.class);
        KeyFactory namespace = mock(KeyFactory.class);
        KeyFactory keyFactoryMock = mock(KeyFactory.class);
        Entity fullEntity = mock(Entity.class);
        IncompleteKey incompleteKey = mock(IncompleteKey.class);
        Key key = mock(Key.class);

        when(ds.newKeyFactory()).thenReturn(keyFactory);
        when(keyFactory.setKind(anyString())).thenReturn(projectId);
        when(projectId.setProjectId(anyString())).thenReturn(namespace);
        when(namespace.setNamespace(anyString())).thenReturn(keyFactoryMock);
        when(ds.add((FullEntity<?>) any())).thenReturn(fullEntity);
        when(keyFactoryMock.newKey()).thenReturn(incompleteKey);
        when(fullEntity.getKey()).thenReturn(key);
    }

    @Test
    public void testConsumeStringInserts() throws Exception {
        List<ChangelogFile> changelogFiles = new ArrayList<>();
        changelogFiles.add(getChangelogFileInsert("tea", EntryCell.Types.STRING.toString().toLowerCase(), "black"));
        this.changelogFiles = changelogFiles;
        run();
        verify(ds, times(1)).put(any(Entity.class));
    }

    @Test
    public void testConsumeBooleanInserts() throws Exception {
        List<ChangelogFile> changelogFiles = new ArrayList<>();
        changelogFiles.add(getChangelogFileInsert("isTea", EntryCell.Types.BOOLEAN.toString().toLowerCase(), "true"));
        this.changelogFiles = changelogFiles;
        run();
        verify(ds, times(1)).put(any(Entity.class));
    }

    @Test
    public void testConsumeIntInserts() throws Exception {
        List<ChangelogFile> changelogFiles = new ArrayList<>();
        changelogFiles.add(getChangelogFileInsert("count", EntryCell.Types.INTEGER.toString().toLowerCase(), "100"));
        this.changelogFiles = changelogFiles;
        run();
        verify(ds, times(1)).put(any(Entity.class));
    }

    @Test
    public void testConsumeTimestampInserts() throws Exception {
        List<ChangelogFile> changelogFiles = new ArrayList<>();
        changelogFiles.add(getChangelogFileInsert("when", EntryCell.Types.TIMESTAMP.toString().toLowerCase(), Timestamp.now()));
        this.changelogFiles = changelogFiles;
        run();
        verify(ds, times(1)).put(any(Entity.class));
    }

    @Test
    public void testConsumeDoubleInserts() throws Exception {
        List<ChangelogFile> changelogFiles = new ArrayList<>();
        changelogFiles.add(getChangelogFileInsert("longNumber", EntryCell.Types.DOUBLE.toString().toLowerCase(), new Double("1.8")));
        this.changelogFiles = changelogFiles;
        run();
        verify(ds, times(1)).put(any(Entity.class));
    }

    @Test
    public void testConsumeAllTypesInsert() throws Exception {
        List<ChangelogFile> changelogFiles = new ArrayList<>();
        changelogFiles.add(getChangelogFileInsert("tea", EntryCell.Types.STRING.toString().toLowerCase(), "black"));
        changelogFiles.add(getChangelogFileInsert("isTea", EntryCell.Types.BOOLEAN.toString().toLowerCase(), "true"));
        changelogFiles.add(getChangelogFileInsert("count", EntryCell.Types.INTEGER.toString().toLowerCase(), "100"));
        changelogFiles.add(getChangelogFileInsert("when", EntryCell.Types.TIMESTAMP.toString().toLowerCase(), Timestamp.now()));
        changelogFiles.add(getChangelogFileInsert("doubleNumber", EntryCell.Types.DOUBLE.toString().toLowerCase(), new Double("1.8")));
        this.changelogFiles = changelogFiles;
        run();
        verify(ds, times(5)).put(any(Entity.class));
    }

    private ChangelogFile getChangelogFileInsert(String key, String type, Object value) {
        return getChangelogFile("jorge",
                "first pass",
                getChangelogRecords(asList(
                        asList("insert", "Variable", "-1", "-1", getEntryCells(asList(
                                asList("key", key, "string"),
                                asList("value", value, type),
                                asList("createdBy", "jorge_c", "string"),
                                asList("updatedBy", "jorge_u", "string")
                        )))
                ))
        );
    }

    private List<EntryCell> getEntryCells(List<List<Object>> asList) {
        List<EntryCell> result = new ArrayList<>();
        for (List<Object> r : asList) {
            String v = (String) r.get(2);
            EntryCell.Types dataType = EntryCell.Types.val(v);
            result.add(new EntryCell((String) r.get(0), dataType, r.get(1)));
        }
        return result;
    }

    private <T> List<ChangelogRecord> getChangelogRecords(List<T> asList) {
        List<ChangelogRecord> result = new ArrayList<>();
        for (T r : asList) {
            String operation = ((List<String>) r).get(0);
            String objectType = ((List<String>) r).get(1);
            String deleteFilter = ((List<String>) r).get(2);
            String updateFilter = ((List<String>) r).get(3);
            List<EntryCell> cells = (List<EntryCell>) ((List<EntryCell>) r).get(4);
            result.add(new ChangelogRecord(operation, objectType, deleteFilter, updateFilter, cells));
        }
        return result;
    }

    private ChangelogFile getChangelogFile(String author, String description, List<ChangelogRecord> records) {
        ChangelogFile result = new ChangelogFile();
        result.setAuthor(author);
        result.setDescription(description);
        result.setObjects(records);
        return result;
    }

    private void run() throws Exception {
        d.put("files2insert", changelogFiles);
        underTest.process(d);
    }
}
