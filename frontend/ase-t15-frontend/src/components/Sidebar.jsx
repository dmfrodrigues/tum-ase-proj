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


function Sidebar() {
  const [activeIndex, setActiveIndex] = useState(false);
  const selectSidebar = () => selectSidebar(activeIndex);
  const location = useLocation();


  return (
    <div className="sidebar">
      <div className="sidebarWrapper">
        <div className="sidebarMenu">
          <ul className="sidebarList" onClick={selectSidebar}>
            <Link to="/orders" className="link">
              <li className={`sidebarListItem ${location.pathname == "/orders" ? "active" : ""}`}>
                <LocalShippingOutlined className="sidebarIcon" />
                Manage Orders
              </li>
            </Link>
            <Link to="/users" className="link">
              <li className={`sidebarListItem ${location.pathname == "/users" ? "active" : ""}`}>
                <PersonOutlineOutlined fontSize="large" className='sidebarIcon' />
                Manage Users
              </li>
            </Link>
            <Link to="/boxes" className="link">
              <li className={`sidebarListItem ${location.pathname == "/boxes" ? "active" : ""}`}>
                <InventoryOutlined className="sidebarIcon" />
                Manage Boxes
              </li>
            </Link>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default Sidebar;