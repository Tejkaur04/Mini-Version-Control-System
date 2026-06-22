import MainLayout from "../layout/MainLayout";

import FileStatusCard from "../components/FileStatusCard";

import { useEffect, useState } from "react";
import vcsApi from "../services/vcsApi";

export default function Status() {
  const [statusData, setStatusData] =
    useState({
      modified: [],
      deleted: [],
      untracked: []
    });
  useEffect(() => {

    vcsApi
      .get("/status")
      .then((response) => {

        setStatusData(response.data);

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
          Working Tree Status
        </h2>

        <p className="text-gray-500 mt-2">
          Monitor repository changes before committing
        </p>

        <div className="flex gap-3 mt-4 flex-wrap">

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
            {statusData.modified.length} Modified
          </span>

          <span
            className="
      bg-red-100
      text-red-700
      px-3
      py-1
      rounded-full
      text-sm
      font-medium
      "
          >
            {statusData.deleted.length} Deleted
          </span>

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
            {statusData.untracked.length} Untracked
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
              statusData.modified.length +
              statusData.deleted.length +
              statusData.untracked.length
            } Total Changes
          </span>

        </div>

      </div>

      <div className="grid grid-cols-3 gap-6 mb-6">

        <FileStatusCard
          title="Modified"
          files={statusData.modified}
          color="text-blue-500"
        />

        <FileStatusCard
          title="Untracked"
          files={statusData.untracked}
          color="text-amber-500"
        />

        <FileStatusCard
          title="Deleted"
          files={statusData.deleted}
          color="text-red-500"
        />



      </div>
      <div className="grid grid-cols-3 gap-6">

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
        <div className="bg-white rounded-2xl p-6 shadow-sm">
          <h2 className="font-semibold mb-4">
            Deleted Files
          </h2>

          {statusData.deleted.map((file) => (
            <div
              key={file}
              className="
      p-3
      rounded-xl
      bg-red-50
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