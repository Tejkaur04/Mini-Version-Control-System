package vcs;

import java.io.IOException;
import java.nio.file.Paths;
import vcs.core.Repository;

public class Main {

    private static final String USAGE = """
    Mini VCS - Available Commands:
    ---------------------------------
    init <directory>                  Initialize a new repository
    add <file>                        Add a file to tracking
    commit <message>                  Commit staged changes
    status                            Show repository status
    log                               Show commit history

    checkout <commitId>               Checkout a commit
    checkout <branchName>             Switch to a branch

    branch                            List all branches
    branch <name>                     Create a new branch

    diff                              Show differences with HEAD
    help                              Show this help message
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
                repository = Repository.getInstance(currentDir);
            }

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
                case "status" ->
                    repository.status();
                case "log" ->
                    repository.log();
                case "checkout" -> {
                    if (args.length < 2) {
                        System.out.println("Usage: checkout <commitId>");
                        return;
                    }
                    repository.checkout(args[1]);
                }
                case "branch" -> {
                    if (args.length == 1) {
                        repository.listBranches();
                    } else {
                        repository.createBranch(args[1]);
                    }
                }
                case "diff" ->
                    repository.diff();
                case "help" ->
                    System.out.println(USAGE);
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
