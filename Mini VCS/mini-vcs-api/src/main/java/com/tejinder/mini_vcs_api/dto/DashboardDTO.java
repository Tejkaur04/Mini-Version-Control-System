package com.tejinder.mini_vcs_api.dto;

public class DashboardDTO {

    private int commits;
    private int branches;
    private int files;
    private String head;

    public DashboardDTO(
            int commits,
            int branches,
            int files,
            String head
    ) {
        this.commits = commits;
        this.branches = branches;
        this.files = files;
        this.head = head;
    }

    public int getCommits() {
        return commits;
    }

    public int getBranches() {
        return branches;
    }

    public int getFiles() {
        return files;
    }

    public String getHead() {
        return head;
    }
}