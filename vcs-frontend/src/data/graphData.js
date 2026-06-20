// data/graphData.js

export const nodes = [
  {
    id: "1",
    position: { x: 100, y: 50 },
    data: {
      label: "Initial Commit",
    },
  },

  {
    id: "2",
    position: { x: 100, y: 150 },
    data: {
      label: "Debug",
    },
  },

  {
    id: "3",
    position: { x: 100, y: 250 },
    data: {
      label: "Hello",
    },
  },

  {
    id: "4",
    position: { x: 300, y: 250 },
    data: {
      label: "feature-auth",
    },
  },
];

export const edges = [
  {
    id: "e1-2",
    source: "1",
    target: "2",
  },

  {
    id: "e2-3",
    source: "2",
    target: "3",
  },

  {
    id: "e3-4",
    source: "3",
    target: "4",
  },
];