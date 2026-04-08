import axios from "axios";
import { useEffect, useState } from "react";

import "../getAllUsers/getAllUsers.css"

export function GetAllUnits() {
  const [data, setData] = useState(null);
  const [refetch,setRefetch]=useState(false)
  const [hoverId,setHoverId]=useState(null)

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
                <tr key={unit.id} 
                  className="TableRawMainDiv"
                  onMouseEnter={() => setHoverId(unit.id)}
                  onMouseLeave={() => setHoverId(null)}>
                  <td>{unit.courseName}</td>
                  <td>{unit.courseCode}</td>
                  <td>{unit.instructorName}</td>
                  {hoverId === unit.id ? (
                    <div className="AssignmentSubmitBtnDiv">
                      <button onClick={() => HandleDeleteUnit(unit.id)}>
                        Delete
                      </button>
                    </div>
                  ) : null}
                </tr>
              ))
            : null}
        </tbody>
      </table>
    </div>
  );
}
