import axios from "axios";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faX } from "@fortawesome/free-solid-svg-icons";

import "./getAllUsers.css";

export function GetAllUsers() {
  const [data, setData] = useState(null);
  const [refetch, setRefetch] = useState(false);
  const [hoverId, setHoverId] = useState(null);

  const getAllUsers = async () => {
    try {
      const res = await axios.get("http://localhost:9080/auth/getAllUsers", {
        withCredentials: true,
      });
      setData(res.data);
      console.log(res);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  const HandleDeleteUser = async (getId) => {
    try {
      const res = await axios.delete(
        `http://localhost:9080/auth/deleteUser/${getId}`,
        { withCredentials: true },
      );
      setRefetch(!refetch);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  useEffect(() => {
    getAllUsers();
  }, [refetch]);

  return (
    <div className="GetAllUsersMainDiv">
      <p className="GetAllUsersMainTitle">All Users</p>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
          </tr>
        </thead>
        <tbody>
          {data !== null
            ? data?.map((user) => (
                <tr
                  key={user.id}
                  className="TableRawMainDiv"
                  onMouseEnter={() => setHoverId(user.id)}
                  onMouseLeave={() => setHoverId(null)}
                >
                  <td>
                    {user.firstName} {user.lastName}
                  </td>
                  <td>{user.email}</td>
                  <td>{user.role}</td>
                  {hoverId === user.id ? (
                    <div className="AssignmentSubmitBtnDiv">
                      <button onClick={() => HandleDeleteUser(user.id)}>
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
