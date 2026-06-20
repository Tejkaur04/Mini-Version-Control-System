// src/components/BranchCard.jsx

export default function BranchCard({
    branch,
}) {
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
            <div className="flex justify-between items-center">

                <h2 className="text-xl font-semibold">
                    {branch.name}
                </h2>

                {branch.current && (
                    <span
                        className="
            bg-green-100
            text-green-700
            px-3
            py-1
            rounded-full
            text-xs
            "
                    >
                        CURRENT
                    </span>
                )}

            </div>

            <div className="mt-5 space-y-2">

                <p className="text-sm text-gray-500">
                    HEAD
                </p>

                <p className="font-mono">
                    {branch.head}
                </p>

                <p className="text-sm text-gray-500 mt-4">
                    Commits
                </p>

                <p>
                    {branch.commits}
                </p>

            </div>
            <div className="mt-6 flex justify-between items-center">
            <button
                className="
    flex-1
    m-2            
    px-4
    py-3
    rounded-xl
    bg-amber-100
    text-amber-700
    font-medium
    text-sm
    hover:bg-amber-200
    transition-all
    duration-200
  "
            >
                Checkout
            </button>
            
            <button
                className="
    flex-1
    px-4
    py-3
    rounded-xl
    bg-red-50
    text-red-600
    font-medium
    text-sm
    hover:bg-red-100
    transition-all
    duration-200
  "
            >
                Delete
            </button>
            </div>

        </div>
    );
}