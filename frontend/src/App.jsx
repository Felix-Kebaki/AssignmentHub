import { BrowserRouter as Router,Route,Routes } from "react-router-dom"
import { LayoutWithNavbar } from "./components/Layouts/LayoutWithNavbar"
import { LayoutWithoutNavbar } from "./components/Layouts/LayoutWithoutNavbar"
import { LoginPage } from "./pages/LoginPage"
import { RegisterPage } from "./pages/RegisterPage"
import { HomePage } from "./pages/HomePage"

function App() {

  return (
    <>
      <Router>
        <Routes>

          <Route element={<LayoutWithNavbar/>}>
              <Route path="/" element={<HomePage/>}/>
          </Route>

          <Route element={<LayoutWithoutNavbar/>}>
              <Route path="/login" element={<LoginPage/>}/>
              <Route path="/register" element={<RegisterPage/>}/>
          </Route>

        </Routes>
      </Router>
    </>
  )
}

export default App
