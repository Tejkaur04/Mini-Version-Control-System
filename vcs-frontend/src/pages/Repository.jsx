import { useState } from "react";
import {
  FileCode2,
  FileText,
  FileJson,
  Search,
} from "lucide-react";

import MainLayout from "../layout/MainLayout";
import FileDetails from "../components/FileDetails";

import { files } from "../data/mockFiles";

export default function Repository() {

  const [selectedFile, setSelectedFile] =
    useState(files[0]);
  const [search, setSearch] = useState("");
  const filteredFiles = files.filter((file) =>
    file.name
      .toLowerCase()
      .includes(search.toLowerCase())
  );

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
          Repository Files
        </h2>

        <p className="text-gray-500 mt-2">
          Browse tracked files in your repository
        </p>

        <div className="flex gap-3 mt-4">

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
            {files.length} Files
          </span>

          <span
            className="
    px-3
    py-1
    bg-green-100
    text-green-700
    rounded-full
    text-sm
    "
          >
            Tracked
          </span>

          <span
            className="
    px-3
    py-1
    bg-amber-100
    text-amber-700
    rounded-full
    text-sm
    "
          >
            feature-auth
          </span>

        </div>

      </div>
      <div className="mb-6">



        <div className="mb-6 relative">

          <Search
            size={18}
            className="
    absolute
    left-4
    top-1/2
    -translate-y-1/2
    text-gray-400
    "
          />

          <input
            type="text"
            placeholder="Search files..."
            value={search}
            onChange={(e) =>
              setSearch(e.target.value)
            }
            className="
    w-full
    pl-12
    pr-4
    py-3
    rounded-xl
    border
    border-gray-200
    focus:outline-none
    focus:ring-2
    focus:ring-amber-200
    "
          />

        </div>
      </div>

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

            <div className="flex justify-between items-center mb-6">

              <h2 className="text-2xl font-semibold">
                Files
              </h2>

              <span
                className="
    bg-gray-100
    px-3
    py-1
    rounded-full
    text-sm
    "
              >
                {files.length}
              </span>

            </div>

            <div className="space-y-3">

              {filteredFiles.map((file) => (

                <div
                  key={file.name}
                  onClick={() => setSelectedFile(file)}
                  className={`
                    flex
                    items-center
                    justify-between
                    p-4
                    rounded-xl
                    cursor-pointer
                    transition-all

                    ${selectedFile.name === file.name
                      ? "bg-amber-50 border border-amber-300 shadow-sm"
                      : "hover:bg-gray-50 hover:translate-x-1 transition-all duration-200hover:shadow-md"
                    }
`}
                >


                  <div className="flex items-center gap-3 space-y-2">

                    <FileText className="text-amber-600" />

                    <span>
                      {file.name}
                    </span>

                  </div>

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
          <div className="bg-white rounded-3xl p-6 shadow-sm mt-6">

            <h2 className="text-2xl font-semibold mb-4">
              Recent Activity
            </h2>

            <div className="space-y-3">

              <div className="bg-gray-50 p-3 rounded-xl">
                Modified hello.txt
              </div>

              <div className="bg-gray-50 p-3 rounded-xl">
                Added branch.txt
              </div>

              <div className="bg-gray-50 p-3 rounded-xl">
                Created testFile.txt
              </div>

            </div>

          </div>

        </div>

      </div>

    </MainLayout>
  );
}