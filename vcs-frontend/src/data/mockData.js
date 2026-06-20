// src/data/mockData.js

export const repositoryData = {
  name: "myrepo",

  currentBranch: "feature-auth",

  head: "6550628",

  status: {
    modified: 1,
    deleted: 0,
    untracked: 2,
  },

  summary: {
    commits: 8,
    branches: 3,
    trackedFiles: 12,
    size: "1.2 MB",
  },

  branches: [
    {
      name: "master",
      head: "1ef06fc",
    },
    {
      name: "feature-auth",
      head: "6550628",
      current: true,
    },
    {
      name: "test",
      head: "2634911",
    },
  ],

  commits: [
    {
      id: "6550628",
      message: "branch commit",
      timestamp: "2 minutes ago",
    },
    {
      id: "1ef06fc",
      message: "hello",
      timestamp: "1 hour ago",
    },
    {
      id: "2634911",
      message: "debug",
      timestamp: "3 hours ago",
    },
  ],
};