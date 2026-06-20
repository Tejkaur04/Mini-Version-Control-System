import MainLayout from "../layout/MainLayout";

import FileStatusCard from "../components/FileStatusCard";

import { statusData }
from "../data/mockStatus";

export default function Status() {

  return (
    <MainLayout>

      <div className="mb-8">
        <h1 className="text-5xl font-bold">
          Repository Status
        </h1>

        <p className="text-gray-500 mt-2">
          Current working tree state
        </p>
      </div>

      <div className="grid grid-cols-3 gap-6 mb-6">

        <FileStatusCard
          title="Modified"
          files={statusData.modified}
          color="text-blue-500"
        />

        <FileStatusCard
          title="Deleted"
          files={statusData.deleted}
          color="text-red-500"
        />

        <FileStatusCard
          title="Untracked"
          files={statusData.untracked}
          color="text-amber-500"
        />

      </div>
      <div className="grid grid-cols-2 gap-6">

  <div className="bg-white rounded-2xl p-6 shadow-sm">

    <h2 className="font-semibold mb-4">
      Modified Files
    </h2>

    {statusData.modified.map((file) => (
      <div
        key={file}
        className="
          p-3
          rounded-xl
          bg-gray-50
          mb-2
        "
      >
        📄 {file}
      </div>
    ))}

  </div>

  <div className="bg-white rounded-2xl p-6 shadow-sm">

    <h2 className="font-semibold mb-4">
      Untracked Files
    </h2>

    {statusData.untracked.map((file) => (
      <div
        key={file}
        className="
          p-3
          rounded-xl
          bg-gray-50
          mb-2
        "
      >
        📄 {file}
      </div>
    ))}

  </div>

</div>

    </MainLayout>
  );
}