// src/components/CommitDetails.jsx

export default function CommitDetails({ commit }) {
  if (!commit) {
    return (
      <div className="bg-white rounded-3xl p-6 border border-gray-100 shadow-sm h-fit">
        <h2 className="text-xl font-semibold">
          Commit Details
        </h2>

        <p className="text-gray-500 mt-4">
          Select a commit
        </p>
      </div>
    );
  }

  return (
    <div className="bg-white rounded-3xl p-6 border border-gray-100 shadow-sm">
      <h2 className="text-xl font-semibold mb-6">
        Commit Details
      </h2>

      <div className="space-y-5">
        <div>
          <p className="text-sm text-gray-500">
            Commit ID
          </p>
          <p className="font-medium">
            {commit.id}
          </p>
        </div>

        <div>
          <p className="text-sm text-gray-500">
            Message
          </p>
          <p>{commit.message}</p>
        </div>

        <div>
          <p className="text-sm text-gray-500">
            Parent
          </p>
          <p>{commit.parent || "None"}</p>
        </div>

        <div>
          <p className="text-sm text-gray-500">
            Timestamp
          </p>
          <p>{commit.timestamp}</p>
        </div>

        <div>
          <p className="text-sm text-gray-500 mb-2">
            Files
          </p>

          {commit.files.map((file) => (
            <div
              key={file}
              className="
              bg-gray-50
              rounded-xl
              p-2
              mb-2
              "
            >
              {file}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}