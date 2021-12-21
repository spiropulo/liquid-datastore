package com.five9.liquid;

public enum SupportedOperations {
    INSERT,UPDATE,DELETE;

    public boolean equals(String p){
        return this.toString().toLowerCase().equals(p.toLowerCase());
    }
}
