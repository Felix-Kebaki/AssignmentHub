import axios from "axios";
import { useEffect, useState } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faX } from "@fortawesome/free-solid-svg-icons";

import "./getOneAssignment.css";

export function GetOneAssignment({ view, setView }) {
  const [assignment, setAssignment] = useState(null);

  const Cancel=()=>{
    setView(null);
  }

  const getAssignment = async () => {
    try {
      const res = await axios.get(
        `http://localhost:9080/assignments/getAssignment/${view}`,
        { withCredentials: true },
      );
      setAssignment(res.data);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  useEffect(() => {
    getAssignment();
  }, []);

  return (
    <div className="ViewAssignmentMain">
      <div className="ViewAssignmentCancelDiv">
        <FontAwesomeIcon icon={faX} onClick={Cancel} id="ExitViewAssignment" />
      </div>
      {assignment !== null ? (
        assignment.resourceType === "Photo" ? (
          <>
            {assignment.fileUrls.map((url, index) => (
              <img src={url} alt="" key={index} />
            ))}
          </>
        ) : assignment.resourceType === "Document" ? (
          <div className="DocumentContainer">
            {assignment.fileUrls.map((url, index) => {
              const isPdf = url.endsWith(".pdg");
              return isPdf ? (
                <iframe
                  key={index}
                  src={url}
                  title={`pdf-${index}`}
                  className="DocumentFrame"
                />
              ) : (
                <iframe
                  key={index}
                  src={`https://docs.google.com/gview?url=${url}&embedded=true`}
                  title={`doc-${index}`}
                  className="DocumentFrame"
                />
              );
            })}
          </div>
        ) : null
      ) : null}
    </div>
  );
}
