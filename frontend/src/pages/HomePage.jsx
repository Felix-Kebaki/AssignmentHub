import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export function HomePage() {

  const navigate=useNavigate()

  const HandleLogoutClick=async()=>{
    try{
      const response=await axios.post("http://localhost:9080/auth/logout",{withCredentials:true})
      console.log(response)
      navigate("/login")
    }catch(error){
      console.error(error.message || error)
    }
  }

  const fetchProfile=async()=>{
    try {
      const response=await axios.get(`http://localhost:9080/auth/getProfile/${localStorage.getItem("userId")}`,{withCredentials:true})
      console.log(response)
    } catch (error) {
      console.error(error.message || error)
    }
  }

  useEffect(()=>{
    fetchProfile();
  },[])

  return (
    <section>
      <div>HomePage</div>
      <button onClick={HandleLogoutClick}>Logout</button>
    </section>
  );
}
