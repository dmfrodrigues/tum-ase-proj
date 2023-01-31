import 'bootstrap/dist/css/bootstrap.min.css';

import Sidebar from './components/Sidebar'
import Topbar from "./components/Topbar";
import "./App.css";
import Home from "./pages/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UserList from "./pages/UserList";
import OrderList from "./pages/OrderList";
import BoxList from "./pages/BoxList";
import Login from './pages/Login';
import Order from './pages/Order';

function App() {
  return (
    <Router>
      <Topbar />
      <div className="container">
        <Sidebar />
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/users" element={<UserList />} />
          <Route path="/orders" element={<OrderList />} />
          <Route path="/boxes" element={<BoxList />} />
          <Route path="/order/:id" element={<Order />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;