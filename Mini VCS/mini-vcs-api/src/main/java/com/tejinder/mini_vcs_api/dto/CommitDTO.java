package com.tejinder.mini_vcs_api.dto;

public class CommitDTO {

    private String id;
    private String message;
    private String parent;
    private String timestamp;
    private boolean head;

    public CommitDTO(
            String id,
            String message,
            String parent,
            String timestamp,
            boolean head
    ) {
        this.id = id;
        this.message = message;
        this.parent = parent;
        this.timestamp = timestamp;
        this.head = head;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getParent() {
        return parent;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean isHead() {
        return head;
    }
}