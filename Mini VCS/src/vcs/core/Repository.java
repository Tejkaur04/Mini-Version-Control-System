package vcs.core;

import vcs.datastructures.HashTable;
import vcs.util.HashUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.Date;

/**
 * Repository class that handles the main functionality of the version control system.
 */
public class Repository {
    private static final String VCS_DIR = ".mini-vcs";
    private static final String OBJECTS_DIR = VCS_DIR + "/objects";
    private static final String REFS_DIR = VCS_DIR + "/refs";
    private static final String HEAD_FILE = VCS_DIR + "/HEAD";

    private String rootPath;
    private CommitHistory commitHistory;
    private HashTable<String, File> trackedFiles;
    private Commit headCommit;

    public Repository() {
        this.rootPath = System.getProperty("user.dir");
        this.commitHistory = new CommitHistory();
        this.trackedFiles = new HashTable<>();
    }

    public void init() {
        try {
            init(this.rootPath);
        } catch (IOException e) {
            System.err.println("Failed to initialize repository: " + e.getMessage());
        }
    }

    public void init(String path) throws IOException {
        this.rootPath = path;

        Files.createDirectories(Paths.get(rootPath, OBJECTS_DIR));
        Files.createDirectories(Paths.get(rootPath, REFS_DIR));
        Files.write(Paths.get(rootPath, HEAD_FILE), new byte[0]);  // Empty HEAD initially

        System.out.println("Initialized empty Mini VCS repository at " +
                Paths.get(rootPath, VCS_DIR).toAbsolutePath());
    }

    public void load(String path) {
        this.rootPath = path;
        this.commitHistory = new CommitHistory(); // TODO: deserialize commit history
        this.trackedFiles = new HashTable<>();
        this.headCommit = commitHistory.getHeadCommit(); // If we persist commits, restore HEAD properly
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
        Path vcsDir = Paths.get(rootPath, VCS_DIR);
        return Files.exists(vcsDir) && Files.isDirectory(vcsDir);
    }

    private void addFile(String filePath) throws IOException {
        Path fullPath = Paths.get(rootPath, filePath);
        if (!Files.exists(fullPath) || Files.isDirectory(fullPath)) {
            throw new IllegalArgumentException("File does not exist or is a directory: " + filePath);
        }

        byte[] content = Files.readAllBytes(fullPath);
        String hash = HashUtils.generateSHA1(new String(content));
        FileVersion fileVersion = new FileVersion(hash, content);

        saveFileVersion(fileVersion);

        File file = new File(filePath, fileVersion);
        trackedFiles.put(filePath, file);

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

        for (String filePath : trackedFiles.keys()) {
            File file = trackedFiles.get(filePath);
            commit.addFile(filePath, file.getCurrentVersion().getHash());
        }

        commit.generateId();
        saveCommit(commit);

        this.headCommit = commit;
        this.commitHistory.addCommit(commit);

        Files.write(Paths.get(rootPath, HEAD_FILE), commit.getId().getBytes());

        System.out.println("Created commit: " + commit.getId() + " - " + message);
    }

    private void saveFileVersion(FileVersion version) throws IOException {
        // Persist file content under its hash in the object directory
        Path objectPath = Paths.get(rootPath, OBJECTS_DIR, version.getHash());
        Files.write(objectPath, version.getContent());
    }

    private void saveCommit(Commit commit) {
        // TODO: Serialize the commit object and save under OBJECTS_DIR
        // Path path = Paths.get(rootPath, OBJECTS_DIR, commit.getId());
        // Files.write(path, serialize(commit));
    }

    public void diff() {
        // TODO: Compare last two commits and show changes
        System.out.println("Diff feature not yet implemented.");
    }

    public void checkout(String commitId) {
        // TODO: Restore tracked file state from the given commit
        System.out.println("Checkout feature not yet implemented.");
    }

    private void statusInternal() throws IOException {
        // TODO: Compare working directory with HEAD commit files
        System.out.println("Status check not yet implemented.");
    }
}
