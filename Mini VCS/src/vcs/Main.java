package vcs;

import java.io.IOException;
import java.nio.file.Paths;
import vcs.core.Repository;

public class Main {
    private static final String USAGE = """
        Mini VCS - Available Commands:
        ---------------------------------
        init <directory>                 Initialize a new repository
        add <file>                       Add a file to tracking
        commit <message>                 Commit current tracked changes
        status                           Show repository status
        log                              Show commit history
        checkout <commitId>              Checkout a specific commit
        diff                             Show differences with HEAD
        help                             Show this help message
        """;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(USAGE);
            return;
        }

        String command = args[0];
        String currentDir = Paths.get("").toAbsolutePath().toString();
        Repository repository = null;

        try {
            if (command.equals("init")) {
                if (args.length < 2) {
                    System.out.println("Usage: init <directory>");
                    return;
                }
                String path = args[1];
                Repository.getInstance(path).init(path);
                return;
            } else {
                repository = Repository.getInstance(currentDir); // Get the singleton instance
            }

            System.out.println("[DEBUG - Main] Command: " + command);
            System.out.println("[DEBUG - Main] Repository instance hash: " + System.identityHashCode(repository));

            switch (command) {
                case "add" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: add <file>");
                        return;
                    }
                    System.out.println("[DEBUG - Main - add] Adding file: " + args[1]);
                    repository.add(args[1]);
                }
                case "commit" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: commit <message>");
                        return;
                    }
                    System.out.println("[DEBUG - Main - commit] Committing with message: " + args[1]);
                    repository.commit(args[1]);
                }
                case "status" -> {
                    System.out.println("[DEBUG - Main - status] Checking status.");
                    repository.status();
                }
                case "log" -> {
                    System.out.println("[DEBUG - Main - log] Showing log.");
                    repository.log();
                }
                case "checkout" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: checkout <commitId>");
                        return;
                    }
                    System.out.println("[DEBUG - Main - checkout] Checking out commit: " + args[1]);
                    repository.checkout(args[1]);
                }
                case "diff" -> {
                    System.out.println("[DEBUG - Main - diff] Showing diff.");
                    repository.diff();
                }
                case "help" -> System.out.println(USAGE);
                default -> {
                    System.out.println("Unknown command: " + command);
                    System.out.println(USAGE);
                }
            }
        } catch (IOException | IllegalStateException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Optional: Add a debug message here to see when Main finishes
            System.out.println("[DEBUG - Main] Finished processing command: " + command);
        }
    }
}