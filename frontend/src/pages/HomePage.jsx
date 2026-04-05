import axios from "axios";
import { useEffect, useState } from "react";
import { CreateUser } from "../components/createUser/CreateUser";
import { CreateCourse } from "../components/createCourse/CreateCourse";
import { GetAllCourses } from "../components/getAllCourses/GetAllCourses";
import { GetAllUsers } from "../components/getAllUsers/GetAllUsers";

import "../index.css";
import { GetAllUnits } from "../components/getAllUnits/GetAllUnits";
import { CreateUnit } from "../components/createUnit/CreateUnit";

export function HomePage() {
  const [data, setData] = useState({});
  const [units, setUnits] = useState(null);

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

  const fetchYourUnits = async () => {
    try {
      const response = await axios.get(
        "http://localhost:9080/units/getYourUnits",
        { withCredentials: true },
      );
      setUnits(response.data);
    } catch (error) {
      console.errror(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  useEffect(() => {
    fetchProfile();
    fetchYourUnits();
  }, []);

  return (
    <div className="HomePageMainDiv">
      <section>
        <p>Hello {data.firstName}, welcome to AssignmentHub.</p>
      </section>

      {data.role == "ADMIN" ? (
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

      {data?.role == "STUDENT" ? (
        <div>
          {units !== null ? (
            <div>
              {units.map((unit) => (
                <div key={unit.id}>
                  <p>{unit.courseName}</p>
                  <p>{unit.instructorName}</p>
                </div>
              ))}
            </div>
          ) : null}
        </div>
      ) : null}
    </div>
  );
}
