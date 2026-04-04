import axios from "axios";
import { useEffect, useState } from "react";

export function HomePage() {
  const [data, setData] = useState({});
  const [units,setUnits]=useState(null)
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "student",
    role: "",
    courseName:""
  });
  const { firstName, lastName, email, password, role ,courseName} = formData;

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
    } catch (error) {
      console.error(error.response.data.errorMessage || error.response);
    }
  };

  const fetchProfile = async () => {
    try {
      const response = await axios.get(`http://localhost:9080/auth/profile`, {
        withCredentials: true,
      });
      setData(response.data);
    } catch (error) {
      console.error(error.response.data.errorMessage || error.response);
    }
  };

  const fetchYourUnits=async()=>{
    try{
      const response=await axios.get("http://localhost:9080/units/getYourUnits",{withCredentials:true})
      setUnits(response.data)
    }catch(error){
      console.errror(error.response.data.errorMessage || error.response.data || error.response)
    }
  }

  useEffect(() => {
    fetchProfile();
    fetchYourUnits();
  }, []);

  return (
    <section>
      <div>
        <p>{data.firstName} welcome to AssignmentHub.</p>
      </div>
      {data.role == "ADMIN" ? (
        <div>
          <div>

          </div>
          <form onSubmit={HandleSubmitCreateUser}>
            <div>
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
            <div>
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
            <div>
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
            <div>
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
            <div>
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
            <div>
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
            <div>
              <button type="submit">Create</button>
            </div>
          </form>
        </div>
      ) : null}
      {
        data?.role=="STUDENT"?<div>
          {units!==null?<div>
            {units.map((unit)=>(
              <div key={unit.id}>
                <p>{unit.courseName}</p>
                <p>{unit.instructorName}</p>
              </div>
            ))}
          </div>:null}
        </div>:null
      }
    </section>
  );
}
