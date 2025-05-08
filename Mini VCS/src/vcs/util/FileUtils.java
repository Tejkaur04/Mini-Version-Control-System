package vcs.util;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling file operations in the version control system.
 */
public class FileUtils {
    
    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param filePath Path to the file to be read
     * @return String content of the file
     * @throws IOException If file cannot be read
     */
    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    /**
     * Writes content to a file.
     *
     * @param filePath Path to the file to write to
     * @param content Content to write to the file
     * @throws IOException If file cannot be written
     */
    public static void writeFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        
        // Create parent directories if they don't exist
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        }
    }
    
    /**
     * Copies a file from source to destination.
     *
     * @param source Source file path
     * @param destination Destination file path
     * @throws IOException If file cannot be copied
     */
    public static void copyFile(String source, String destination) throws IOException {
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        
        // Create parent directories if they don't exist
        if (destinationPath.getParent() != null) {
            Files.createDirectories(destinationPath.getParent());
        }
        
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
    
    /**
     * Deletes a file or directory.
     *
     * @param path Path to file or directory to delete
     * @return true if deletion was successful, false otherwise
     */
    public static boolean delete(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            return deleteDirectory(file);
        } else {
            return file.delete();
        }
    }
    
    /**
     * Recursively deletes a directory and all its contents.
     *
     * @param directory Directory to delete
     * @return true if deletion was successful, false otherwise
     */
    private static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return directory.delete();
    }
    
    /**
     * Lists all files in a directory and its subdirectories.
     *
     * @param directoryPath Path to the directory
     * @return List of file paths
     * @throws IOException If directory cannot be read
     */
    public static List<String> listAllFiles(String directoryPath) throws IOException {
        List<String> files = new ArrayList<>();
        Path start = Paths.get(directoryPath);
        
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                // Skip hidden files and VCS directory
                if (!file.toFile().isHidden() && !file.toString().contains(".vcs")) {
                    files.add(file.toString());
                }
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                // Skip VCS directory
                if (dir.getFileName().toString().equals(".vcs")) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }
        });
        
        return files;
    }
    
    /**
     * Creates a directory if it doesn't exist.
     *
     * @param directoryPath Path to directory
     * @return true if directory exists or was created, false otherwise
     */
    public static boolean createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            return directory.mkdirs();
        }
        return true;
    }
    
    /**
     * Checks if a file exists.
     *
     * @param filePath Path to file
     * @return true if file exists, false otherwise
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
    
    /**
     * Gets the file extension.
     *
     * @param filePath Path to file
     * @return File extension or empty string if no extension
     */
    public static String getFileExtension(String filePath) {
        int lastIndexOf = filePath.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // No extension
        }
        return filePath.substring(lastIndexOf + 1);
    }
    
    /**
     * Gets the relative path of a file from a base directory.
     *
     * @param basePath Base directory path
     * @param filePath Absolute file path
     * @return Relative path
     */
    public static String getRelativePath(String basePath, String filePath) {
        Path base = Paths.get(basePath).toAbsolutePath().normalize();
        Path path = Paths.get(filePath).toAbsolutePath().normalize();
        
        return base.relativize(path).toString();
    }
}
