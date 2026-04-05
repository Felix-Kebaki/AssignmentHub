import axios from "axios";
import { useEffect, useState } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faX } from "@fortawesome/free-solid-svg-icons";

import "../getAllUsers/getAllUsers.css"

export function GetAllUnits() {
  const [data, setData] = useState(null);
  const [refetch,setRefetch]=useState(false)

  const getAllUnits = async () => {
    try {
      const res = await axios.get("http://localhost:9080/units/getAllUnits", {
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

  const HandleDeleteUnit = async (getId) => {
    try {
      const res = await axios.delete(
        `http://localhost:9080/units/deleteUnit/${getId}`,
        {
          withCredentials: true,
        },
      );
      console.log(res);
      setRefetch(!refetch)
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  useEffect(() => {
    getAllUnits();
  }, [refetch]);

  return (
    <div className="GetAllUsersMainDiv">
      <p className="GetAllUsersMainTitle">All Units</p>
      <table>
        <thead>
          <tr>
            <th>Course name</th>
            <th>Course code</th>
            <th>Instructor</th>
          </tr>
        </thead>
        <tbody>
          {data !== null
            ? data?.map((unit) => (
                <tr key={unit.id}>
                  <td>{unit.courseName}</td>
                  <td>{unit.courseCode}</td>
                  <td>{unit.instructorName}</td>
                  <td onClick={()=>HandleDeleteUnit(unit.id)}>X</td>
                  {/* <FontAwesomeIcon
                    icon={faX}
                    onClick={() => HandleDeleteUnit(unit.id)}
                  /> */}
                </tr>
              ))
            : null}
        </tbody>
      </table>
    </div>
  );
}
