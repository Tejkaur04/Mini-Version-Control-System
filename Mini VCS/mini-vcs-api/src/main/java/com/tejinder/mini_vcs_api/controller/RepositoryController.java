package com.tejinder.mini_vcs_api.controller;

import org.springframework.web.bind.annotation.*;

import com.tejinder.mini_vcs_api.dto.BranchDTO;
import com.tejinder.mini_vcs_api.dto.CommitDTO;
import com.tejinder.mini_vcs_api.dto.DashboardDTO;
import com.tejinder.mini_vcs_api.dto.FileDTO;
import com.tejinder.mini_vcs_api.dto.StatusDTO;
import com.tejinder.mini_vcs_api.service.RepositoryService;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(
            RepositoryService repositoryService
    ) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/commits")
    public List<CommitDTO> getCommits() {
        return repositoryService.getCommits();
    }

    @GetMapping("/branches")
    public List<BranchDTO> getBranches() {
        return repositoryService.getBranches();
    }

    @GetMapping("/status")
    public StatusDTO getStatus() {
        return repositoryService.getStatus();
    }

    @GetMapping("/files")
    public List<FileDTO> getFiles() {
        return repositoryService.getFiles();
    }

    @GetMapping("/dashboard")
    public DashboardDTO getDashboard() throws IOException {
        return repositoryService.getDashboard();
    }
}
