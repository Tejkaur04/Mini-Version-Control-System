# 🚀 Mini VCS

A Git-inspired Version Control System built from scratch using **Java, Spring Boot, React, and custom data structures**.

Mini VCS allows users to track files, create commits, manage branches, visualize repository history, and monitor repository status through an interactive web dashboard.

---

## 📸 Screenshots

> Add screenshots here after uploading them to GitHub.

---

## ✨ Features

### Repository Management

* Initialize repositories
* Track files and versions
* Monitor repository status
* Browse tracked files

### Commit Management

* Create commits with messages
* Maintain commit history
* Track parent-child relationships
* View commit metadata

### Branch Management

* Create branches
* Switch between branches
* Track branch heads
* Merge branch histories

### Interactive Dashboard

* Live repository statistics
* Dynamic commit graph
* Repository status monitoring
* Recent activity feed

### File Versioning

* SHA-1 based file tracking
* Version history management
* File metadata visualization

---

## 🧩 Custom Data Structures

To better understand the internals of version control systems, several data structures were implemented from scratch and integrated into the project:

* Linked List
* Stack
* Queue
* Hash Table
* Binary Search Tree

These structures are used throughout the repository, commit, and version management modules.

---

## 🔗 Full Stack Integration

The Version Control System was originally implemented as a Java application.

To make it interactive and easier to visualize, a Spring Boot API layer was built on top of the core VCS engine. The React frontend communicates with these APIs to display repository data in real time.

```text
React Frontend
       │
       ▼
Spring Boot REST API
       │
       ▼
Mini VCS Engine
       │
       ▼
Custom Data Structures
```

---

## 📊 Commit Graph Visualization

One of the core features of Mini VCS is the dynamic commit graph.

The graph is generated directly from repository commit history and visualized using React Flow.

Features include:

* Dynamic node generation
* Parent-child commit relationships
* HEAD commit highlighting
* Real-time updates from backend APIs
* Interactive repository history visualization

Example:

```text
debug
  │
  ▼
next
  │
  ▼
hello (HEAD)
```

Unlike static diagrams, the graph is generated entirely from actual commit data.

---

## 📈 Dashboard Features

The dashboard provides a real-time overview of repository activity.

### Repository Insights

* Total commits
* Active branches
* Tracked files
* Current HEAD commit

### Repository Status

* Modified files
* Deleted files
* Untracked files
* Working tree health

### Recent Activity

* Latest commits
* Commit timestamps
* Repository updates

---

## 🛠️ Tech Stack

### Frontend

* React.js
* Tailwind CSS
* React Flow
* Axios
* Lucide React

### Backend

* Java
* Spring Boot
* REST APIs

### Core VCS Engine

* Repository Management
* Commit Tracking
* Branch Management
* File Versioning

---

## 📂 Project Structure

```text
Mini-VCS
│
├── mini-vcs-ui
│   ├── components
│   ├── pages
│   ├── services
│   └── layout
│
├── mini-vcs-api
│   ├── controller
│   ├── service
│   ├── dto
│   └── vcs
│
└── core-engine
    ├── Repository
    ├── Commit
    ├── CommitHistory
    ├── File
    └── FileVersion
```

---

## 📡 REST API Endpoints

### Dashboard

```http
GET /api/dashboard
```

Returns repository statistics including commit count, branch count, tracked files, and current HEAD.

### Commits

```http
GET /api/commits
```

Returns commit history and metadata.

### Branches

```http
GET /api/branches
```

Returns branch information and current branch details.

### Repository Files

```http
GET /api/files
```

Returns tracked files and metadata.

### Repository Status

```http
GET /api/status
```

Returns modified, deleted, and untracked files.

---

## 💡 Challenges Solved

During development, several interesting problems were addressed:

* Designing commit-parent relationships
* Implementing branch tracking and checkout functionality
* Building file version management using SHA-1 hashes
* Exposing a Java-based VCS through REST APIs
* Synchronizing frontend visualizations with backend repository data
* Generating dynamic commit graphs from repository history
* Integrating custom data structures into a real-world application

---

## 🚀 Running the Project

### Backend

```bash
cd mini-vcs-api
./mvnw spring-boot:run
```

Runs on:

```text
http://localhost:8080
```

### Frontend

```bash
cd mini-vcs-ui
npm install
npm run dev
```

Runs on:

```text
http://localhost:5173
```

---

## 🔮 Future Improvements

* Branch checkout from UI
* Branch creation and deletion
* Merge conflict visualization
* Commit search and filtering
* File diff viewer
* Multi-repository support
* Authentication and user profiles
* Dark mode
* GitHub integration

---

## 👩‍💻 Author

**Tejinder Kaur**
Passionate about Software Engineering, Backend Development, Open Source, and Building Systems from Scratch.
