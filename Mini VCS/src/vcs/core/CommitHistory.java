package vcs.core;

import java.io.Serializable;
import java.util.*;

/**
 * Manages the history of commits in the repository.
 * Provides methods to navigate through the commit history.
 */
public class CommitHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Commit> commits;
    private Map<String, String> branches;
    private String currentBranch;
    private String headCommitId;

    public CommitHistory() {
        this.commits = new HashMap<>();
        this.branches = new HashMap<>();
        this.currentBranch = "master";
        this.branches.put(currentBranch, null);
        this.headCommitId = null;
    }

    public void addCommit(Commit commit) {
        commits.put(commit.getId(), commit);
        branches.put(currentBranch, commit.getId());
        headCommitId = commit.getId();
    }

    public Commit getCommit(String commitId) {
        return commits.get(commitId);
    }

    public Commit getHeadCommit() {
        return headCommitId != null ? commits.get(headCommitId) : null;
    }

    // Get the parent commit ID of a commit
    public String getParentId(String commitId){
        Commit commit = commits.get(commitId);
        return commit != null ? commit.getParentId() : null;
    }

    public List<Commit> getAllCommits() {
        return new ArrayList<>(commits.values());
    }

    public List<Commit> getCommitHistory() {
        List<Commit> history = new ArrayList<>();
        String currentId = headCommitId;

        while (currentId != null) {
            Commit commit = commits.get(currentId);
            if (commit == null) break;

            history.add(0, commit);
            currentId = commit.getParentId();  // This now fetches from Commit's parentId
        }

        return history;
    }

    public String getHeadCommitId() {
        return headCommitId;
    }

    public boolean isEmpty() {
        return commits.isEmpty();
    }

    public boolean createBranch(String branchName) {
        if (branches.containsKey(branchName)) return false;
        branches.put(branchName, headCommitId);
        return true;
    }

    public boolean switchBranch(String branchName) {
        if (!branches.containsKey(branchName)) return false;
        currentBranch = branchName;
        headCommitId = branches.get(branchName);
        return true;
    }

    public String getCurrentBranch() {
        return currentBranch;
    }

    public List<String> getBranches() {
        return new ArrayList<>(branches.keySet());
    }

    public String getBranchHead(String branchName) {
        return branches.get(branchName);
    }

    public boolean checkout(String commitId) {
        if (!commits.containsKey(commitId)) return false;
        headCommitId = commitId;
        return true;
    }

    public Commit mergeBranch(String branchName) {
        if (!branches.containsKey(branchName)) return null;

        String branchHeadId = branches.get(branchName);
        if (branchHeadId == null) return null;

        Commit branchHead = commits.get(branchHeadId);
        Commit currentHead = getHeadCommit();

        if (currentHead == null) {
            headCommitId = branchHeadId;
            branches.put(currentBranch, headCommitId);
            return branchHead;
        }

        // Updated Commit constructor call with correct parameters
        Commit mergeCommit = new Commit(
            "Merge branch '" + branchName + "' into " + currentBranch,
            currentHead.getId()
        );
        
        // The mergeCommit should have the current commit and branch commit as parents
        mergeCommit.setParent(currentHead.getId());

        addCommit(mergeCommit);
        return mergeCommit;
    }

    public String getHistoryGraph() {
        StringBuilder graph = new StringBuilder();
        List<Commit> history = getCommitHistory();

        for (Commit commit : history) {
            String commitId = commit.getId();
            String shortId = commitId != null && commitId.length() >= 7
                ? commitId.substring(0, 7)
                : commitId;

            StringBuilder branchInfo = new StringBuilder();
            for (Map.Entry<String, String> branch : branches.entrySet()) {
                if (commitId.equals(branch.getValue())) {
                    branchInfo.append(" [").append(branch.getKey()).append("]");
                }
            }

            graph.append(shortId)
                 .append(branchInfo)
                 .append(" - ")
                 .append(commit.getMessage())
                 .append(" (")
                 .append(commit.getTimestamp())
                 .append(")\n");
        }

        return graph.toString();
    }

    @Override
    public String toString() {
        return "CommitHistory[commits=" + commits.size() +
               ", currentBranch=" + currentBranch + "]";
    }
}
