import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { LayoutWithNavbar } from "./components/Layouts/LayoutWithNavbar";
import { LayoutWithoutNavbar } from "./components/Layouts/LayoutWithoutNavbar";
import { LoginPage } from "./pages/LoginPage";
import { RegisterPage } from "./pages/RegisterPage";
import { HomePage } from "./pages/HomePage";
import { ProtectedRoute } from "./components/ProtectedRoute";

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
              path="/registerUser"
              element={
                <ProtectedRoute>
                  <RegisterPage />
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
