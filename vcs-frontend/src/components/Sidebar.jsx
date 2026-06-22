import {
    Home,
    GitBranch,
    History,
    Folder,
    Activity,
    HomeIcon,
    GitBranchIcon,
    HistoryIcon,
    FolderIcon,
    ActivityIcon,
} from "lucide-react";
import { NavLink } from "react-router-dom";

export default function Sidebar() {
    const navClass = ({ isActive }) =>
        `flex items-center gap-3 px-4 py-3 rounded-xl transition
   ${isActive
            ? "bg-amber-100 text-amber-700"
            : "hover:bg-gray-100"
        }`;
    return (
        <aside className="w-64 bg-white border-r border-gray-200 p-6">
            <div className="mb-12">
                <h1 className="text-3xl font-bold">
                    Mini VCS
                </h1>

                <p className="text-sm text-gray-500">
                    Version Control
                </p>
            </div>

            <nav className="space-y-2">

                <NavLink
                    to="/"
                    className={navClass}
                >
                <HomeIcon></HomeIcon>
                     Dashboard
                </NavLink>

                <NavLink
                    to="/commits"
                    className={navClass}
                >
                    <GitBranchIcon></GitBranchIcon>
                     Commits
                </NavLink>

                <NavLink
                    to="/branches"
                    className={navClass}
                >
                    <HistoryIcon></HistoryIcon>
                     Branches
                </NavLink>

                <NavLink
                    to="/repository"
                    className={navClass}
                >
                    <FolderIcon></FolderIcon>
                     Repository
                </NavLink>

                <NavLink
                    to="/status"
                    className={navClass}
                >
                    <ActivityIcon></ActivityIcon>
                     Status
                </NavLink>

            </nav>
        </aside>
    );
}