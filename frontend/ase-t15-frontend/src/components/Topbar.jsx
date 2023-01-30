import React from "react";
import '../css/component/topbar.css'

import "@fontsource/josefin-sans"

function Topbar() {
  return (
    <div className="topbar">
      <div className="topbarWrapper">
        <div className="topLeft">
          <span className="logo">Name Of App</span>
        </div>
        <div className="topRight">
          <span className="logIn">Logged in as: Dispatcher</span>
        </div>
      </div>
    </div>
  );
}

export default Topbar;