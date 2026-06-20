import Sidebar from "../components/Sidebar";

export default function MainLayout({ children }) {
  return (
    <div className="min-h-screen bg-[#F8F7F4] flex">
      <Sidebar />

      <main className="flex-1 p-8 overflow-y-auto">
        {children}
      </main>
    </div>
  );
}