import { Handle, Position } from "reactflow";


export default function GitCommitNode({ data }) {
  return (
    <>
      <Handle
        type="target"
        position={Position.Top}
      />

      <div className="flex items-center gap-3">

        <div
          className="
          w-4
          h-4
          rounded-full
          bg-amber-500
          "
        />

        <div>

          <div className="flex items-center gap-2">

            <p className="font-medium">
              {data.message}
            </p>

            {data.head && (
              <span
                className="
                px-2
                py-1
                text-xs
                bg-green-100
                text-green-700
                rounded-full
                "
              >
                HEAD
              </span>
            )}

            {data.branch && (
              <span
                className="
                px-2
                py-1
                text-xs
                bg-amber-100
                text-amber-700
                rounded-full
                "
              >
                {data.branch}
              </span>
            )}

          </div>

          <p
            className="
            text-xs
            text-gray-500
            font-mono
            "
          >
            {data.hash}
          </p>

        </div>

      </div>

      <Handle
        type="source"
        position={Position.Bottom}
      />
    </>
  );
}