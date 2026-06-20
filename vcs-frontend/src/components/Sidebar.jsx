import {
    Home,
    GitBranch,
    History,
    Folder,
    Activity,
} from "lucide-react";

export default function Sidebar() {
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

            <nav className="mt-10 space-y-4">
                <div>🏠 Dashboard</div>
                <div>📝 Commits</div>
                <div>🌿 Branches</div>
                <div>📁 Repository</div>
                <div>📊 Status</div>
            </nav>
        </aside>
    );
}