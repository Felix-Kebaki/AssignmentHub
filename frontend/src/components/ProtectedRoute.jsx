import { Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

export function ProtectedRoute({ children }) {
  const [isAuth, setIsAuth] = useState(null);

  useEffect(() => {
    axios.get("http://localhost:9090/auth/me", {
      withCredentials: true
    })
    .then(() => setIsAuth(true))
    .catch((error) => {setIsAuth(false)
        console.log(error.response)});
  }, []);

  if (isAuth === null) return <div>Loading...</div>;

  return isAuth ? children : <Navigate to="/login" />;
}