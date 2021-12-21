package com.five9.liquid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Locale;

public class EntryCell {
    private String fieldName;
    private Types dataType;
    private Object fieldValue;

    public EntryCell() {
    }

    public EntryCell(String fieldName, Types dataType, Object fieldValue) {
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Types getDataType() {
        return dataType;
    }

    public void setDataType(Types dataType) {
        this.dataType = dataType;
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

    public enum Types {
        STRING, BOOLEAN, INTEGER, DOUBLE, TIMESTAMP;

        public static Types val(String v) {
            return Types.valueOf(v.toUpperCase());
        }
    }
}
