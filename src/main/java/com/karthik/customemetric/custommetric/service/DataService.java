package com.karthik.customemetric.custommetric.service;

public class DataService {
    private final long id;
    private final String content;

    public DataService(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
