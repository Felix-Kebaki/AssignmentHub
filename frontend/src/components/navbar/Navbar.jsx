import { Link, useNavigate } from 'react-router-dom'
import axios from 'axios'

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
    <nav>
        <Link>AssignmentHub</Link>
        <div>
          <button onClick={HandleLogoutClick}>Logout</button>
        </div>
    </nav>
  )
}
