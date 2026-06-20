// components/CommitCard.jsx

export default function CommitCard({
  commit,
  onSelect,
  selected,
}) {
  return (
    <div
      onClick={() => onSelect(commit)}
      className={`
  rounded-2xl
  p-5
  border
  shadow-sm
  cursor-pointer
  transition-all
  hover:shadow-md

  ${
    selected
      ? "bg-amber-50 border-amber-300"
      : "bg-white border-gray-100"
  }
`}
    >
      <div className="flex items-center gap-3">

        <h2 className="font-semibold text-lg">
          {commit.message}
        </h2>

        {commit.head && (
          <span
            className="
            bg-green-100
            text-green-700
            px-2
            py-1
            rounded-full
            text-xs
            "
          >
            HEAD
          </span>
        )}

      </div>

      <p
        className="
        font-mono
        text-gray-500
        text-sm
        mt-2
        "
      >
        {commit.id}
      </p>

      <p className="text-sm text-gray-400 mt-2">
        {commit.timestamp}
      </p>
    </div>
  );
}