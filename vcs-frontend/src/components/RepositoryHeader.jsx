import { FolderGit2 } from "lucide-react";
export default function RepositoryHeader() {
  return (
    <div
      className="
  bg-white
  rounded-3xl
  border
  border-gray-100
  shadow-sm
  p-6
  "
    >

      {/* Repository Name */}

      <div className="flex items-center gap-3 mb-6">

        <FolderGit2
          size={30}
          className="text-amber-500"
        />

        <div>
          <h2 className="text-3xl font-bold">
            myrepo
          </h2>

          <p className="text-gray-500 text-sm">
            Local Repository
          </p>
        </div>

      </div>

      {/* Repository Info */}

      <div className="grid grid-cols-3 gap-4">

        <div
          className="
      bg-gray-50
      rounded-2xl
      p-4
      "
        >
          <p className="text-xs text-gray-500 mb-2">
            Current Branch
          </p>

          <p className="font-semibold">
            feature-auth
          </p>
        </div>

        <div
          className="
      bg-gray-50
      rounded-2xl
      p-4
      "
        >
          <p className="text-xs text-gray-500 mb-2">
            HEAD
          </p>

          <p className="font-mono font-semibold">
            6550628
          </p>
        </div>

        <div
          className="
      bg-gray-50
      rounded-2xl
      p-4
      "
        >
          <p className="text-xs text-gray-500 mb-2">
            Last Commit
          </p>

          <p className="font-semibold truncate">
            branch commit
          </p>
        </div>

      </div>

    </div>
  );
}