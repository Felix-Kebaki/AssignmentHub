import axios from "axios";
import { useEffect, useState } from "react"
import { MakeSubmission } from "../makeSubmissions/MakeSubmission";


export function GetAssignmentsStudent() {
    const [hoverId,setHoverId]=useState(null)
    const [assignment,setAssignment]=useState(null)
    const [submitAssign,setSubmitAssign]=useState(null)

    const HandleClickSubmit=(getId)=>{
        setSubmitAssign(getId)
    }

    const fetchYourAssignment=async()=>{
        try {
            const res=await axios.get("http://localhost:9080/assignments/getAssignmentsForStudent",{withCredentials:true})
            console.log(res.data)
            setAssignment(res.data)
        } catch (error) {
                  console.error(
        error.response.data.errorMessage ||
          error.response.data ||
          error.response,
      );
        }
    }

    useEffect(()=>{
        fetchYourAssignment();
    },[])

  return (
    <div className="GetAllUsersMainDiv">
        <p className="GetAllUsersMainTitle">Your Assignments</p>
        {assignment!==null?
        <table>
            <thead>
                <tr>
                    <th>Assignment</th>
                    <th>Unit name</th>
                    <th>Unit code</th>
                    <th>Due date</th>
                </tr>
            </thead>
            <tbody>
                {assignment?.map((assign)=>(
                    <tr key={assign.id} onMouseEnter={()=>setHoverId(assign.id)} onMouseLeave={()=>setHoverId(null)} className="TableRawMainDiv">
                        <td>{assign.assignmentName}</td>
                        <td>{assign.unitName}</td>
                        <td>{assign.unitCode}</td>
                        <td></td>
                        {assign.id === hoverId? 
                        <div className="AssignmentSubmitBtnDiv"><button onClick={()=>HandleClickSubmit(assign.id)}>Submit</button></div>:null}
                    </tr>
                ))}
            </tbody>
        </table>:
        <p>You have no assignment</p>}

        {
            submitAssign!==null?<div className="OverflowAddMainDiv">
                <MakeSubmission/>
            </div>:null
        }
    </div>
  )
}
