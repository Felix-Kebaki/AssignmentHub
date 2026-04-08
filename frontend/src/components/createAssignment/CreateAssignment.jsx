import React, { useState } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faX } from "@fortawesome/free-solid-svg-icons";

import "./createAssignment.css";
import axios from "axios";

export function CreateAssignment({ create, setCreate }) {
  const [upload, setUpload] = useState([]);
  const [formData, setFormData] = useState({
    assignmentName: "",
    dueDate:"",
    fileType: "",
    link: ""
  });
  const { assignmentName, fileType, link ,dueDate} = formData;

  const OnChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const Cancel = () => {
    setCreate(null);
  };

  const HandleCreateAssignment = async (e) => {
    e.preventDefault();
    const formDataToSend = new FormData();

    formDataToSend.append("assignmentName", assignmentName);
    formDataToSend.append("fileType", fileType);
    formDataToSend.append("dueDate",dueDate)

    if (fileType === "Link") {
      formDataToSend.append("link", link);
    } else {
      upload.forEach((file) => {
        formDataToSend.append("files", file);
      });
    }
    try {

      const res = await axios.post(
        `http://localhost:9080/assignments/create/${create}`,
        formDataToSend,
        {
          withCredentials: true,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        },
      );
      console.log(res.data);
      setFormData({
        assignmentName: "",
        dueDate:"",
        fileType: "",
        link: "",
      });
      setUpload([]);
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
      <div className="ExitAssignmentIconDiv">
        <FontAwesomeIcon
          icon={faX}
          onClick={Cancel}
          id="ExitCreateAssignment"
        />
      </div>
      <form onSubmit={HandleCreateAssignment}>
        <div className="EachProfileInputDiv">
          <label htmlFor="AssignmentId">Assignment name</label>
          <br />
          <input
            type="text"
            id="AssignmentId"
            name="assignmentName"
            value={assignmentName}
            onChange={OnChange}
          />
          <br />
        </div>
        <div className="EachProfileInputDiv">
          <label htmlFor="DueId">Due date</label>
          <br />
          <input
            type="date"
            id="DueId"
            name="dueDate"
            value={dueDate}
            onChange={OnChange}
          />
          <br />
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
          <button type="submit">Create</button>
        </div>
      </form>
    </div>
  );
}
