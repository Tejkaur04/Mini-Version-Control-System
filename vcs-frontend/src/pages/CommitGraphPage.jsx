import ReactFlow from "reactflow";

import "reactflow/dist/style.css";


import { CommitGraph } from "CommitGraph.jsx";

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
        nodeTypes={nodeTypes}
        fitView
      />
    </div>
  );
}