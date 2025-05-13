package vcs.core;

import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;
import vcs.datastructures.HashTable;
import vcs.util.HashUtils;

public class Repository {
    private static Repository instance;
    private static String currentRootPath; // To store the current repository root

    private static final String VCS_DIR = ".mini-vcs";
    private static final String OBJECTS_DIR = VCS_DIR + "/objects";
    private static final String REFS_DIR = VCS_DIR + "/refs";
    private static final String HEAD_FILE = VCS_DIR + "/HEAD";
    private static final String INDEX_FILE = VCS_DIR + "/index";

    private CommitHistory commitHistory;
    private HashTable<String, File> trackedFiles;
    private Commit headCommit;

    private Repository() {
        this.commitHistory = new CommitHistory();
        this.trackedFiles = new HashTable<>();
        System.out.println("[DEBUG - Repository] Repository instance created.");
    }

    public static Repository getInstance(String rootPath) throws IOException {
        System.out.println("[DEBUG - Repository - getInstance] Requested with rootPath: " + rootPath);
        if (instance == null || (currentRootPath != null && !currentRootPath.equals(rootPath))) {
            System.out.println("[DEBUG - Repository - getInstance] Creating new instance or root path changed.");
            instance = new Repository();
            instance.load(rootPath); // Load repository data if it exists
            currentRootPath = rootPath;
        } else {
            System.out.println("[DEBUG - Repository - getInstance] Returning existing instance.");
        }
        return instance;
    }

    public static Repository getCurrentInstance() {
        return instance;
    }

    public void init(String path) throws IOException {
        this.currentRootPath = path;
        Path vcsPath = Paths.get(currentRootPath, VCS_DIR);
        System.out.println("[DEBUG - Repository - init] Initializing repository at: " + path);
        if (Files.exists(vcsPath) && Files.isDirectory(vcsPath)) {
            System.out.println("Mini VCS repository already exists at " + vcsPath.toAbsolutePath());
            load(path); // Load existing repository
            return;
        }

        Files.createDirectories(Paths.get(currentRootPath, OBJECTS_DIR));
        Files.createDirectories(Paths.get(currentRootPath, REFS_DIR));
        Files.write(Paths.get(currentRootPath, HEAD_FILE), new byte[0]);

        System.out.println("Initialized empty Mini VCS repository at " + vcsPath.toAbsolutePath());
        this.trackedFiles.clear(); // Start with a clean staging area
        saveIndex();
        System.out.println("[DEBUG - Repository - init] Finished initialization.");
    }

    public void load(String path) {
        this.currentRootPath = path;
        System.out.println("[DEBUG - Repository - load] Loading repository from: " + path);
        Path vcsPath = Paths.get(currentRootPath, VCS_DIR);
        if (!Files.exists(vcsPath) || !Files.isDirectory(vcsPath)) {
            System.out.println("[DEBUG - Repository - load] Repository not initialized.");
            this.trackedFiles.clear();
            this.commitHistory = new CommitHistory();
            this.headCommit = null;
            System.out.println("[DEBUG - Repository - load] Finished loading (not initialized).");
            return;
        }

        try {
            loadIndex(); // Load staged files
            System.out.println("[DEBUG - Repository - load] Index loaded. trackedFiles size: " + this.trackedFiles.size());
            // TODO: Load commit history and HEAD
            this.commitHistory = new CommitHistory(); // Placeholder for loading
            this.headCommit = commitHistory.getHeadCommit(); // Placeholder
        } catch (IOException e) {
            System.err.println("Error loading repository data: " + e.getMessage());
            this.trackedFiles.clear();
            this.commitHistory = new CommitHistory();
            this.headCommit = null;
            System.out.println("[DEBUG - Repository - load] Error during loadIndex: " + e.getMessage());
        }
        System.out.println("[DEBUG - Repository - load] Finished loading.");
    }

