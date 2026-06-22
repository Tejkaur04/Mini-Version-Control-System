import ReactFlow from "reactflow";
import "reactflow/dist/style.css";
import { useEffect, useState } from "react";

import GitCommitNode from "./GitCommitNode";
import vcsApi from "../services/vcsApi";



const nodeTypes = {
  gitCommit: GitCommitNode,
};

export default function CommitGraph() {
  const [nodes, setNodes] = useState([]);
  const [edges, setEdges] = useState([]);
  const nodeTypes = {
    gitCommit: GitCommitNode,
  };

  useEffect(() => {

    vcsApi
      .get("/commits")
      .then((response) => {

        console.log(response.data);

        generateGraph(response.data);

      })
      .catch((err) => {

        console.error(err);

      });

  }, []);

  const generateGraph = (commits) => {

    const graphNodes = [];
    const graphEdges = [];

    commits.forEach((commit, index) => {

      graphNodes.push({

        id: commit.id,

        type: "gitCommit",

        position: {
          x: 300,
          y: 80 + index * 160,
        },

        data: {
          message: commit.message,
          hash: commit.id.substring(0, 7),
          head: commit.head,
        },

      });

      if (commit.parent) {

        graphEdges.push({

          id: `${commit.parent}-${commit.id}`,

          source: commit.parent,

          target: commit.id,

          type: "step",

          animated: true,

          style: {
            stroke: "#f59e0b",
            strokeWidth: 3,
          },

        });
      }

    });

    setNodes(graphNodes);
    setEdges(graphEdges);

  };

  return (
    <div
      className="
      h-[600px]
      bg-white
      rounded-3xl
      border
      border-gray-100
      overflow-hidden
      "
    >
      <ReactFlow
        nodes={nodes}
        edges={edges}
        nodeTypes={nodeTypes}
        fitView

        nodesDraggable={false}
        nodesConnectable={false}
        elementsSelectable={false}
        zoomOnScroll={false}
      />
    </div>
  );
}