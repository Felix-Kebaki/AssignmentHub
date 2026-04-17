import { Outlet } from "react-router-dom";
import { Navbar } from "../navbar/Navbar";

export function LayoutWithNavbar() {
  return (
    <>
      <Navbar />
      <main>
        <Outlet /> 
      </main>
    </>
  );
}
