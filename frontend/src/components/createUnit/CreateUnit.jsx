import axios from "axios";
import React, { useEffect, useState } from "react";

export function CreateUnit() {
  const [selectedCourses, setSelectedCourses] = useState(null);
  const [data, setData] = useState(null);
  const [formData, setFormData] = useState({
    unitName: "",
    unitCode: "",
    instructorEmail: "",
  });
  const { unitName, unitCode, instructorEmail } = formData;

  const OnChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const HandleSelectChange = (e) => {
    const values = Array.from(
      e.target.selectedOptions,
      (option) => option.value,
    );
    setSelectedCourses(values);
  };

  const HandleCreateUnit = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post(
        "http://localhost:9080/units/createUnit",
        { ...formData, courseList: selectedCourses },
        { withCredentials: true },
      );
      console.log(res);
      setFormData({
        unitCode: "",
        unitName: "",
        instructorEmail: "",
      });
      setSelectedCourses(null);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  const getAllCourses = async () => {
    try {
      const res = await axios.get(
        "http://localhost:9080/course/getAllCourses",
        { withCredentials: true },
      );
      setData(res.data);
    } catch (error) {
      console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
    }
  };

  const getAllInstructor=async()=>{
    
  }

  useEffect(() => {
    getAllCourses();
  }, []);

  return (
    <div>
      <form onSubmit={HandleCreateUnit}>
        <div className="EachProfileInputDiv">
          <label htmlFor="courseNameId">Unit name</label>
          <br />
          <input
            type="text"
            id="courseNameId"
            name="unitName"
            value={unitName}
            onChange={OnChange}
          />
          <br />
        </div>
        <div className="EachProfileInputDiv">
          <label htmlFor="courseCodeId">Unit code</label>
          <br />
          <input
            type="text"
            id="courseCodeId"
            name="unitCode"
            value={unitCode}
            onChange={OnChange}
          />
          <br />
        </div>
        <div className="EachProfileInputDiv">
          <label htmlFor="instructorId">Instructor</label>
          <br />
          <input
            type="email"
            id="instructorId"
            name="instructorEmail"
            value={instructorEmail}
            onChange={OnChange}
          />
          <br />
        </div>
        {data !== null ? (
          <div className="EachProfileInputDiv">
            <label htmlFor="CourseId">Courses</label>
            <br />
            <select multiple onChange={HandleSelectChange}>
              {data?.map((course) => (
                <option value={course.courseName}>{course.courseName}</option>
              ))}
            </select>
            <br />
          </div>
        ) : null}
        <div className="SubmitProfileBtnDiv">
          <button type="submit">Create</button>
        </div>
      </form>
    </div>
  );
}
