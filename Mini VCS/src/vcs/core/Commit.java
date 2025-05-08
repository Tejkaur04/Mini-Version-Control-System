package vcs.core;

import vcs.datastructures.HashTable;
import vcs.util.HashUtils;

import java.io.*;
import java.util.Date;

/**
 * Represents a commit in the version control system.
 * A commit captures the state of tracked files at a specific point in time.
 */
public class Commit {
    private String id;
    private String message;
    private Date timestamp;
    private String parent;
    private HashTable<String, String> fileVersions; // Maps file paths to file version hashes

    /**
     * Creates a new commit.
     */
    public Commit() {
        this.fileVersions = new HashTable<>();
        this.timestamp = new Date();
    }

    /**
     * Creates a commit with a specified message and parent commit ID.
     * @param message The commit message.
     * @param parent  The ID of the parent commit (can be null for the initial commit).
     */
    public Commit(String message, String parent) {
        this();
        this.message = message;
        this.parent = parent;
        generateId(); // Generate ID after setting message and parent
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentId() {
        return parent;  // Added this method to match `CommitHistory` expectations
    }

    public void addFile(String filePath, String versionHash) {
        fileVersions.put(filePath, versionHash);
    }

    public Iterable<String> getFiles() {
        return fileVersions.keys();
    }

    public String getFileVersionId(String filePath) {
        return fileVersions.get(filePath);
    }

    /**
     * Generates a unique ID for this commit based on its contents.
     */
    public void generateId() {
        StringBuilder sb = new StringBuilder();
        sb.append(message != null ? message : "").append("\n");
        sb.append(timestamp.getTime()).append("\n");

        if (parent != null) {
            sb.append(parent).append("\n");
        }

        for (String filePath : fileVersions.keys()) {
            sb.append(filePath).append("=").append(fileVersions.get(filePath)).append("\n");
        }

        this.id = HashUtils.generateSHA1(sb.toString());
    }

    /**
     * Serializes this commit to a byte array.
     * @return Byte array representation of this commit.
     * @throws IOException If serialization fails.
     */
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(id != null ? id : "");
        dos.writeUTF(message != null ? message : "");
        dos.writeLong(timestamp.getTime());
        dos.writeUTF(parent != null ? parent : "");

        int count = fileVersions.size();
        dos.writeInt(count);
        for (String filePath : fileVersions.keys()) {
            dos.writeUTF(filePath);
            dos.writeUTF(fileVersions.get(filePath));
        }

        dos.flush();
        return baos.toByteArray();
    }

    /**
     * Deserializes a commit from a byte array.
     * @param data Byte array to deserialize.
     * @return The deserialized commit.
     * @throws IOException If deserialization fails.
     */
    public static Commit deserialize(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);

        Commit commit = new Commit();

        commit.id = dis.readUTF();
        commit.message = dis.readUTF();
        commit.timestamp = new Date(dis.readLong());

        String parentId = dis.readUTF();
        commit.parent = parentId.isEmpty() ? null : parentId;

        int fileCount = dis.readInt();
        for (int i = 0; i < fileCount; i++) {
            String filePath = dis.readUTF();
            String versionHash = dis.readUTF();
            commit.fileVersions.put(filePath, versionHash);
        }

        return commit;
    }

    @Override
    public String toString() {
        return "Commit{" +
               "id='" + id + '\'' +
               ", message='" + message + '\'' +
               ", timestamp=" + timestamp +
               ", parent='" + parent + '\'' +
               ", fileCount=" + fileVersions.size() +
               '}';
    }
}
