import React from "react";
import '../css/component/topbar.css'

import "@fontsource/josefin-sans"
import { Button } from "@mui/material";
import { Link } from "react-router-dom";

function Topbar() {
  return (
    <div className="topbar">
      <div className="topbarWrapper">
        <div className="topLeft">
          <span className="logo">Name Of App</span>
        </div>
        <div className="topRight">
          <span className="logInMsg">Logged in as:</span>
          <span className="logInUser">Dispatcher</span>
        </div>
        <Link to="/login">
          <Button variant="contained" color="error" className="logOut">Log Out</Button>
        </Link>
      </div>
    </div>
  );
}

export default Topbar;