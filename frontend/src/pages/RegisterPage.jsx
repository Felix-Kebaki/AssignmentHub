import { useEffect, useState } from "react";
import axios from "axios";

export function RegisterPage() {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    role: "",
    course: "",
  });
  const { firstName, lastName, email, role, course } = formData;

  const OnChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const fetchCourses=async()=>{
    try {
        const data=await axios.get("http://localhost:9080/course/getAllCourses")
        console.log(data)
    } catch (error) {
        console.error(error.message || error)
    }
  }

  useEffect(() => {
    fetchCourses();
  }, []);

  return (
    <section>
      <div>
        <form>
          <div>
            <label htmlFor="FirstNameId">First name</label>
            <br />
            <input
              type="text"
              name="firstName"
              value={firstName}
              onChange={OnChange}
              id="FirstNameId"
            />
            <br />
          </div>
          <div>
            <label htmlFor="LastNameId">Last name</label>
            <br />
            <input
              type="text"
              name="lastName"
              value={lastName}
              onChange={OnChange}
              id="LastNameId"
            />
            <br />
          </div>
          <div>
            <label htmlFor="EmailId">Email</label>
            <br />
            <input
              type="text"
              name="email"
              value={email}
              onChange={OnChange}
              id="EmailId"
            />
            <br />
          </div>
          <div>
            <label htmlFor="">Role</label>
            <br />
            <select></select>
            <br />
          </div>
          {role === "STUDENT" ? (
            <div>
              <label htmlFor="CourseId">Course</label>
              <br />
              <input
                type="text"
                name="course"
                value={course}
                onChange={OnChange}
                id="CourseId"
              />
              <br />
            </div>
          ) : null}
          <div>
            <button type="submit">Create</button>
          </div>
        </form>
      </div>
    </section>
  );
}
