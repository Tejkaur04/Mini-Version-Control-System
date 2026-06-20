package vcs.core;

import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;
import vcs.datastructures.HashTable;
import vcs.util.HashUtils;

public class Repository {

    private static Repository instance;
    private static String currentRootPath;

    private static final String VCS_DIR = ".mini-vcs";
    private static final String OBJECTS_DIR = VCS_DIR + "/objects";
    private static final String REFS_DIR = VCS_DIR + "/refs";
    private static final String HEAD_FILE = VCS_DIR + "/HEAD";
    private static final String INDEX_FILE = VCS_DIR + "/index";
    private static final String CURRENT_BRANCH_FILE = VCS_DIR + "/CURRENT_BRANCH";
    private CommitHistory commitHistory;
    private HashTable<String, File> trackedFiles;
    private Commit headCommit;

    private Repository() {
        this.commitHistory = new CommitHistory();
        this.trackedFiles = new HashTable<>();
    }

    public static Repository getInstance(String rootPath) throws IOException {
        if (instance == null) {
            instance = new Repository();
            currentRootPath = rootPath;
            instance.load(rootPath);
        } else if (currentRootPath != null && !currentRootPath.equals(rootPath)) {
            currentRootPath = rootPath;
            instance.load(rootPath);
        }
        return instance;
    }

    public static Repository getCurrentInstance() {
        return instance;
    }

    public void init(String path) throws IOException {
        this.currentRootPath = path;
        Path vcsPath = Paths.get(currentRootPath, VCS_DIR);
        if (Files.exists(vcsPath) && Files.isDirectory(vcsPath)) {
            System.out.println("Mini VCS repository already exists at " + vcsPath.toAbsolutePath());
            load(path);
            return;
        }

        Files.createDirectories(Paths.get(currentRootPath, OBJECTS_DIR));
        Files.createDirectories(Paths.get(currentRootPath, REFS_DIR));
        Files.write(Paths.get(currentRootPath, HEAD_FILE), new byte[0]);

        System.out.println("Initialized empty Mini VCS repository at " + vcsPath.toAbsolutePath());
        this.trackedFiles.clear();
        saveIndex();
    }

