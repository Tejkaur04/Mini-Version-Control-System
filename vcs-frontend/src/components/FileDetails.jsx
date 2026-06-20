// src/components/FileDetails.jsx

export default function FileDetails({ file }) {

  if (!file) {
    return (
      <div className="bg-white rounded-3xl p-6 shadow-sm border border-gray-100">
        Select a file
      </div>
    );
  }

  return (
    <div className="bg-white rounded-3xl p-6 shadow-sm border border-gray-100">

      <h2 className="text-2xl font-semibold mb-6">
        File Details
      </h2>

      <div className="space-y-5">

        <div>
          <p className="text-sm text-gray-500">
            File Name
          </p>

          <p className="font-medium">
            {file.name}
          </p>
        </div>

        <div>
          <p className="text-sm text-gray-500">
            Hash
          </p>

          <p className="font-mono">
            {file.hash}
          </p>
        </div>

        <div>
          <p className="text-sm text-gray-500">
            Size
          </p>

          <p>
            {file.size}
          </p>
        </div>

        <div>
          <p className="text-sm text-gray-500">
            Last Commit
          </p>

          <p>
            {file.lastCommit}
          </p>
        </div>

      </div>

    </div>
  );
}