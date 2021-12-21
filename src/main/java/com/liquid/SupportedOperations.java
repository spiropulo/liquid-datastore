package com.liquid;

public enum SupportedOperations {
    INSERT, UPDATE, DELETE;

    public boolean equals(String p) {
        return this.toString().equalsIgnoreCase(p);
    }
}
