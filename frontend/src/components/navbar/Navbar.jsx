import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios'

import './navbar.css'

export function Navbar() {

  const navigate=useNavigate()

  const HandleLogoutClick=async()=>{
    try {
      const response=await axios.post("http://localhost:9080/auth/logout",{},{withCredentials:true})
      console.log(response)
      navigate("/login")
    } catch (error) {
      console.error(error.response.data.errorMessage || error.response.data || error.response)
    }
  }

  return (
    <nav className='NavbarMainNav'>
        <Link to="/" className='NavbarMainLogo'>AssignmentHub</Link>
        <div>
          <Link to="/profile">Profile</Link>
          <button onClick={HandleLogoutClick}>Logout</button>
        </div>
    </nav>
  )
}
