export const nodes = [
  {
    id: "1",

    type: "gitCommit",

    position: {
      x: 100,
      y: 50,
    },

    data: {
      message: "Initial Commit",
      hash: "000001",
    },
  },

  {
    id: "2",

    type: "gitCommit",

    position: {
      x: 100,
      y: 150,
    },

    data: {
      message: "Debug",
      hash: "2634911",
    },
  },

  {
    id: "3",

    type: "gitCommit",

    position: {
      x: 100,
      y: 250,
    },

    data: {
      message: "Hello",
      hash: "1ef06fc",
    },
  },

  {
    id: "4",

    type: "gitCommit",

    position: {
      x: 300,
      y: 350,
    },

    data: {
      message: "branch commit",
      hash: "6550628",
      head: true,
      branch: "feature-auth"
    },
  },
];

export const edges = [
  {
    id: "e1",
    source: "1",
    target: "2",

    type: "smoothstep",

    animated: true,

    style: {
      stroke: "#f59e0b",
      strokeWidth: 3,
    },
  },

  {
    id: "e2",
    source: "2",
    target: "3",

    type: "smoothstep",

    animated: true,

    style: {
      stroke: "#f59e0b",
      strokeWidth: 3,
    },
  },

  {
    id: "e3",
    source: "3",
    target: "4",

    type: "smoothstep",

    animated: true,

    style: {
      stroke: "#f59e0b",
      strokeWidth: 3,
    },
  },
];