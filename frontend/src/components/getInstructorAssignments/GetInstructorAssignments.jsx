import axios from "axios";
import { useEffect, useState } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faImage, faFile } from "@fortawesome/free-regular-svg-icons";
import { faVideo } from "@fortawesome/free-solid-svg-icons";

import "./getInstructorAssignment.css";
import "../getAllUsers/getAllUsers.css";
import { GetOneAssignment } from "../getOneAssignment/getOneAssignment";

export function GetInstructorAssignments() {
  const [assignmets, setAssignments] = useState([]);
  const [view,setView]=useState(null)

  const HandleOpenResource=(getId)=>{
    setView(getId)
  }

  const fetchAssignments = async () => {
    try {
      const res = await axios.get(
        "http://localhost:9080/assignments/InstructorAssignmentView",
        { withCredentials: true },
      );
      setAssignments(res.data);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  useEffect(() => {
    fetchAssignments();
  }, [view]);

  return (
    <div className="GetAllUsersMainDiv">
      <p className="GetAllUsersMainTitle">Assignments created</p>
      {assignmets.length !== 0 ? (
        <div className="InstructorAssignmentViewMainDiv">
          {assignmets?.map((assign) => (
            <div key={assign.id} className="InstructorAssignmentEachMainDiv" onClick={()=>HandleOpenResource(assign.id)}>
              <div className="AssignmentIconMainDiv">
                {assign.resourceType === "Document" ? (
                  <FontAwesomeIcon icon={faFile} className="AssignmentIcon" />
                ) : assign.resourceType === "Photo" ? (
                  <FontAwesomeIcon icon={faImage} className="AssignmentIcon" />
                ) : (
                  <FontAwesomeIcon icon={faVideo} className="AssignmentIcon" />
                )}
                <p>{assign.assignmentName}</p>
              </div>
              <div>
                <p>{assign.unitName}</p>
                <p>{assign.unitCode}</p>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="NoAssigmentsDiv">
          <p>No Assignments created</p>
        </div>
      )}

      {view !==null ?
      <div className="OverflowAddMainDiv">
        <GetOneAssignment view={view} setView={setView}/>
      </div>:null}
    </div>
  );
}
