import { useState } from "react";
import MainLayout from "../layout/MainLayout";
import SummaryCard from "../components/SummaryCard";
import StatusCard from "../components/StatusCard";
import CommitGraph from "../components/CommitGraph";
import CommitDetails from "../components/CommitDetails";
import { commits } from "../data/mockCommits";
import RepositoryHeader from "../components/RepositoryHeader";

export default function Dashboard() {
    const [selectedCommit, setSelectedCommit] =
        useState(commits[0]);

    return (

        <MainLayout>
            <h1 className="text-5xl font-bold">
                myrepo
            </h1>

            <p className="text-gray-500 mt-2">
                Current Branch • feature-auth
            </p>

            <div className="grid grid-cols-12 gap-6">

                {/* LEFT */}
                <div className="col-span-8 space-y-6">
                    <RepositoryHeader></RepositoryHeader>
                    <CommitGraph
                        commits={commits}
                        onSelect={setSelectedCommit}
                    />

                    <div className="grid grid-cols-4 gap-4">
                        <SummaryCard title="Commits" value="8" />
                        <SummaryCard title="Branches" value="3" />
                        <SummaryCard title="Files" value="12" />
                        <SummaryCard title="Size" value="1.2 MB" />
                    </div>

                </div>

                {/* RIGHT */}
                <div className="col-span-4 space-y-6">

                    <StatusCard />

                    <CommitDetails
                        commit={selectedCommit}
                    />

                </div>

            </div>





        </MainLayout>
    );
}