    public void add(String filePath) {
        try {
            if (!isRepositoryInitialized()) {
                System.out.println("Repository not initialized. Run 'init' first.");
                return;
            }
            addFile(filePath);
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

    public void log() {
        if (!isRepositoryInitialized()) {
            System.out.println("Repository not initialized. Run 'init' first.");
            return;
        }
        System.out.println(commitHistory.getHistoryGraph());
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

    private void addFile(String filePath) throws IOException {
        Path fullPath = Paths.get(currentRootPath, filePath);
        System.out.println("[DEBUG - Repository - addFile] Adding file: " + filePath);
        if (!Files.exists(fullPath) || Files.isDirectory(fullPath)) {
            throw new IllegalArgumentException("File does not exist or is a directory: " + filePath);
        }

        byte[] content = Files.readAllBytes(fullPath);
        String hash = HashUtils.generateSHA1(new String(content));
        FileVersion fileVersion = new FileVersion(hash, content);

        saveFileVersion(fileVersion);

        File file = new File(filePath, fileVersion);
        trackedFiles.put(filePath, file);
        System.out.println("[DEBUG - Repository - addFile] File added to trackedFiles. Size: " + trackedFiles.size());

        saveIndex();
        System.out.println("[DEBUG - Repository - addFile] Index saved.");
        System.out.println("Added file: " + filePath);
    }

    private void commitInternal(String message) throws IOException {
        System.out.println("[DEBUG - Repository - commitInternal] Starting commit. trackedFiles size: " + trackedFiles.size());
        System.out.println("[DEBUG - Repository - commitInternal] Contents of trackedFiles:");
        for (String key : trackedFiles.keys()) {
            System.out.println("[DEBUG - Repository - commitInternal]   - " + key);
        }

        if (trackedFiles.isEmpty()) {
            throw new IllegalStateException("Nothing to commit.");
        }

        Commit commit = new Commit();
        commit.setMessage(message);
        commit.setTimestamp(new Date());
        commit.setParent(headCommit != null ? headCommit.getId() : null);

        for (String filePath : trackedFiles.keys()) {
            File file = trackedFiles.get(filePath);
            commit.addFile(filePath, file.getCurrentVersion().getHash());
        }

        commit.generateId();
        saveCommit(commit);
        System.out.println("[DEBUG - Repository - commitInternal] Commit saved.");

        this.headCommit = commit;
        this.commitHistory.addCommit(commit);

        Files.write(Paths.get(currentRootPath, HEAD_FILE), commit.getId().getBytes());
        Files.deleteIfExists(Paths.get(currentRootPath, INDEX_FILE)); // Clear the staging index
        trackedFiles.clear(); // Clear staged files after commit
        System.out.println("[DEBUG - Repository - commitInternal] Index cleared and trackedFiles cleared.");

        System.out.println("Created commit: " + commit.getId() + " - " + message);
    }

    private void saveFileVersion(FileVersion version) throws IOException {
        Path objectPath = Paths.get(currentRootPath, OBJECTS_DIR, version.getHash());
        Files.write(objectPath, version.getContent());
        System.out.println("[DEBUG - Repository - saveFileVersion] File version saved: " + version.getHash());
    }

    private void saveCommit(Commit commit) {
        // TODO: Serialize the commit object and save under OBJECTS_DIR
        System.out.println("[DEBUG - Repository - saveCommit] Saving commit: " + commit.getId());
    }

    private void saveIndex() throws IOException {
        Path indexPath = Paths.get(currentRootPath, INDEX_FILE);
        StringBuilder content = new StringBuilder();

        for (String path : trackedFiles.keys()) {
            File file = trackedFiles.get(path);
            content.append(path).append("=").append(file.getCurrentVersion().getHash()).append("\n");
        }

        Files.write(indexPath, content.toString().getBytes());
        System.out.println("[DEBUG - Repository - saveIndex] Index file written.");
    }

    private void loadIndex() throws IOException {
        trackedFiles.clear();
        Path indexPath = Paths.get(currentRootPath, INDEX_FILE);
        System.out.println("[DEBUG - Repository - loadIndex] Loading index from: " + indexPath);
        if (!Files.exists(indexPath)) {
            System.out.println("[DEBUG - Repository - loadIndex] Index file does not exist.");
            return;
        }

        List<String> lines = Files.readAllLines(indexPath);
        System.out.println("[DEBUG - Repository - loadIndex] Read " + lines.size() + " lines from index.");
        for (String line : lines) {
            if (!line.contains("=")) continue;

            String[] parts = line.split("=", 2);
            String path = parts[0];
            String hash = parts[1];
            Path objectPath = Paths.get(currentRootPath, OBJECTS_DIR, hash);
            if (Files.exists(objectPath)) {
                byte[] content = Files.readAllBytes(objectPath);
                FileVersion version = new FileVersion(hash, content);
                File file = new File(path, version);
                trackedFiles.put(path, file);
                System.out.println("[DEBUG - Repository - loadIndex] Added to trackedFiles: " + path + " with hash " + hash);
            } else {
                System.out.println("[DEBUG - Repository - loadIndex] Object file not found for: " + path + " with hash " + hash);
            }
        }
        System.out.println("[DEBUG - Repository - loadIndex] Finished loading index. trackedFiles size: " + trackedFiles.size());
    }

    public void diff() {
        System.out.println("Diff feature not yet implemented.");
    }

    public void checkout(String commitId) {
        System.out.println("Checkout feature not yet implemented.");
    }

    private void statusInternal() throws IOException {
        // TODO: Compare working directory with HEAD commit files and index
        System.out.println("Status check not yet implemented.");
    }
}