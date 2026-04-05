import axios from "axios";
import React, { useState } from "react";

import "../profile/profile.css"

export function CreateCourse() {
  const [formData, setFormData] = useState({
    courseName:""
  });
  const {courseName}=formData

  const OnChange=(e)=>{
    setFormData((prev)=>({
        ...prev,[e.target.name]:e.target.value
    }))
  }

  const HandleCreateCourse=async(e)=>{
    e.preventDefault()
    try{
        const res=await axios.post("http://localhost:9080/course/create",formData,{withCredentials:true})
        console.log(res)
        setFormData({courseName:""})
    }catch(error){
        console.error(error.response.data.errorMessage || error.response.data || error.response)
    }
  }

  return (
    <div className="ProfileMainDiv">
      <p className="ProfileMainTitle">Create course</p>
      <form onSubmit={HandleCreateCourse}>
        <div className="EachProfileInputDiv">
          <label htmlFor="courseNameId">Course name</label>
          <br />
          <input
            type="text"
            id="courseNameId"
            name="courseName"
            value={courseName}
            onChange={OnChange}
          />
          <br />
        </div>
        <div className="SubmitProfileBtnDiv">
          <button type="submit">Create</button>
        </div>
      </form>
    </div>
  );
}
