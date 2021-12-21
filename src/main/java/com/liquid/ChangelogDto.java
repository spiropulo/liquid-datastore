package com.liquid;

import com.google.cloud.Timestamp;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChangelogDto {
    public static final String FILENAME = "filename";
    public static final String AUTHOR = "author";
    public static final String DATEEXECUTED = "dateexecuted";
    public static final String DESCRIPTION = "description";
    public static final String MD5SUM = "md5sum";

    private String author;
    private String filename;
    private Timestamp dateExecuted;
    private String md5sum;
    private String description;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Timestamp getDateExecuted() {
        return dateExecuted;
    }

    public void setDateExecuted(Timestamp dateExecuted) {
        this.dateExecuted = dateExecuted;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
