import '../css/component/sidebar.css'

import {
  Inventory,
  InventoryOutlined,
  LocalShipping,
  LocalShippingOutlined,
  Person,
  PersonOutline,
  PersonOutlineOutlined,
} from "@mui/icons-material";
import { Link } from "react-router-dom";
import { useLocation } from 'react-router-dom'

import "@fontsource/josefin-sans"
import { useState } from 'react';
import { useSelector } from 'react-redux'


function Sidebar() {
  const location = useLocation();
  const auth = useSelector(state => state.auth)

  let noBar = () => {
    return (
      <div className="sidebar" >
      </div>
    )
  }

  let bar = () => {
    return < div className="sidebar" >
      <div className="sidebarWrapper">
        <div className="sidebarMenu">
          <ul className="sidebarList">
            <Link to="/orders" className="link">
              <li className={`sidebarListItem ${location.pathname == "/orders" ? "active" : ""}`}>
                <LocalShippingOutlined className="sidebarIcon" />
                Manage Orders
              </li>
            </Link>
            {auth.user.role === "DISPATCHER" &&
              <Link to="/users" className="link">
                <li className={`sidebarListItem ${location.pathname == "/users" ? "active" : ""}`}>
                  <PersonOutlineOutlined fontSize="large" className='sidebarIcon' />
                  Manage Users
                </li>
              </Link>
            }
            {auth.user.role === "DISPATCHER" &&
              <Link to="/boxes" className="link">
                <li className={`sidebarListItem ${location.pathname == "/boxes" ? "active" : ""}`}>
                  <InventoryOutlined className="sidebarIcon" />
                  Manage Boxes
                </li>
              </Link>
            }
          </ul>
        </div>
      </div>
    </div >
  }

  return auth.isLoggedIn ? bar() : noBar()
}

export default Sidebar;