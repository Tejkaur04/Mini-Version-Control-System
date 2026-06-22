package com.tejinder.mini_vcs_api.dto;

import java.util.List;

public class StatusDTO {

    private List<String> modified;
    private List<String> deleted;
    private List<String> untracked;

    public StatusDTO(
            List<String> modified,
            List<String> deleted,
            List<String> untracked
    ) {
        this.modified = modified;
        this.deleted = deleted;
        this.untracked = untracked;
    }

    public List<String> getModified() {
        return modified;
    }

    public List<String> getDeleted() {
        return deleted;
    }

    public List<String> getUntracked() {
        return untracked;
    }
}