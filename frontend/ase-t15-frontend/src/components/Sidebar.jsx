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

import "@fontsource/josefin-sans"

function Sidebar() {
  return (
    <div className="sidebar">
      <div className="sidebarWrapper">
        <div className="sidebarMenu">
          <ul className="sidebarList">
            <Link to="/orders" className="link">
              <li className="sidebarListItem active">
                <LocalShippingOutlined className="sidebarIcon" />
                Manage Orders
              </li>
            </Link>
            <Link to="/users" className="link">
              <li className="sidebarListItem">
                <PersonOutlineOutlined fontSize="large" className='sidebarIcon' />
                Manage Users
              </li>
            </Link>
            <Link to="/boxes" className="link">
              <li className="sidebarListItem">
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