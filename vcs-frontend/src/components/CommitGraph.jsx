import ReactFlow from "reactflow";
import "reactflow/dist/style.css";

import GitCommitNode from "./GitCommitNode";

import {
  nodes,
  edges,
} from "../data/graphData";

const nodeTypes = {
  gitCommit: GitCommitNode,
};

export default function CommitGraph() {
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