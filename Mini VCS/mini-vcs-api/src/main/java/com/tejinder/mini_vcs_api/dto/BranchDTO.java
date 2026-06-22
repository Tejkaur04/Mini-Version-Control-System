package com.tejinder.mini_vcs_api.dto;

public class BranchDTO {

    private String name;
    private String head;
    private boolean current;
    private int commits;

    public BranchDTO(
            String name,
            String head,
            boolean current,
            int commits
    ) {
        this.name = name;
        this.head = head;
        this.current = current;
        this.commits = commits;
    }

    public String getName() { return name; }
    public String getHead() { return head; }
    public boolean isCurrent() { return current; }
    public int getCommits() { return commits; }
}