// src/data/mockCommits.js

export const commits = [
  {
    id: "6550628",
    message: "branch commit",
    parent: "1ef06fc",
    timestamp: "20 Jun 2026",
    files: [
      "hello.txt",
      "branch.txt",
      "testFile.txt",
    ],
    head: true,
  },
  {
    id: "1ef06fc",
    message: "hello",
    parent: "2634911",
    timestamp: "20 Jun 2026",
    files: ["hello.txt"],
  },
  {
    id: "2634911",
    message: "debug",
    parent: null,
    timestamp: "20 Jun 2026",
    files: ["debug.txt"],
  },
];