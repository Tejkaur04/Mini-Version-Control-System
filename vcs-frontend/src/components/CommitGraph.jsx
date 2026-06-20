export default function CommitGraph({
  commits,
  onSelect,
}) {
  return (
    <div
      className="
      bg-white
      rounded-3xl
      border
      border-gray-100
      shadow-sm
      p-8
    //   min-h-[300px]
      "
    >
      <div className="flex justify-between items-center mb-10">
        <h2 className="text-2xl font-semibold">
          Repository Timeline
        </h2>

        <span
          className="
          bg-amber-100
          text-amber-700
          px-4
          py-2
          rounded-full
          text-sm
          "
        >
          feature-auth
        </span>
      </div>

      <div className="relative">
        <div
          className="
          absolute
          left-[7px]
          top-0
          w-[2px]
          h-full
          bg-gray-200
          "
        />

        <div className="space-y-10">
          {commits.map((commit) => (
            <div
              key={commit.id}
              className="
                flex
                gap-6
                relative
                p-4
                rounded-2xl
                hover:bg-amber-50
                cursor-pointer
                transition-all
                duration-200
            "
            >
              <div
                className="
                w-4
                h-4
                rounded-full
                bg-amber-500
                z-10
                "
              />

              <div>
                <div className="flex items-center gap-3">
                  <h3 className="font-semibold text-lg">
                    {commit.message}
                  </h3>

                  {commit.head && (
                    <span
                    className="
                     bg-green-100
                     text-green-700
                        px-3
                        py-1
                        rounded-full
                        text-xs
                        font-medium
                            "
                        >
                         HEAD
                    </span>
                  )}
                </div>

                <p
                className="
                text-xs
                font-mono
              text-gray-400
                mt-1
                 "
                >
                 {commit.id}
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}


