import axios from "axios";
import { useState } from "react";

import "../profile/profile.css"

export function CreateUser() {

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "1234",
    role: "",
    courseName: "",
  });
  const { firstName, lastName, email, password, role, courseName } = formData;

  const OnChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

    const HandleSubmitCreateUser = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:9080/auth/register",
        formData,
        { withCredentials: true },
      );
      console.log(response)
      setFormData({email:"",password:"",courseName:"",firstName:"",lastName:"",role:""})
    } catch (error) {
      console.error(error.response.data.errorMessage || error.response.data || error.response);
    }
  };


  return (
    <div className="ProfileMainDiv">
      <p className="ProfileMainTitle">Create user</p>
      <form onSubmit={HandleSubmitCreateUser}>
        <div className="EachProfileInputDiv">
          <label htmlFor="FirstNameId">First name</label>
          <br />
          <input
            type="text"
            id="FirstNameId"
            name="firstName"
            value={firstName}
            onChange={OnChange}
          />
          <br />
        </div>
        <div className="EachProfileInputDiv">
          <label htmlFor="LastNameId">Last name</label>
          <br />
          <input
            type="text"
            id="LastNameId"
            name="lastName"
            value={lastName}
            onChange={OnChange}
          />
          <br />
        </div>
        <div className="EachProfileInputDiv">
          <label htmlFor="EmailId">Email</label>
          <br />
          <input
            type="email"
            id="EmailId"
            name="email"
            value={email}
            onChange={OnChange}
          />
          <br />
        </div>
        <div className="EachProfileInputDiv">
          <label htmlFor="RoleId">Role</label>
          <br />
          <input
            type="text"
            id="RoleId"
            name="role"
            value={role}
            onChange={OnChange}
          />
          <br />
        </div>
        <div className="EachProfileInputDiv">
          <label htmlFor="CourseId">Course</label>
          <br />
          <input
            type="text"
            id="CourseId"
            name="courseName"
            value={courseName}
            onChange={OnChange}
          />
          <br />
        </div>
        <div className="EachProfileInputDiv">
          <label htmlFor="passwordId">Password</label>
          <br />
          <input
            type="text"
            id="passwordId"
            name="password"
            value={password}
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
