import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import './login.css'

export function Login() {
  const [errorMessage, setErrorMessage] = useState(null);
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const { email, password } = formData;
  const navigate = useNavigate();

  const OnChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const HandleSubmitForm = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:9080/auth/login",
        formData,
        { withCredentials: true },
      );
      navigate("/");
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
      setErrorMessage(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
      setTimeout(() => {
        setErrorMessage(null);
      }, 3000);
    }
  };

  return (
      <div className="LoginMainDiv">
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
          <div className="LoginBtnMainDiv">
            <button type="submit">Login</button>
          </div>
          {errorMessage !== null ? <pre>{errorMessage}</pre> : null}
        </form>
      </div>
  );
}
