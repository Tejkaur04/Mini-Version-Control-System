import MainLayout from "../layout/MainLayout";

import BranchCard from "../components/BranchCard";

import { useEffect, useState } from "react";
import vcsApi from "../services/vcsApi";

export default function Branches() {
  const [branches, setBranches] =
    useState([]);

  useEffect(() => {

    vcsApi
      .get("/branches")
      .then((response) => {

        setBranches(response.data);

      });

  }, []);
  return (
    <MainLayout>

      <div
        className="
  bg-white
  rounded-3xl
  border
  border-gray-100
  shadow-sm
  p-6
  mb-6
  "
      >

        <h2 className="text-2xl font-bold">
          Branch Overview
        </h2>

        <p className="text-gray-500 mt-2">
          Manage and navigate repository branches
        </p>

        <div className="flex gap-3 mt-4 flex-wrap">

          <span
            className="
      bg-amber-100
      text-amber-700
      px-3
      py-1
      rounded-full
      text-sm
      font-medium
      "
          >
            {branches.length} Branches
          </span>

          <span
            className="
      bg-green-100
      text-green-700
      px-3
      py-1
      rounded-full
      text-sm
      font-medium
      "
          >
            {
              branches.find(
                (branch) => branch.current
              )?.name || "master"
            }
          </span>

          <span
            className="
      bg-blue-100
      text-blue-700
      px-3
      py-1
      rounded-full
      text-sm
      font-medium
      "
          >
            Active Branch
          </span>

        </div>

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