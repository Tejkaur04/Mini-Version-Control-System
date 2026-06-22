import { GitCommitHorizontal } from "lucide-react";

export default function RecentActivity({ commits }) {

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

      <h2 className="text-2xl font-semibold mb-6">
        Recent Activity
      </h2>

      <div className="space-y-3">

        {commits.length === 0 ? (

          <p className="text-gray-500">
            No recent activity
          </p>

        ) : (

          commits
            .slice()
            .reverse()
            .slice(0, 5)
            .map((commit) => (

              <div
                key={commit.id}
                className="
                bg-gray-50
                rounded-2xl
                p-4
                "
              >

                <div className="flex items-center gap-3">

                  <GitCommitHorizontal
                    size={16}
                    className="text-amber-600"
                  />

                  <span className="font-medium">
                    {commit.message}
                  </span>

                </div>

                <p
                  className="
                  text-xs
                  text-gray-500
                  mt-2
                  font-mono
                  "
                >
                  {commit.id.substring(0, 7)}
                </p>

                <p
                  className="
                  text-sm
                  text-gray-400
                  mt-1
                  "
                >
                  {commit.timestamp}
                </p>

              </div>

            ))
        )}

      </div>

    </div>
  );
}