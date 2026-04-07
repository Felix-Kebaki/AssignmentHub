import axios from "axios";
import { useEffect, useState } from "react";
import { CreateUser } from "../components/createUser/CreateUser";
import { CreateCourse } from "../components/createCourse/CreateCourse";
import { GetAllCourses } from "../components/getAllCourses/GetAllCourses";
import { GetAllUsers } from "../components/getAllUsers/GetAllUsers";

import "../index.css";
import { GetAllUnits } from "../components/getAllUnits/GetAllUnits";
import { CreateUnit } from "../components/createUnit/CreateUnit";
import { GetYourUnits } from "../components/getYourUnits/GetYourUnits";
import { GetInstructorUnits } from "../components/getInstructorUnits/GetInstructorUnits";
import { GetInstructorAssignments } from "../components/getInstructorAssignments/GetInstructorAssignments";

export function HomePage() {
  const [data, setData] = useState(null);

  const fetchProfile = async () => {
    try {
      const response = await axios.get(`http://localhost:9080/auth/profile`, {
        withCredentials: true,
      });
      console.log(response.data)
      setData(response.data);
    } catch (error) {
      console.error(error.response.data.errorMessage || error.response);
    }
  };

  useEffect(() => {
    fetchProfile();
  }, []);

  return (
    <div className="HomePageMainDiv">
      <section>
        <p>Hello {data?.firstName}{data?.role==="STUDENT"? <span>({data?.courseName} student)</span>:null}, welcome to AssignmentHub.</p>
      </section>

      {data?.role === "ADMIN" ? (
        <>
          <section className="CreateUserAndCourseSec">
            <CreateUser />
            <div>
              <CreateCourse />
              <CreateUnit />
            </div>
          </section>
          <section>
            <GetAllUsers />
          </section>
          <section>
            <GetAllCourses />
          </section>
          <section>
            <GetAllUnits />
          </section>
        </>
      ) : null}

      {data?.role === "STUDENT" ? (
        <>
          <section>
            <GetYourUnits/>
          </section>
        </>
      ) : null}

      {data?.role === "INSTRUCTOR"?(
        <>
          <GetInstructorUnits/>
          <GetInstructorAssignments/>
        </>
      ):null}

    </div>
  );
}
