package vcs.core;

import vcs.datastructures.LinkedList;

/**
 * Represents a file tracked by the version control system.
 * Keeps track of the file's path and version history.
 */
public class File {
    private String path;
    private FileVersion currentVersion;
    private LinkedList<FileVersion> versionHistory;

    /**
     * Creates a new file with the given path and initial version
     * @param path Path of the file relative to repository root
     * @param initialVersion Initial version of the file
     */
    public File(String path, FileVersion initialVersion) {
        this.path = path;
        this.currentVersion = initialVersion;
        this.versionHistory = new LinkedList<>();
        this.versionHistory.add(initialVersion);
    }

    /**
     * Gets the path of the file
     * @return The file path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the current version of the file
     * @return The current file version
     */
    public FileVersion getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Updates the current version of the file
     * @param newVersion The new file version
     */
    public void updateVersion(FileVersion newVersion) {
        this.currentVersion = newVersion;
        this.versionHistory.add(newVersion);
    }

    /**
     * Gets all versions of the file
     * @return List of all file versions
     */
    public LinkedList<FileVersion> getVersionHistory() {
        return versionHistory;
    }

    /**
     * Gets a specific version of the file by index
     * @param index Index of the version to get
     * @return The file version at the specified index
     */
    public FileVersion getVersion(int index) {
        return versionHistory.get(index);
    }

    /**
     * Gets the number of versions for this file
     * @return The number of versions
     */
    public int getVersionCount() {
        return versionHistory.size();
    }

    /**
     * Checks if this file has the same path as another file
     * @param obj The other file to compare with
     * @return true if the files have the same path
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof File)) return false;
        File otherFile = (File) obj;
        return this.path.equals(otherFile.path);
    }

    /**
     * Returns a hash code for this file based on its path
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return path.hashCode();
    }

    /**
     * Returns a string representation of this file
     * @return String representation
     */
    @Override
    public String toString() {
        return "File{path='" + path + "', currentVersion=" + currentVersion.getHash() + "}";
    }
}
