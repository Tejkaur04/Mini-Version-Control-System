package vcs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import vcs.core.Repository;

/**
 * Main class for the Mini VCS command-line interface.
 */
public class Main {
    private static final String USAGE = """
        Mini VCS - Available Commands:
        ---------------------------------
        init <directory>             Initialize a new repository
        add <file>                   Add a file to tracking
        commit <message>             Commit current tracked changes
        status                       Show repository status
        log                          Show commit history
        checkout <commitId>          Checkout a specific commit
        diff                         Show differences with HEAD
        help                         Show this help message
        """;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(USAGE);
            return;
        }

        String command = args[0];
        Repository repository = new Repository();

        try {
            // Handle 'init' separately as it does not require prior repository existence
            if (command.equals("init")) {
                if (args.length < 2) {
                    System.out.println("Usage: init <directory>");
                    return;
                }
                repository.init(args[1]);
                return;
            }

            // Load repository for all other commands
            String currentDir = Paths.get("").toAbsolutePath().toString();
            Path vcsDir = Paths.get(currentDir, ".mini-vcs");
            if (!Files.exists(vcsDir) || !Files.isDirectory(vcsDir)) {
                System.out.println("Repository not initialized. Run 'init <directory>' first.");
                return;
            }
            repository.load(currentDir);

            // Handle other commands
            switch (command) {
                case "add" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: add <file>");
                        return;
                    }
                    repository.add(args[1]);
                }
                case "commit" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: commit <message>");
                        return;
                    }
                    repository.commit(args[1]);
                }
                case "status" -> repository.status();
                case "log" -> repository.log();
                case "checkout" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: checkout <commitId>");
                        return;
                    }
                    repository.checkout(args[1]);
                }
                case "diff" -> repository.diff();
                case "help" -> System.out.println(USAGE);
                default -> {
                    System.out.println("Unknown command: " + command);
                    System.out.println(USAGE);
                }
            }
        } catch (IOException | IllegalStateException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
