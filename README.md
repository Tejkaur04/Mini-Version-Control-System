# 📁 Mini Version Control System

A simplified version control system built in Java to mimic the core functionalities of Git. This project is designed for educational purposes and demonstrates how core VCS features like commits, version tracking, and file diffs can be implemented from scratch.

---

## 🚀 Features

- Track and version files using a local `.mini-vcs` repository
- Custom-built data structures: `LinkedList`, `BinarySearchTree`, `HashTable`, etc.
- File version comparison and diff tool
- Undo/redo functionality using stacks and queues
- Command Line Interface (CLI) with command parsing

---
## 📚 Data Structures Used
| Data Structure        | Purpose                                         |
|-----------------------|-------------------------------------------------|
| `LinkedList`          | Maintains a sequential list of commits (commit history). |
| `BinarySearchTree`    | Efficient storage and retrieval of file versions. |
| `HashTable`           | Fast file tracking by mapping filenames to metadata. |
| `Stack`               | Enables undo/redo operations during commit traversal. |
| `Queue`               | Manages the commit process in a FIFO manner.   |

These structures are implemented from scratch in `src/vcs/datastructures`.

---

## 🛠️ Setup Instructions

## ✅ Prerequisites

- Java JDK 8 or higher installed
- Command line access (Windows, macOS, or Linux)
- Git (optional for cloning)

---

## 📦 Clone the Repository

```bash
git clone https://github.com/your-username/Mini-Version-Control-System.git
cd Mini-Version-Control-System
```

---
## ⚙️ Compile the Project

```bash
javac -d out/ src/vcs/Main.java src/vcs/core/*.java src/vcs/datastructures/*.java src/vcs/util/*.java
```
If you are on Windows and want to compile everything automatically in PowerShell:

```bash
run.bat
```

---

## ▶️ Run the Project

```bash
java -cp out vcs.Main
```

---

## 💡 Example Commands

Inside the CLI, you can run commands like:
```pgsql
init               - Initialize a new repository
add <filename>     - Track a new file
commit <message>   - Create a commit snapshot
log                - Show commit history
status             - Display tracked files
diff <file>        - Show differences in versions
undo               - Revert to previous state
redo               - Redo the undone change
```
ℹ️ Use help within the CLI to view all available commands.

---

## 📁 Project Structure

```php
Mini-Version-Control-System/
├── src/                             # Source code folder
│   └── vcs/
│       ├── Main.java                # CLI entry point
│       ├── datastructures/          # Custom data structure implementations
│       │   ├── LinkedList.java          # Commit history
│       │   ├── BinarySearchTree.java   # File versioning
│       │   ├── HashTable.java          # File tracking
│       │   ├── Queue.java              # Commit processing
│       │   └── Stack.java              # Undo/Redo and traversal
│       ├── core/                    # Core functionality
│       │   ├── Repository.java         # Main repository logic
│       │   ├── File.java               # File metadata/tracking
│       │   ├── Commit.java             # Represents each commit
│       │   ├── CommitHistory.java      # Linked commit list
│       │   ├── FileVersion.java        # Stores file snapshots
│       │   └── DiffTool.java           # Computes file diffs
│       └── util/                    # Utility classes
│           ├── FileUtils.java         # File I/O helpers
│           ├── HashUtils.java         # Commit/file hashing
│           └── CommandParser.java     # CLI command parser
├── out/                             # Compiled class files
│   ├── vcs/
│   │   ├── Main.class
│   │   ├── datastructures/
│   │   │   ├── LinkedList.class
│   │   │   ├── BinarySearchTree.class
│   │   │   ├── HashTable.class
│   │   │   ├── Queue.class
│   │   │   └── Stack.class
│   │   ├── core/
│   │   │   ├── Repository.class
│   │   │   ├── File.class
│   │   │   ├── Commit.class
│   │   │   ├── CommitHistory.class
│   │   │   ├── FileVersion.class
│   │   │   └── DiffTool.class
│   │   └── util/
│   │       ├── FileUtils.class
│   │       ├── HashUtils.class
│   │       └── CommandParser.class
├── myrepo/                          # Initialized repository folder
│   └── .mini-vcs/                   # Internal Mini-VCS repository folder
├── run.bat                          # Batch script for testing
└── README.md                        # Project documentation

```
---
## 🤝 Contributing
Feel free to fork this project, submit issues, or open pull requests. Contributions are welcome!

---