    public void load(String path) {
        this.currentRootPath = path;
        Path vcsPath = Paths.get(currentRootPath, VCS_DIR);
        if (!Files.exists(vcsPath) || !Files.isDirectory(vcsPath)) {
            this.trackedFiles.clear();
            this.commitHistory = new CommitHistory();
            this.headCommit = null;
            return;
        }

        try {
            loadIndex();

            this.commitHistory = new CommitHistory();

            loadBranches();

            Path headPath
                    = Paths.get(currentRootPath, HEAD_FILE);

            if (Files.exists(headPath)) {

                String headCommitId
                        = Files.readString(headPath).trim();

                if (!headCommitId.isEmpty()) {

                    rebuildHistory(headCommitId);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading repository data: " + e.getMessage());
            this.trackedFiles.clear();
            this.commitHistory = new CommitHistory();
            this.headCommit = null;
        }
    }

    private void rebuildHistory(String headCommitId)
            throws IOException {

        java.util.List<Commit> commits
                = new java.util.ArrayList<>();

        Commit current = loadCommit(headCommitId);

        while (current != null) {

            commits.add(current);

            if (current.getParent() == null) {
                break;
            }

            current = loadCommit(current.getParent());
        }

        java.util.Collections.reverse(commits);

        for (Commit commit : commits) {
            commitHistory.addCommit(commit);
        }

        this.headCommit = loadCommit(headCommitId);
    }

    public void add(String filePath) {
        try {
            if (!isRepositoryInitialized()) {
                System.out.println("Repository not initialized. Run 'init' first.");
                return;
            }
            addFileInternal(filePath);
        } catch (IOException e) {
            System.err.println("Failed to add file: " + e.getMessage());
        }
    }

    public void status() {
        if (!isRepositoryInitialized()) {
            System.out.println("Repository not initialized. Run 'init' first.");
            return;
        }
        try {
            statusInternal();
        } catch (IOException e) {
            System.err.println("Failed to check status: " + e.getMessage());
        }
    }

    public void inspectHead() {

        if (headCommit == null) {
            System.out.println("No HEAD commit.");
            return;
        }

        System.out.println("HEAD = " + headCommit.getId());

        for (String file : headCommit.getFiles()) {
            System.out.println(file);
        }
    }

    public void log() {
        if (!isRepositoryInitialized()) {
            System.out.println("Repository not initialized. Run 'init' first.");
            return;
        }
        System.out.println(commitHistory.getHistoryGraph());
    }
    

    private void deleteExtraFiles(Commit targetCommit)
            throws IOException {

        Path repoRoot
                = Paths.get(currentRootPath);

        Files.list(repoRoot)
                .filter(Files::isRegularFile)
                .forEach(path -> {

                    String fileName
                            = path.getFileName().toString();

                    boolean existsInCommit = false;

                    for (String commitFile
                            : targetCommit.getFiles()) {

                        if (commitFile.equals(fileName)) {

                            existsInCommit = true;
                            break;
                        }
                    }

                    if (!existsInCommit) {

                        try {

                            Files.delete(path);

                            System.out.println(
                                    "[CHECKOUT] Removed "
                                    + fileName
                            );

                        } catch (IOException e) {

                            System.err.println(
                                    "Failed to delete "
                                    + fileName
                            );
                        }
                    }
                });
    }

    public void commit(String message) {
        if (!isRepositoryInitialized()) {
            System.out.println("Repository not initialized. Run 'init' first.");
            return;
        }
        try {
            commitInternal(message);
        } catch (IOException e) {
            System.err.println("Failed to commit: " + e.getMessage());
        }
    }

    private boolean isRepositoryInitialized() {
        Path vcsDir = Paths.get(currentRootPath, VCS_DIR);
        return Files.exists(vcsDir) && Files.isDirectory(vcsDir);
    }

    private void addFileInternal(String filePath) throws IOException {
        Path fullPath = Paths.get(currentRootPath, filePath);
        if (!Files.exists(fullPath) || Files.isDirectory(fullPath)) {
            throw new IllegalArgumentException("File does not exist or is a directory: " + filePath);
        }

        byte[] content = Files.readAllBytes(fullPath);
        String hash = HashUtils.generateSHA1(new String(content));
        FileVersion fileVersion = new FileVersion(hash, content);

        saveFileVersion(fileVersion);

        File file = new File(filePath, fileVersion);
        trackedFiles.put(filePath, file);

        saveIndex();
        System.out.println("Added file: " + filePath);
    }

    private void commitInternal(String message) throws IOException {
        if (trackedFiles.isEmpty()) {
            throw new IllegalStateException("Nothing to commit.");
        }

        Commit commit = new Commit();
        commit.setMessage(message);
        commit.setTimestamp(new Date());

        commit.setParent(headCommit != null ? headCommit.getId() : null);

        // Copy parent snapshot first
        if (headCommit != null) {

            for (String filePath : headCommit.getFiles()) {

                commit.addFile(
                        filePath,
                        headCommit.getFileVersionId(filePath)
                );
            }
        }

// Apply staged changes on top
        for (String filePath : trackedFiles.keys()) {

            File file = trackedFiles.get(filePath);

            commit.addFile(
                    filePath,
                    file.getCurrentVersion().getHash()
            );
        }

        commit.generateId();
        saveCommit(commit);

        this.headCommit = commit;
        this.commitHistory.addCommit(commit);
        
        saveBranches();

        Files.write(Paths.get(currentRootPath, HEAD_FILE), commit.getId().getBytes());
        Files.deleteIfExists(Paths.get(currentRootPath, INDEX_FILE));
        trackedFiles.clear();

        System.out.println("Created commit: " + commit.getId() + " - " + message);
    }

    private void saveFileVersion(FileVersion version) throws IOException {
        Path objectPath = Paths.get(currentRootPath, OBJECTS_DIR, version.getHash());
        Files.write(objectPath, version.getContent());
    }

    private void saveCommit(Commit commit) throws IOException {
        Path commitPath
                = Paths.get(currentRootPath, OBJECTS_DIR, commit.getId());

        Files.write(
                commitPath,
                commit.serialize()
        );
    }

    private Commit loadCommit(String commitId) throws IOException {

        Path commitPath
                = Paths.get(currentRootPath,
                        OBJECTS_DIR,
                        commitId);

        if (!Files.exists(commitPath)) {
            return null;
        }

        byte[] data
                = Files.readAllBytes(commitPath);

        return Commit.deserialize(data);
    }

    private void saveIndex() throws IOException {
        Path indexPath = Paths.get(currentRootPath, INDEX_FILE);
        StringBuilder content = new StringBuilder();

        for (String path : trackedFiles.keys()) {
            File file = trackedFiles.get(path);
            content.append(path).append("=").append(file.getCurrentVersion().getHash()).append("\n");
        }

        Files.write(indexPath, content.toString().getBytes());
    }

    private void loadIndex() throws IOException {
        trackedFiles.clear();
        Path indexPath = Paths.get(currentRootPath, INDEX_FILE);
        if (!Files.exists(indexPath)) {
            return;
        }

        List<String> lines = Files.readAllLines(indexPath);
        for (String line : lines) {
            if (!line.contains("=")) {
                continue;
            }

            String[] parts = line.split("=", 2);
            String path = parts[0];
            String hash = parts[1];
            Path objectPath = Paths.get(currentRootPath, OBJECTS_DIR, hash);
            if (Files.exists(objectPath)) {
                byte[] content = Files.readAllBytes(objectPath);
                FileVersion version = new FileVersion(hash, content);
                File file = new File(path, version);
                trackedFiles.put(path, file);
            }
        }
    }

    public void diff() {
        System.out.println("Diff feature not yet implemented.");
    }

    private byte[] loadFileVersion(String hash) throws IOException {

        Path objectPath
                = Paths.get(
                        currentRootPath,
                        OBJECTS_DIR,
                        hash
                );

        if (!Files.exists(objectPath)) {
            throw new IOException(
                    "Missing object: " + hash
            );
        }

        return Files.readAllBytes(objectPath);
    }

    public void checkout(String commitId) {

        try {

            if (isBranch(commitId)) {

                String branchName = commitId;

                String branchHead
                        = commitHistory.getBranchHead(branchName);

                if (branchHead == null) {

                    System.out.println(
                            "Branch has no commits."
                    );

                    return;
                }

                commitHistory.switchBranch(branchName);

                saveBranches();

                System.out.println(
                        "Switching to branch: "
                        + branchName
                );

                commitId = branchHead;
            }

            Commit commit = loadCommit(commitId);
             if (commit == null) {

                System.out.println(
                        "Commit not found: "
                        + commitId
                );

                return;
            }

            System.out.println(
                    "Checking out commit: "
                    + commit.getId()
            );
            deleteExtraFiles(commit);

            // Restore all files from the commit
            for (String filePath : commit.getFiles()) {

                String hash
                        = commit.getFileVersionId(filePath);

                byte[] content
                        = loadFileVersion(hash);

                Path target
                        = Paths.get(
                                currentRootPath,
                                filePath
                        );

                Files.write(
                        target,
                        content
                );

                System.out.println(
                        "[CHECKOUT] Restored "
                        + filePath
                );
            }

            // Update HEAD
            Files.write(
                    Paths.get(
                            currentRootPath,
                            HEAD_FILE
                    ),
                    commit.getId().getBytes()
            );

            // Reload repository state
            load(currentRootPath);

            System.out.println(
                    "Checkout completed."
            );

        } catch (Exception e) {

            e.printStackTrace();

            System.err.println(
                    "Checkout failed: "
                    + e.getMessage()
            );
        }
    }

    
    
    // public void checkout(String commitId) {

    //     if (isBranch(commitId)) {

    //         String branchHead
    //                 = commitHistory.getBranchHead(commitId);

    //         if (branchHead == null) {

    //             System.out.println(
    //                     "Branch has no commits."
    //             );

    //             return;
    //         }

    //         String branchName = commitId;

    //         commitHistory.switchBranch(commitId);
    //         saveBranches();

    //         commitId = branchHead;

    //         System.out.println(
    //                 "Switching to branch: "
    //                 + branchName
    //         );
    //     }

    //     try {

    //         Commit commit = loadCommit(commitId);

    //         if (commit == null) {

    //             System.out.println(
    //                     "Commit not found: "
    //                     + commitId
    //             );

    //             return;
    //         }

    //         System.out.println(
    //                 "Checking out commit: "
    //                 + commit.getId()
    //         );
    //         deleteExtraFiles(commit);

    //         // Restore all files from the commit
    //         for (String filePath : commit.getFiles()) {

    //             String hash
    //                     = commit.getFileVersionId(filePath);

    //             byte[] content
    //                     = loadFileVersion(hash);

    //             Path target
    //                     = Paths.get(
    //                             currentRootPath,
    //                             filePath
    //                     );

    //             Files.write(
    //                     target,
    //                     content
    //             );

    //             System.out.println(
    //                     "[CHECKOUT] Restored "
    //                     + filePath
    //             );
    //         }

    //         // Update HEAD
    //         Files.write(
    //                 Paths.get(
    //                         currentRootPath,
    //                         HEAD_FILE
    //                 ),
    //                 commit.getId().getBytes()
    //         );

    //         // Reload repository state
    //         load(currentRootPath);

    //         System.out.println(
    //                 "Checkout completed."
    //         );

    //     } catch (Exception e) {

    //         e.printStackTrace();

    //         System.err.println(
    //                 "Checkout failed: "
    //                 + e.getMessage()
    //         );
    //     }
    // }

    private void statusInternal() throws IOException {

        if (headCommit == null) {
            System.out.println("No commits yet.");
            return;
        }

        boolean clean = true;

        for (String filePath : headCommit.getFiles()) {

            String storedHash
                    = headCommit.getFileVersionId(filePath);

            Path workingFile
                    = Paths.get(currentRootPath, filePath);

            if (!Files.exists(workingFile)) {

                System.out.println(
                        "Deleted: " + filePath);

                clean = false;
                continue;
            }

            byte[] content
                    = Files.readAllBytes(workingFile);

            String currentHash
                    = HashUtils.generateSHA1(
                            new String(content));

            if (!currentHash.equals(storedHash)) {

                System.out.println(
                        "Modified: " + filePath);

                clean = false;
            }
        }

        if (clean) {
            System.out.println("Working tree clean");
        }
    }

    private void saveBranches()
            throws IOException {

        Path refsDir
                = Paths.get(
                        currentRootPath,
                        REFS_DIR
                );

        Files.createDirectories(refsDir);

        for (String branch
                : commitHistory.getBranches()) {

            String head
                    = commitHistory.getBranchHead(branch);

            if (head == null) {
                head = "";
            }

            Files.write(
                    refsDir.resolve(branch),
                    head.getBytes()
            );
        }

        Files.write(
                Paths.get(
                        currentRootPath,
                        CURRENT_BRANCH_FILE
                ),
                commitHistory.getCurrentBranch()
                        .getBytes()
        );
    }

    public void createBranch(String branchName) {

        try {

            if (commitHistory.createBranch(branchName)) {

                saveBranches();

                System.out.println(
                        "Created branch: "
                        + branchName
                );

            } else {

                System.out.println(
                        "Branch already exists."
                );
            }

        } catch (IOException e) {

            System.err.println(
                    "Failed to save branches: "
                    + e.getMessage()
            );
        }
    }

    // public void listBranches() {
    //     for (String branch
    //             : commitHistory.getBranches()) {
    //         if (branch.equals(
    //                 commitHistory.getCurrentBranch()
    //         )) {
    //             System.out.println(
    //                     "* " + branch
    //             );
    //         } else {
    //             System.out.println(
    //                     "  " + branch
    //             );
    //         }
    //     }
    // }
    public void listBranches() {

        for (String branch
                : commitHistory.getBranches()) {

            System.out.println(
                    branch
                    + " -> "
                    + commitHistory.getBranchHead(branch)
            );
        }
    }

    private void loadBranches() throws IOException {

        Path refsDir
                = Paths.get(
                        currentRootPath,
                        REFS_DIR
                );

        if (!Files.exists(refsDir)) {
            return;
        }

        for (Path branchFile
                : Files.list(refsDir).toList()) {

            String branchName
                    = branchFile.getFileName().toString();

            String commitId
                    = Files.readString(branchFile).trim();

            commitHistory.createBranch(branchName);
            commitHistory.setBranchHead(
                    branchName,
                    commitId.isEmpty()
                    ? null
                    : commitId
            );

            Path currentBranchFile
                    = Paths.get(
                            currentRootPath,
                            CURRENT_BRANCH_FILE
                    );

            if (Files.exists(currentBranchFile)) {

                String currentBranch
                        = Files.readString(currentBranchFile)
                                .trim();

                commitHistory.switchBranch(currentBranch);
            }
        }
    }

    private boolean isBranch(String name) {

        return commitHistory.getBranches()
                .contains(name);
    }

}
