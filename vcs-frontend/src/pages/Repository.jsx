import { useState } from "react";

import MainLayout from "../layout/MainLayout";
import FileDetails from "../components/FileDetails";

import { files } from "../data/mockFiles";

export default function Repository() {

  const [selectedFile, setSelectedFile] =
    useState(files[0]);

  return (
    <MainLayout>

      <h1 className="text-5xl font-bold mb-8">
        Repository Files
      </h1>

      <div className="grid grid-cols-12 gap-6">

        {/* File List */}

        <div className="col-span-8">

          <div
            className="
            bg-white
            rounded-3xl
            border
            border-gray-100
            shadow-sm
            p-6
            "
          >

            <h2 className="text-2xl font-semibold mb-6">
              Files
            </h2>

            <div className="space-y-3">

              {files.map((file) => (

                <div
                  key={file.name}
                  onClick={() =>
                    setSelectedFile(file)
                  }
                  className="
                  p-4
                  rounded-xl
                  hover:bg-gray-50
                  cursor-pointer
                  transition
                  "
                >
                  📄 {file.name}
                </div>

              ))}

            </div>

          </div>

        </div>

        {/* Details */}

        <div className="col-span-4">

          <FileDetails
            file={selectedFile}
          />

        </div>

      </div>

    </MainLayout>
  );
}