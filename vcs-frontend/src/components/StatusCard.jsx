
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
        <p className="font-semibold text-green-700">
          ✓ Working Tree Clean
        </p>

        <p className="text-sm text-green-600 mt-1">
          No changes to commit
        </p>
      </div>

      <div className="space-y-4">
        <div className="flex justify-between">
          <span>Modified</span>
          <span className="font-semibold text-blue-500">
            1
          </span>
        </div>

        <div className="flex justify-between">
          <span>Deleted</span>
          <span className="font-semibold text-red-500">
            0
          </span>
        </div>

        <div className="flex justify-between">
          <span>Untracked</span>
          <span className="font-semibold text-amber-500">
            2
          </span>
        </div>
      </div>
    </div>
  );
}