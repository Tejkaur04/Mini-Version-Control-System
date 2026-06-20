import { BrowserRouter, Routes, Route } from "react-router-dom";

import Dashboard from "./pages/Dashboard";
import Commits from "./pages/Commits";
import Branches from "./pages/Branches";
import Repository from "./pages/Repository";
import Status from "./pages/Status";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        <Route
          path="/"
          element={<Dashboard />}
        />

        <Route
          path="/commits"
          element={<Commits />}
        />

        <Route
          path="/branches"
          element={<Branches />}
        />

        <Route
          path="/repository"
          element={<Repository />}
        />

        <Route
          path="/status"
          element={<Status />}
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;