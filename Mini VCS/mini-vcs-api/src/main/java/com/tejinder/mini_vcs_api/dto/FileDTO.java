package com.tejinder.mini_vcs_api.dto;

public class FileDTO {

    private String name;
    private String hash;
    private String size;
    private String lastCommit;

    public FileDTO(
            String name,
            String hash,
            String size,
            String lastCommit
    ) {
        this.name = name;
        this.hash = hash;
        this.size = size;
        this.lastCommit = lastCommit;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    public String getSize() {
        return size;
    }

    public String getLastCommit() {
        return lastCommit;
    }
}