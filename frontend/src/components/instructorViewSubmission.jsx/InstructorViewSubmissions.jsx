import axios from "axios";
import moment from "moment";
import { useEffect, useState } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faImage, faFile } from "@fortawesome/free-regular-svg-icons";
import { faVideo ,faLink} from "@fortawesome/free-solid-svg-icons";

import "./instructorViewSubmissions.css";

export function InstructorViewSubmissions({
  submissionView,
  setSubmissionView,
}) {
  const [submissions, setSubmissions] = useState(null);

  const Cancel = () => {
    setSubmissionView(null);
  };

  const getSubmissions = async () => {
    try {
      const res = await axios.get(
        `http://localhost:9080/submissions/getSubmissions/${submissionView}`,
        { withCredentials: true },
      );
      setSubmissions(res.data);
      console.log(res.data);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  useEffect(() => {
    getSubmissions();
  }, []);

  return (
    <div className="ViewSubmissionMainDiv">
      <div className="ViewSubmissionTopDiv">
        <p className="">Submissions made</p>
        <button onClick={Cancel}>x</button>
      </div>
      {submissions?.length !== 0 ? (
        <div className="ViewSubmissionWrapperDiv">
          {submissions?.map((sub) => (
            <div key={sub.id} className="ViewSubmissionEachDiv">
              <p>Assignment: {sub.assignmentName}</p>
              <p>Submitted By: {sub.submittedByName}</p>
              <p>
                Submitted On:{" "}
                {moment(sub.submittedAt).format("Do MMM  YYYY ,HH:mm")}
              </p>
              <div>
                {sub.submissionType === "Document" ? (
                  <FontAwesomeIcon icon={faFile} className="SubmissionIcon" />
                ) : sub.submissionType === "Photo" ? (
                  <FontAwesomeIcon icon={faImage} className="SubmissionIcon" />
                ) : sub.submissionType === "Link" ? (
                  <FontAwesomeIcon icon={faLink} className="SubmissionIcon" />
                ) : (
                  <FontAwesomeIcon icon={faVideo} className="SubmissionIcon" />
                )}
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="NoSubmissionMadeDiv">
          <p>No submissions made yet.</p>
        </div>
      )}
    </div>
  );
}
