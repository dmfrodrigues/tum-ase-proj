import React from "react";
import '../css/component/topbar.css'
import { useDispatch, useSelector } from "react-redux";

import "@fontsource/josefin-sans"
import { Button } from "@mui/material";
import { Link } from "react-router-dom";
import { logout } from "../actions/auth";

function Topbar() {
  const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);
  const dispatch = useDispatch();
  const user = useSelector((state) => state.auth.user);

  const handleLogout = () => {
    dispatch(logout());
    window.location.reload();
  }

  return (
    <div className="topbar">
      <div className="topbarWrapper">
        <div className="topLeft">
          <span className="logo">Name Of App</span>
        </div>
        <div className="topRight">
          {isLoggedIn ? <span className="logInMsg">Logged in as:</span> : <span className="logInMsg">Not logged in</span>}
          {isLoggedIn && <span className="logInUser">{user.username}</span>}
        </div>
        {isLoggedIn ?
          <Button variant="contained" color="error" className="logOut" onClick={handleLogout}>Log Out</Button>
          : <Link to="/login">
            <Button variant="contained" color="success" className="logIn">Log In</Button>
          </Link>
        }
      </div>
    </div>
  );
}

export default Topbar;