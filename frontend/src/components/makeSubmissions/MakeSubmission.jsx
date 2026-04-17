import { useState } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faX } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

export function MakeSubmission({ submitAssign, setSubmitAssign }) {
  const [formData, setFormData] = useState({
    fileType: "",
    link: "",
  });
  const [upload, setUpload] = useState([]);

  const { fileType, link } = formData;

  const OnChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const Cancel = () => {
    setSubmitAssign(null);
  };

  const HandleMakeSubmissions = async (e) => {
    e.preventDefault();

    const formDataToSend = new FormData();

    formDataToSend.append("fileType", fileType);
    if (fileType === "Link") {
      formDataToSend.append("link", link);
    } else {
      upload.forEach((file) => {
        formDataToSend.append("files", file);
      });
    }

    try {
      const resp = await axios.post(
        `http://localhost:9080/submissions/makeSubmission/${submitAssign}`,
        formDataToSend,
        {
          withCredentials: true,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        },
      );
      console.log(resp.data);
      setSubmitAssign(null);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  return (
    <div className="CreateAssignmentMainDiv">
      <form onSubmit={HandleMakeSubmissions}>
        <div className="ExitAssignmentIconDiv">
          <FontAwesomeIcon
            icon={faX}
            onClick={Cancel}
            id="ExitCreateAssignment"
          />
        </div>
        <div>
          <label htmlFor="">Upload type</label>
          <br />
          <select value={fileType} name="fileType" onChange={OnChange}>
            <option value="" disabled selected>
              Select upload type
            </option>
            <option value="Document">Document</option>
            <option value="Photo">Photo</option>
            <option value="Link">Link</option>
          </select>
          <br />
        </div>
        {fileType === "Document" || fileType === "Photo" ? (
          <div>
            <label htmlFor="UploadId">Uploads</label>
            <br />
            <input
              type="file"
              multiple
              onChange={(e) => setUpload([...e.target.files])}
              id="UploadId"
            />
            <br />
          </div>
        ) : fileType === "Link" ? (
          <div>
            <label htmlFor="LinkId">Link</label>
            <br />
            <input
              type="text"
              id="LinkId"
              value={link}
              onChange={OnChange}
              name="link"
            />
            <br />
          </div>
        ) : null}
        <div>
          <button type="submit">Submit</button>
        </div>
      </form>
    </div>
  );
}
