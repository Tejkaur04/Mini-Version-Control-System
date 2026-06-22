import { useEffect, useState } from "react";

import MainLayout from "../layout/MainLayout";

import CommitCard from "../components/CommitCard";
import CommitDetails from "../components/CommitDetails";

import vcsApi from "../services/vcsApi";

export default function Commits() {

    const [selectedCommit, setSelectedCommit] =
        useState(null);
    const [commits, setCommits] =
        useState([]);

    useEffect(() => {

        vcsApi
            .get("/commits")
            .then((response) => {

                setCommits(response.data);

                if (response.data.length > 0) {
                    setSelectedCommit(response.data[0]);
                }

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
                    Commits
                </h2>

                <p className="text-gray-500 mt-2">
                    Browse repository history
                </p>



            </div>

            <div className="grid grid-cols-12 gap-6">

                <div className="col-span-8">

                    {/* Stats Cards */}

                    <div className="grid grid-cols-3 gap-4 mb-6">

                        <div className="bg-white rounded-2xl p-4 shadow-sm">
                            <p className="text-gray-500 text-sm">
                                Total Commits
                            </p>

                            <h2 className="text-3xl font-bold">
                                {commits.length}
                            </h2>
                        </div>

                        <div className="bg-white rounded-2xl p-4 shadow-sm">
                            <p className="text-gray-500 text-sm">
                                Active Branches
                            </p>

                            <h2 className="text-3xl font-bold">
                                3
                            </h2>
                        </div>

                        <div className="bg-white rounded-2xl p-4 shadow-sm">
                            <p className="text-gray-500 text-sm">
                                HEAD
                            </p>

                            <h2 className="text-xl font-mono">
                                {commits.length > 0
                                    ? commits[0].id.substring(0, 7)
                                    : "--"}
                            </h2>
                        </div>

                    </div>

                    {/* Search Box */}

                    <input
                        type="text"
                        placeholder="Search commits..."
                        className="
            w-full
            mb-6
            px-4
            py-3
            rounded-xl
            border
            border-gray-200
            focus:outline-none
            focus:ring-2
            focus:ring-amber-200
        "
                    />

                    {/* Commit List */}

                    <div className="space-y-4">

                        {commits.map((commit) => (
                            <CommitCard
                                key={commit.id}
                                commit={commit}
                                selected={selectedCommit?.id === commit.id}
                                onSelect={setSelectedCommit}
                            />
                        ))}

                    </div>

                </div>

                <div className="col-span-4">

                    <CommitDetails
                        commit={selectedCommit}
                    />

                </div>

            </div>

        </MainLayout>
    );
}