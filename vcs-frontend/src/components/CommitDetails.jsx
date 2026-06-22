import {
  Hash,
  GitCommitHorizontal,
  Calendar,
  FolderTree,
  FileText,
} from "lucide-react";

export default function CommitDetails({ commit }) {

  if (!commit) return null;

  return (
    <div
      className="
      bg-white
      rounded-3xl
      p-6
      border
      border-gray-100
      shadow-sm
      "
    >

      <h2 className="text-2xl font-semibold mb-6">
        Commit Details
      </h2>

      <div className="space-y-4">

        {/* Commit ID */}

        <div className="bg-gray-50 rounded-2xl p-4">

          <div className="flex items-center gap-2 mb-2">

            <Hash
              size={16}
              className="text-gray-400"
            />

            <span className="text-xs text-gray-500 uppercase tracking-wide">
              Commit ID
            </span>

          </div>

          <p className="font-mono font-semibold">
            {commit.id}
          </p>

        </div>

        {/* Message */}

        <div className="bg-gray-50 rounded-2xl p-4">

          <div className="flex items-center gap-2 mb-2">

            <GitCommitHorizontal
              size={16}
              className="text-gray-400"
            />

            <span className="text-xs text-gray-500 uppercase tracking-wide">
              Message
            </span>

          </div>

          <p className="font-medium">
            {commit.message}
          </p>

        </div>

        {/* Parent */}

        <div className="bg-gray-50 rounded-2xl p-4">

          <div className="flex items-center gap-2 mb-2">

            <FolderTree
              size={16}
              className="text-gray-400"
            />

            <span className="text-xs text-gray-500 uppercase tracking-wide">
              Parent
            </span>

          </div>

          <p className="font-mono">
            {commit.parent}
          </p>

        </div>

        {/* Timestamp */}

        <div className="bg-gray-50 rounded-2xl p-4">

          <div className="flex items-center gap-2 mb-2">

            <Calendar
              size={16}
              className="text-gray-400"
            />

            <span className="text-xs text-gray-500 uppercase tracking-wide">
              Timestamp
            </span>

          </div>

          <p>
            {commit.timestamp}
          </p>

        </div>

        {/* Files Changed  */}
        <div className="mt-6">

          <h3
            className="
    text-sm
    font-semibold
    text-gray-500
    mb-3
    "
          >
            FILES CHANGED
          </h3>

          <div className="space-y-2">

            {commit.files?.map((file) => (

              <div
                key={file}
                className="
        flex
        items-center
        gap-3
        bg-gray-50
        rounded-xl
        p-3
        "
              >

                <FileText
                  size={16}
                  className="text-gray-400"
                />

                <span>{file}</span>

              </div>

            ))}

          </div>

        </div>

      </div>

    </div>
  );
}