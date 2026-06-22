import {
  FileEdit,
  Trash2,
  FileQuestion,
  CheckCircle2,
} from "lucide-react";

export default function StatusCard() {
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
          className="
  bg-green-50
  border
  border-green-100
  rounded-2xl
  p-4
  mb-6
  "
        >

          <div className="flex items-center gap-2">

            <CheckCircle2
              size={18}
              className="text-green-600"
            />

            <span className="font-semibold text-green-700">
              Working Tree Clean
            </span>

          </div>

          <p
            className="
    text-sm
    text-green-600
    mt-2
    "
          >
            No pending changes detected
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
            1
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
            0
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
            2
          </span>

        </div>
      </div>
    </div>
  );
}