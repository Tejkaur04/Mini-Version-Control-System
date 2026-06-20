# 🚀 Mini Version Control System (Mini VCS)

A Git-inspired Version Control System built entirely from scratch in Java.

Mini VCS recreates the fundamental concepts behind modern version control systems such as Git, including commit snapshots, content-addressable storage, branching, checkout operations, repository persistence, and file state tracking.

The goal of this project was to understand how version control systems work internally rather than relying on existing tools.

---

## ✨ Highlights

✅ Repository initialization

✅ Snapshot-based commits

✅ Persistent object storage

✅ SHA-1 content addressing

✅ Commit history tracking

✅ Branch creation and switching

✅ Checkout using commit IDs and branch names

✅ Modified, Deleted, and Untracked file detection

✅ Custom-built data structures

✅ Git-inspired command-line interface

---

## 🎯 Why This Project?

Version control systems are one of the most important tools in software engineering, yet many developers use them without understanding how they work under the hood.

Mini VCS was built to explore:

* How commits are stored and reconstructed
* How snapshots represent repository state
* How branching works internally
* How file tracking and checkout operations function
* How repository metadata can be persisted across sessions

Instead of using Git libraries or existing implementations, all core functionality was developed from scratch in Java.

---

## 🏗️ System Architecture

```text
Working Directory
        │
        ▼
    add(file)
        │
        ▼
   Staging Area
      (Index)
        │
        ▼
 commit(message)
        │
        ▼
   Commit Object
        │
        ▼
 Object Storage
 (.mini-vcs/objects)
        │
        ▼
 Commit History
```

---

## 📂 Repository Structure

```text
myrepo/
│
├── .mini-vcs/
│   ├── objects/
│   │   ├── <file-hash>
│   │   └── <commit-hash>
│   │
│   ├── refs/
│   │   ├── master
│   │   ├── feature-auth
│   │   └── test
│   │
│   ├── HEAD
│   ├── CURRENT_BRANCH
│   └── index
│
└── project files...
```

### Objects

Stores:

* Commit objects
* File snapshots

Each object is identified using a SHA-1 hash.

### Refs

Stores branch references and their latest commit hashes.

### HEAD

Stores the currently checked-out commit.

### CURRENT_BRANCH

Stores the active branch name.

---

# 🛠️ Technologies Used

* Java
* File I/O (NIO)
* SHA-1 Hashing
* Custom Data Structures
* Command Line Interface (CLI)

---

# 📚 Custom Data Structures

The project implements several data structures from scratch.

| Data Structure   | Purpose                                          |
| ---------------- | ------------------------------------------------ |
| HashTable        | Fast lookup of tracked files and commit metadata |
| LinkedList       | Commit history traversal                         |
| BinarySearchTree | Version indexing and retrieval                   |
| Stack            | State management and future traversal operations |
| Queue            | Internal processing workflows                    |

---

# ⚙️ Prerequisites

* Java JDK 17 or higher
* PowerShell / Command Prompt / Terminal

Verify installation:

```bash
java --version
```

---

# 🚀 Getting Started

## Clone the Repository

```bash
git clone <repository-url>
cd Mini-Version-Control-System
```

---

## Compile the Project

### Windows

```bash
run.bat
```

### Manual Compilation

```bash
javac -d out src/vcs/Main.java src/vcs/core/*.java src/vcs/datastructures/*.java src/vcs/util/*.java
```

---

## Run the Application

```bash
java -cp out vcs.Main
```

---

# 📖 Quick Demo

## Initialize Repository

```bash
java -cp out vcs.Main init myrepo
```

Output:

```text
Initialized empty Mini VCS repository
```

Move into repository:

```bash
cd myrepo
```

---

## Create a File

```bash
echo Hello World > hello.txt
```

---

## Add File

```bash
java -cp ../out vcs.Main add hello.txt
```

Output:

```text
Added file: hello.txt
```

---

## Commit Changes

```bash
java -cp ../out vcs.Main commit "Initial commit"
```

Output:

```text
Created commit: 1ef06fcba2557d58bfe21f21fd1406a28c4cbf6b
```

---

## View Commit History

```bash
java -cp ../out vcs.Main log
```

Example:

```text
1ef06fc - Initial commit
6550628 - Added feature
```

---

# 🌿 Branching

## Create a Branch

```bash
java -cp ../out vcs.Main branch feature-auth
```

Output:

```text
Created branch: feature-auth
```

---

## View Branches

```bash
java -cp ../out vcs.Main branch
```

Example:

```text
* master -> 6550628...
  feature-auth -> 1ef06fc...
```

---

## Switch Branch

```bash
java -cp ../out vcs.Main checkout feature-auth
```

---

# 🔄 Checkout

## Checkout a Commit

Using full commit ID:

```bash
java -cp ../out vcs.Main checkout 6550628a0cd55bd1ca9dab0e26109ce20386b21d
```

Using shortened commit ID:

```bash
java -cp ../out vcs.Main checkout 6550628
```

---

## Checkout a Branch

```bash
java -cp ../out vcs.Main checkout master
```

---

# 📊 Status Tracking

Check repository status:

```bash
java -cp ../out vcs.Main status
```

Example output:

```text
Modified: hello.txt
Deleted: notes.txt
Untracked: temp.txt
```

Mini VCS currently detects:

* Modified files
* Deleted files
* Untracked files

---

# 📜 Command Reference

| Command               | Description            |
| --------------------- | ---------------------- |
| `init <directory>`    | Initialize repository  |
| `add <file>`          | Stage a file           |
| `commit <message>`    | Create commit snapshot |
| `status`              | Show repository status |
| `log`                 | Show commit history    |
| `branch`              | List all branches      |
| `branch <name>`       | Create a new branch    |
| `checkout <branch>`   | Switch branch          |
| `checkout <commitId>` | Checkout a commit      |
| `help`                | Display help menu      |

---

# 🔮 Future Enhancements

* Merge support
* Diff visualization
* Interactive commit graph
* React-based frontend dashboard
* Remote repositories
* Push/Pull support

---

# 🤝 Contributing

Contributions, suggestions, and improvements are welcome.

Feel free to fork the repository and submit pull requests.

---

# 📄 License

This project was built for educational and learning purposes.
