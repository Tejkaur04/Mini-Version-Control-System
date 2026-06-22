import {
  FileEdit,
  Trash2,
  FileQuestion,
  CheckCircle2,
} from "lucide-react";

export default function StatusCard({ status }) {
  const isClean =
    (status?.modified?.length || 0) === 0 &&
    (status?.deleted?.length || 0) === 0 &&
    (status?.untracked?.length || 0) === 0;

  return (
    <div
      className="
      bg-white
      rounded-3xl
      p-6
      border
      border-gray-100
      shadow-sm
       h-auto
    "
    >
      <h3 className="text-xl font-semibold mb-6">
        Repository Status
      </h3>


      <div
        className={`
    rounded-2xl
    p-4
    mb-6
    border
    ${isClean
            ? "bg-green-50 border-green-100"
            : "bg-amber-50 border-amber-100"
          }
  `}
      >



        <span
          className={`font-semibold ${isClean
            ? "text-green-700"
            : "text-amber-700"
            }`}
        >
          {isClean
            ? "Working Tree Clean"
            : "Changes Detected"}
        </span>

        <p
          className={`text-sm mt-2 ${isClean
              ? "text-green-600"
              : "text-amber-600"
            }`}
        >
          {isClean
            ? "No pending changes detected"
            : "Repository contains uncommitted changes"}
        </p>

      </div>



      <div className="space-y-4">
        <div
          className="
  bg-blue-50
  rounded-xl
  p-3
  flex
  justify-between
  items-center
  "
        >

          <div className="flex items-center gap-2 hover:scale-[1.02] transition-all duration-200">

            <FileEdit
              size={16}
              className="text-blue-500"
            />

            <span>Modified</span>

          </div>

          <span className="font-semibold">
            {status?.modified?.length || 0}
          </span>

        </div>

        <div
          className="
  bg-red-50
  rounded-xl
  p-3
  flex
  justify-between
  items-center
  "
        >

          <div className="flex items-center gap-2 hover:scale-[1.02] transition-all duration-200">

            <Trash2
              size={16}
              className="text-red-500"
            />

            <span>Deleted</span>

          </div>

          <span className="font-semibold">
            {status?.deleted?.length || 0}
          </span>

        </div>

        <div
          className="
  bg-amber-50
  rounded-xl
  p-3
  flex
  justify-between
  items-center
  "
        >

          <div className="flex items-center gap-2 hover:scale-[1.02] transition-all duration-200">

            <FileQuestion
              size={16}
              className="text-amber-500"
            />

            <span>Untracked</span>

          </div>

          <span className="font-semibold">
            {status?.untracked?.length || 0}
          </span>

        </div>
      </div>
    </div>
  );
}