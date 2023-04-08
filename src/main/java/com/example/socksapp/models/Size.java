package com.example.socksapp.models;

public enum Size {
    S("40"),
    M("42"),
    L("44"),
    XL("46");

    private final String SizeOfSocks;

    Size(String SizeOfSocks) {
        this.SizeOfSocks = SizeOfSocks;
    }

    public String getSize() {
        return SizeOfSocks;
    }
}