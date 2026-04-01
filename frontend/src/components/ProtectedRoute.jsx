import { Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";

export function ProtectedRoute({ children }) {
  const [isAuth, setIsAuth] = useState(null);
  async function checkAuth(){
      const res=await axios.get("http://localhost:9080/auth/me", {
        withCredentials: true,
      })
      .then(() => {
        setIsAuth(true);
      })
      .catch((error) => {
        setIsAuth(false);
        console.error(error.response || error);
      });
  }

  useEffect(() => {
    checkAuth()
  }, []);

  if (isAuth === null) return <div>Loading...</div>;

  return isAuth ? children : <Navigate to="/login" />;
}
