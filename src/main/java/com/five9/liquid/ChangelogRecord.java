package com.five9.liquid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ChangelogRecord {
    private String operation;
    private String objectType;
    private List<EntryCell> payload;
    private String updateFilter;
    private String deleteFilter;

    public ChangelogRecord(){}

    public ChangelogRecord(String operation, String objectType, String updateFilter, String deleteFilter, List<EntryCell> payload) {
        this.operation = operation;
        this.objectType = objectType;
        this.payload = payload;
        this.updateFilter = updateFilter;
        this.deleteFilter = deleteFilter;
    }

    public List<EntryCell> getPayload() {
        return payload;
    }

    public void setPayload(List<EntryCell> payload) {
        this.payload = payload;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getUpdateFilter() {
        return updateFilter;
    }

    public void setUpdateFilter(String updateFilter) {
        this.updateFilter = updateFilter;
    }

    public String getDeleteFilter() {
        return deleteFilter;
    }

    public void setDeleteFilter(String deleteFilter) {
        this.deleteFilter = deleteFilter;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
