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
import OrderDetail from "./pages/OrderDetail";
import PrivateRoute from './pages/PrivateRoute';


function App() {
  return (
    <div>
      <Router>
        <Topbar />

        <div className="container">
          <Sidebar />
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/users" element={
              <PrivateRoute>
                <UserList />
              </PrivateRoute>
            } />
            <Route path="/orders" element={
              <PrivateRoute>
                <OrderList />
              </PrivateRoute>
            } />
            <Route path="/boxes" element={
              <PrivateRoute>
                <BoxList />
              </PrivateRoute>
            } />
            <Route path="/orders/:id" element={
              <PrivateRoute>
                <OrderDetail />
              </PrivateRoute>
            } />
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default App;