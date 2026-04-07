import { useEffect, useState } from "react";
import axios from "axios";

import "../getAllUsers/getAllUsers.css";

export function GetYourUnits() {
  const [units, setUnits] = useState(null);

  const fetchYourUnits = async () => {
    try {
      const response = await axios.get(
        "http://localhost:9080/units/getYourUnits",
        { withCredentials: true },
      );
      setUnits(response.data);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  useEffect(() => {
    fetchYourUnits();
  }, []);

  return (
    <div className="GetAllUsersMainDiv">
      <p className="GetAllUsersMainTitle">Your Units</p>
      {units !== null ? (
        <table>
          <thead>
            <tr>
              <th>Unit course</th>
              <th>Unit code</th>
              <th>Instructor</th>
              <th>Assignments</th>
            </tr>
          </thead>
          <tbody>
            {units?.map((unit) => (
              <tr key={unit.id}>
                <td>{unit.courseName}</td>
                <td>{unit.courseCode}</td>
                <td>{unit.instructorName}</td>
                <td>{unit.assignments.length}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : null}
      {/* {(units!==null && units?.assignments.length !== 0 )? (
        <>
          <p className="GetAllUsersMainTitle">Your Assignments</p>

        </>
      ) : null} */}
    </div>
  );
}
