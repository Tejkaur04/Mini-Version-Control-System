import {
  Hash,
  HardDrive,
  GitCommitHorizontal,
  FileText,
} from "lucide-react";

export default function FileDetails({ file }) {

  if (!file) {
    return (
      <div className="bg-white rounded-3xl p-6 shadow-sm border border-gray-100 min-h-[550px]">
        Select a file
      </div>
    );
  }

  return (
    <div className="bg-white rounded-3xl p-6 shadow-sm border border-gray-100  ">

      <h2 className="text-2xl font-semibold mb-6 ">
        File Details
      </h2>

      <div className="space-y-5">


        <div className=" rounded-xl p-4 bg-amber-50
border
border-amber-300
shadow-sm">

          <p className="text-xs text-gray-500 mb-2">
            FILE NAME
          </p>

          <p className="font-medium">
            {file.name}
          </p>

        </div>

        <div className="bg-gray-50 rounded-xl p-4">
          <Hash
            size={16}
            className="text-gray-400"
          />

          <p className="text-xs text-gray-500 mb-2">
            HASH
          </p>

          <p className="font-medium">
            {file.hash?.substring(0, 12)}
          </p>

        </div>



        <div className="bg-gray-50 rounded-xl p-4">
          <HardDrive
            size={16}
            className="text-gray-400"
          />
          <p className="text-xs text-gray-500 mb-2">
            SIZE
          </p>

          <p className="font-medium">
            {file.size}
          </p>

        </div>

        <div className="bg-gray-50 rounded-xl p-4">
          <GitCommitHorizontal
            size={16}
            className="text-gray-400"
          />
          <p className="text-xs text-gray-500 mb-2">
            LAST COMMIT
          </p>

          <p className="font-medium">
            {file.lastCommit}
          </p>

        </div>



      </div>

    </div>
  );
}