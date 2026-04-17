import axios from "axios";
import { useEffect, useState } from "react";

import "./profile.css";

export function Profile() {
  const [data, setData] = useState(null);
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    courseName: "",
    password: "",
    role: "",
  });
  const { firstName, lastName, email, password, courseName, role } = formData;

  const OnChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const getProfile = async () => {
    try {
      const res = await axios.get("http://localhost:9080/auth/profile", {
        withCredentials: true,
      });
      setData(res.data);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  useEffect(() => {
    getProfile();
  }, []);

  useEffect(() => {
    if (data !== null) {
      setFormData({
        firstName: data.firstName || "",
        lastName: data.lastName || "",
        email: data.email || "",
        courseName: data.courseName || "",
        password: "",
        role: data.role || "",
      });
    }
  }, [data]);

  return (
    <div className="ProfileMainDiv">
      <p className="ProfileMainTitle">Your Profile</p>
      {data !== null ? (
        <form>
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
            <button type="submit">Update</button>
          </div>
        </form>
      ) : null}
    </div>
  );
}
