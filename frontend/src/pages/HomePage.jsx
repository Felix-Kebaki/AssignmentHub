import axios from "axios";
import { useNavigate } from "react-router-dom";

export function HomePage() {

  const navigate=useNavigate()

  const HandleLogoutClick=async()=>{
    try{
      const response=await axios.post("http://localhost:9090/auth/logout",{},{withCredentials:true})
      console.log(response)
      navigate("/login")
    }catch(error){
      console.error(error.message || error)
    }
  }

  return (
    <section>
      <div>HomePage</div>
      <button onClick={HandleLogoutClick}>Logout</button>
    </section>
  );
}
