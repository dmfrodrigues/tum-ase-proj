import "../css/page/login.css"

import { useNavigate } from 'react-router-dom';

import { login } from "../actions/auth";
import { useDispatch, useSelector } from "react-redux";
import { useState } from "react";

function Login() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const dispatch = useDispatch();
    const isLoggedIn = useSelector((state) => state.auth.isLoggedIn);

    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        dispatch(login(username, password)).then((response) => {
            navigate("/users");
            window.location.reload();
        }
        ).catch(() => {
            alert("Login failed");
        }
        );

        console.log(state);

    };

    return (
        <div className="login">
            <div className="loginWrapper">
                <h1>Welcome! Please Log In</h1>
                <form id="loginform" onSubmit={handleLogin}>
                    <div className="form-group p-2">
                        <label>Username</label>
                        <input
                            type="text"
                            className="form-control"
                            id="userInput"
                            name="userInput"
                            aria-describedby="userHelp"
                            placeholder="Enter username"
                            onChange={(event) => setUsername(event.target.value)}
                        />
                    </div>
                    <div className="form-group p-2">
                        <label>Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="exampleInputPassword1"
                            placeholder="Password"
                            onChange={(event) => setPassword(event.target.value)}
                        />
                    </div>
                    <button type="submit" className="btn btn-primary p-2 loginSubmit">
                        Log In
                    </button>
                </form>
            </div>

            <button className="btn btn-primary p-2 loginSubmit" onClick={handleLogin}>
                Login
            </button>
        </div>
    );
}

export default Login;