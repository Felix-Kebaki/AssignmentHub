import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { LayoutWithNavbar } from "./components/Layouts/LayoutWithNavbar";
import { LayoutWithoutNavbar } from "./components/Layouts/LayoutWithoutNavbar";
import { LoginPage } from "./pages/LoginPage";
import { HomePage } from "./pages/HomePage";
import { ProtectedRoute } from "./components/ProtectedRoute";
import { ProfilePage } from "./pages/ProfilePage";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route element={<LayoutWithNavbar />}>
            <Route
              path="/"
              element={
                <ProtectedRoute>
                  <HomePage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/profile"
              element={
                <ProtectedRoute>
                  <ProfilePage />
                </ProtectedRoute>
              }
            />
          </Route>

          <Route element={<LayoutWithoutNavbar />}>
            <Route path="/login" element={<LoginPage />} />
          </Route>
        </Routes>
      </Router>
    </>
  );
}

export default App;
