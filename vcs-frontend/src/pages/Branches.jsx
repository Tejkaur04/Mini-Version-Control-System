import MainLayout from "../layout/MainLayout";

import BranchCard from "../components/BranchCard";

import { branches }
from "../data/mockBranches";

export default function Branches() {
  return (
    <MainLayout>

      <div className="mb-8">

        <h1 className="text-5xl font-bold">
          Branches
        </h1>

        <p className="text-gray-500 mt-2">
          Manage repository branches
        </p>

      </div>

      <div
        className="
        grid
        grid-cols-2
        gap-6
        "
      >
        {branches.map((branch) => (
          <BranchCard
            key={branch.name}
            branch={branch}
          />
        ))}
      </div>

    </MainLayout>
  );
}