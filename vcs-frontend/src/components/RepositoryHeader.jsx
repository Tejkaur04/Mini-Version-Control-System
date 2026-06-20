export default function RepositoryHeader() {
  return (
    <div
      className="
      bg-white
      rounded-3xl
      p-6
      border
      border-gray-100
      shadow-sm
      mb-6
      "
    >
      <h2 className="text-2xl font-semibold">
        📁 myrepo
      </h2>

      <div className="flex gap-8 mt-4 text-gray-600">
        <p>
          Branch:
          <span className="font-medium ml-2">
            feature-auth
          </span>
        </p>

        <p>
          HEAD:
          <span className="font-medium ml-2">
            6550628
          </span>
        </p>

        <p>
          Last Commit:
          <span className="font-medium ml-2">
            branch commit
          </span>
        </p>
      </div>
    </div>
  );
}