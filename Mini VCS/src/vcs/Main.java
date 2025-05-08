package vcs;

import vcs.core.Repository;

import java.io.IOException;
import java.nio.file.Paths;

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
            switch (command) {
                case "init" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: init <directory>");
                        return;
                    }
                    repository.init(args[1]);
                }
                case "add" -> {
                    ensureRepositoryLoaded(repository);
                    if (args.length < 2) {
                        System.out.println("Usage: add <file>");
                        return;
                    }
                    repository.add(args[1]);
                }
                case "commit" -> {
                    ensureRepositoryLoaded(repository);
                    if (args.length < 2) {
                        System.out.println("Usage: commit <message>");
                        return;
                    }
                    repository.commit(args[1]);
                }
                case "status" -> {
                    ensureRepositoryLoaded(repository);
                    repository.status();
                }
                case "log" -> {
                    ensureRepositoryLoaded(repository);
                    repository.log();
                }
                case "checkout" -> {
                    ensureRepositoryLoaded(repository);
                    if (args.length < 2) {
                        System.out.println("Usage: checkout <commitId>");
                        return;
                    }
                    repository.checkout(args[1]);
                }
                case "diff" -> {
                    ensureRepositoryLoaded(repository);
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
        }
    }

    private static void ensureRepositoryLoaded(Repository repository) throws IOException {
        String currentDir = Paths.get("").toAbsolutePath().toString();
        repository.load(currentDir);
    }
}
