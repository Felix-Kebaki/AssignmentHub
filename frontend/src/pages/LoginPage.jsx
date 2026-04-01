import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export function LoginPage() {
    const [formData,setFormData]=useState({
        email:"",
        password:""
    })
    const {email,password}=formData
    const navigate=useNavigate()

    const OnChange=(e)=>{
        setFormData((prev)=>({
            ...prev,[e.target.name]:e.target.value
        }))
    }

    const HandleSubmitForm= async (e)=>{
        e.preventDefault()
        try {
            const response=await axios.post("http://localhost:9090/auth/login",formData,{withCredentials:true})
            localStorage.setItem("userId",response.data.id);
            navigate("/")
        } catch (error) {
            console.error(error.message || error)
        }
    }

  return (
    <section>
      <div>
        <form onSubmit={HandleSubmitForm}>
          <div>
            <label htmlFor="emailId">Email</label>
            <br />
            <input
              type="email"
              name="email"
              value={email}
              onChange={OnChange}
              id="emailId"
            />
            <br />
          </div>
          <div>
            <label htmlFor="PasswordId">password</label>
            <br />
            <input
              type="password"
              name="password"
              value={password}
              onChange={OnChange}
              id="PasswordId"
            />
            <br />
          </div>
          <div>
            <button type="submit">Login</button>
          </div>
        </form>
      </div>
    </section>
  );
}
