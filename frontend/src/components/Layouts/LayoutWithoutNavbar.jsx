import { Outlet } from "react-router-dom"

export function LayoutWithoutNavbar() {
  return (
    <main>
      <Outlet /> 
    </main>
  )
}
