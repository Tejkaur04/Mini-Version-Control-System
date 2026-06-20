import ReactFlow from "reactflow";

import "reactflow/dist/style.css";

import {
  nodes,
  edges,
} from "../data/graphData";

export default function CommitGraphPage() {
  return (
    <div
      className="
      h-[700px]
      bg-white
      rounded-3xl
      overflow-hidden
      "
    >
      <ReactFlow
        nodes={nodes}
        edges={edges}
        fitView
      />
    </div>
  );
}