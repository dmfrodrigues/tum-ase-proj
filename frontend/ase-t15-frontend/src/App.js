import 'bootstrap/dist/css/bootstrap.min.css';

import Sidebar from './components/Sidebar'
import Topbar from "./components/Topbar";
import "./App.css";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UserList from "./pages/UserList";
import User from "./pages/User";
import NewUser from "./pages/NewUser";
import OrderList from "./pages/OrderList";
import Order from "./pages/Order";
import NewProduct from "./pages/NewProduct";
function App() {
  return (
    <Router>
      <Topbar />
      <div className="container">
        <Sidebar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/users" element={<UserList />} />
          <Route path="/orders" element={<OrderList />} />
          <Route path="/boxes" element={<OrderList />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;