import axios from 'axios'
import { useEffect, useState } from 'react'

import '../getAllUsers/getAllUsers.css'
import { CreateAssignment } from '../createAssignment/CreateAssignment'

export function GetInstructorUnits() {
    const [units,setUnits]=useState(null)
    const [create,setCreate]=useState(null)
    const [hoverId,setHoverId]=useState(null)

    
  const HandleCreateAssingment = (getId) => {
    setCreate(getId);
  };

    const fetchUnits=async()=>{
        try{
            const res=await axios.get("http://localhost:9080/units/getYourUnits?role=INSTRUCTOR",{withCredentials:true})
            setUnits(res.data)
        }catch(error){
            console.error(error.response.data.errorMessage ||
                error.response.data ||
                error.response
            )
        }
    }

    useEffect(()=>{
        fetchUnits()
    },[create])

  return (
    <div className='GetAllUsersMainDiv'>
        <p className='GetAllUsersMainTitle'>Your Assigned Units</p>
        <table>
            <thead>
                <tr>
                    <th>Unit name</th>
                    <th>Unit code</th>
                    <th>Assignments</th>
                </tr>
            </thead>
            <tbody>
                {units?.map((unit)=>(
                    <tr key={unit.id} className='TableRawMainDiv' onMouseEnter={()=>setHoverId(unit.id)} onMouseLeave={()=>setHoverId(null)}>
                        <td>{unit.courseName}</td>
                        <td>{unit.courseCode}</td>
                        <td>{unit.assignments.length}</td>
                        {unit.id===hoverId?
                        <div className='AssignmentSubmitBtnDiv'><button onClick={()=>HandleCreateAssingment(unit.id)}>Add Assignment</button></div>:null}
                    </tr>
                ))}
            </tbody>
        </table>
        
      {create!==null ? (
        <div className="OverflowAddMainDiv">
          <CreateAssignment create={create} setCreate={setCreate} />
        </div>
      ) : null}
    </div>
  )
}
