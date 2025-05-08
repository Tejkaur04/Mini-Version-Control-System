package vcs.core;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a specific version of a file in the version control system.
 * Each version is uniquely identified by a hash of its content.
 */
public class FileVersion {
    private final String hash;
    private final byte[] content;

    /**
     * Creates a new file version with the given hash and content.
     * Performs a defensive copy of the content to prevent external mutation.
     * @param hash Hash of the file content
     * @param content Content of the file
     */
    public FileVersion(String hash, byte[] content) {
        this.hash = hash;
        this.content = Arrays.copyOf(content, content.length);
    }

    /**
     * Gets the hash of this file version.
     * @return The hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * Gets a copy of the content of this file version.
     * @return A defensive copy of the content as a byte array
     */
    public byte[] getContent() {
        return Arrays.copyOf(content, content.length);
    }

    /**
     * Gets the content of this file version as a string.
     * @return The content as a string
     */
    public String getContentAsString() {
        return new String(content);
    }

    /**
     * Gets the size of this file version in bytes.
     * @return The size in bytes
     */
    public int getSize() {
        return content.length;
    }

    /**
     * Checks if this file version is equal to another one.
     * @param other The other file version to compare with
     * @return true if the file versions have the same hash and content
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof FileVersion)) return false;
        FileVersion that = (FileVersion) other;
        return Objects.equals(this.hash, that.hash) && Arrays.equals(this.content, that.content);
    }

    /**
     * Returns a hash code for this file version based on its hash and content.
     * @return The hash code
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(hash);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    /**
     * Returns a string representation of this file version.
     * @return String representation
     */
    @Override
    public String toString() {
        return "FileVersion{hash='" + hash + "', size=" + content.length + " bytes}";
    }
}
