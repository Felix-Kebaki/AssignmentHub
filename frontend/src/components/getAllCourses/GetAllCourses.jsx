import axios from "axios"
import { useEffect, useState } from "react"

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import {faX} from '@fortawesome/free-solid-svg-icons'

import "./getAllCourses.css"

export function GetAllCourses() {
    const [data,setData]=useState(null)
    const [refetch,setRefetch]=useState(false);
    const [hoverId,setHoverId]=useState(null)

    const getAll=async()=>{
        try {
            const res=await axios.get("http://localhost:9080/course/getAllCourses",{withCredentials:true})
            setData(res.data)
            console.log(res)
        } catch (error) {
            console.error(error.response.data.errorMessage || error.response.data || error.response)
        }
    }

    const HandleDeleteCourse=async(getId)=>{
        try {
            const res=await axios.delete(`http://localhost:9080/course/deleteCourse/${getId}`,{withCredentials:true})
            setRefetch(!refetch);
        } catch (error) {
              console.error(error.response.data.errorMessage || error.response.data || error.response)
        }
    }

    useEffect(()=>{
        getAll();
    },[refetch])

  return (
    <div className="GetAllCoursesMainDiv">
        <p className="GetAllCoursesMainTitle">All Courses</p>
        {data!==null?
            data?.map((course)=>(
                <div key={course.id} 
                  className="TableRawMainDiv EachCourseMainDiv"
                  onMouseEnter={() => setHoverId(course.id)}
                  onMouseLeave={() => setHoverId(null)}>
                    <p>{course.courseName}</p>
                    {hoverId === course.id ? (
                    <div className="AssignmentSubmitBtnDiv">
                      <button onClick={() => HandleDeleteCourse(course.id)}>
                        Delete
                      </button>
                    </div>
                  ) : null}
                </div>
            ))
        :null}
    </div>
  )
}
