import MainLayout from "../layout/MainLayout";
import SummaryCard from "../components/SummaryCard";
import StatusCard from "../components/StatusCard";
import CommitGraph from "../components/CommitGraph";
import CommitDetails from "../components/CommitDetails";
import { useEffect, useState } from "react";
import vcsApi from "../services/vcsApi";
import RepositoryHeader from "../components/RepositoryHeader";
import RecentActivity from "../components/RecentActivity";

export default function Dashboard() {
    const [selectedCommit, setSelectedCommit] =
        useState(null);

    const [dashboard, setDashboard] =
        useState(null);

    const [commits, setCommits] =
        useState([]);

    const [status, setStatus] = useState(null);

    useEffect(() => {

        vcsApi
            .get("/dashboard")
            .then((response) => {

                setDashboard(response.data);

            });

        vcsApi
            .get("/commits")
            .then((response) => {

                setCommits(response.data);

                if (response.data.length > 0) {

                    setSelectedCommit(
                        response.data[
                        response.data.length - 1
                        ]
                    );
                }
            });

        vcsApi
            .get("/status")
            .then((response) => {
                setStatus(response.data);
            });

    }, []);

    return (

        <MainLayout>


            <div className="grid grid-cols-12 gap-6">

                {/* LEFT */}
                <div className="col-span-8 space-y-6">
                    <RepositoryHeader></RepositoryHeader>
                    <CommitGraph />

                    <div className="grid grid-cols-4 gap-4">
                        <SummaryCard
                            title="Commits"
                            value={dashboard?.commits || 0}
                        />

                        <SummaryCard
                            title="Branches"
                            value={dashboard?.branches || 0}
                        />

                        <SummaryCard
                            title="Files"
                            value={dashboard?.files || 0}
                        />

                        <SummaryCard
                            title="HEAD"
                            value={dashboard?.head || "--"}
                        />
                    </div>

                </div>

                {/* RIGHT */}
                <div className="col-span-4 space-y-6">

                    <StatusCard status={status} />

                    <RecentActivity
                        commits={commits}
                    />

                </div>

            </div>





        </MainLayout>
    );
}