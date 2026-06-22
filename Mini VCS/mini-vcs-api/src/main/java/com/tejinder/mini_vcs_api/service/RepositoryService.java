package com.tejinder.mini_vcs_api.service;

import com.tejinder.mini_vcs_api.dto.BranchDTO;
import com.tejinder.mini_vcs_api.dto.CommitDTO;
import com.tejinder.mini_vcs_api.dto.DashboardDTO;
import com.tejinder.mini_vcs_api.dto.FileDTO;
import com.tejinder.mini_vcs_api.dto.StatusDTO;
import com.tejinder.mini_vcs_api.vcs.core.Commit;
import com.tejinder.mini_vcs_api.vcs.core.Repository;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryService {

    public List<CommitDTO> getCommits() {

        List<CommitDTO> result
                = new ArrayList<>();

        try {

            Repository repo
                    = Repository.getInstance(
                            "D:/Projects/Mini-Version-Control-System/Mini VCS/myrepo"
                    );

            List<Commit> commits
                    = repo.getCommitHistory()
                            .getCommitHistory();

            for (Commit commit : commits) {

                result.add(
                        new CommitDTO(
                                commit.getId(),
                                commit.getMessage(),
                                commit.getParentId(),
                                commit.getTimestamp().toString(),
                                commit.getId().equals(
                                        repo.getHeadCommit().getId()
                                )
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    public List<BranchDTO> getBranches() {

        List<BranchDTO> result = new ArrayList<>();

        try {

            Repository repo
                    = Repository.getInstance(
                            "D:/Projects/Mini-Version-Control-System/Mini VCS/myrepo"
                    );

            List<String> branches
                    = repo.getCommitHistory().getBranches();

            String currentBranch
                    = repo.getCommitHistory().getCurrentBranch();

            for (String branch : branches) {

                result.add(
                        new BranchDTO(
                                branch,
                                repo.getCommitHistory()
                                        .getBranchHead(branch),
                                branch.equals(currentBranch), 0
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public StatusDTO getStatus() {

        return new StatusDTO(
                List.of(
                        "hello.txt",
                        "notes.txt"
                ),
                List.of(
                        "old.txt"
                ),
                List.of(
                        "temp.txt",
                        "draft.txt"
                )
        );
    }

    public List<FileDTO> getFiles() {

        List<FileDTO> result
                = new ArrayList<>();

        try {

            Repository repo
                    = Repository.getInstance(
                            "D:/Projects/Mini-Version-Control-System/Mini VCS/myrepo"
                    );

            Commit head
                    = repo.getHeadCommit();

            if (head == null) {
                return result;
            }

            for (String filePath : head.getFiles()) {

                result.add(
                        new FileDTO(
                                filePath,
                                head.getFileVersionId(filePath),
                                "--",
                                head.getMessage()
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    public DashboardDTO getDashboard() throws IOException {

        Repository repo
                    = Repository.getInstance(
                            "D:/Projects/Mini-Version-Control-System/Mini VCS/myrepo"
                    );

        int commits
                = repo.getCommitHistory()
                        .getAllCommits()
                        .size();

        int branches
                = repo.getCommitHistory()
                        .getBranches()
                        .size();

        int files =
                repo.getTrackedFileCount();

        String head
                = repo.getCommitHistory()
                        .getHeadCommit()
                        .getMessage();

        return new DashboardDTO(
                commits,
                branches,
                files,
                head
        );
    }
}
