package vcs.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

/**
 * Utility class for generating various types of hashes used in the version control system.
 */
public class HashUtils {
    
    /**
     * Generates a SHA-256 hash of the file content.
     *
     * @param filePath Path to the file
     * @return Hexadecimal string representation of the hash
     * @throws IOException If file cannot be read
     */
    public static String generateSHA256(String filePath) throws IOException {
        try {
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(fileData);
            
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Generates a SHA-256 hash of a string.
     *
     * @param content String content to hash
     * @return Hexadecimal string representation of the hash
     */
    public static String generateSHA256(byte[] content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(content);
            
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Generates a SHA-1 hash of the provided content.
     *
     * @param content String content to hash
     * @return Hexadecimal string representation of the hash
     */
    public static String generateSHA1(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = digest.digest(content.getBytes(StandardCharsets.UTF_8));
            
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not available", e);
        }
    }
    
    /**
     * Generates a CRC32 checksum of the file content.
     * Faster but less collision-resistant than cryptographic hashes.
     *
     * @param filePath Path to the file
     * @return CRC32 checksum as a long value
     * @throws IOException If file cannot be read
     */
    public static long generateCRC32(String filePath) throws IOException {
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));
        
        CRC32 crc = new CRC32();
        crc.update(fileData);
        
        return crc.getValue();
    }
    
    /**
     * Generates a simple hash code for a string.
     * Used for less critical operations where cryptographic security is not needed.
     *
     * @param input String to hash
     * @return Integer hash code
     */
    public static int simpleHash(String input) {
        int hash = 7;
        for (int i = 0; i < input.length(); i++) {
            hash = hash * 31 + input.charAt(i);
        }
        return hash;
    }
    
    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes Byte array to convert
     * @return Hexadecimal string
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    /**
     * Generates a commit ID based on timestamp and content.
     *
     * @param timestamp Commit timestamp
     * @param message Commit message
     * @param author Commit author
     * @return First 8 characters of the hash as a commit ID
     */
    public static String generateCommitId(long timestamp, String message, String author) {
        String content = timestamp + message + author;
        String fullHash = generateSHA1(content);
        
        // Return first 8 characters for readability (similar to Git)
        return fullHash.substring(0, 8);
    }
    
    /**
     * Generates a blob ID for file content.
     *
     * @param content File content
     * @return Hash of the file content, to be used as blob ID
     */
    public static String generateBlobId(String content) {
        return generateSHA1(content);
    }
}
