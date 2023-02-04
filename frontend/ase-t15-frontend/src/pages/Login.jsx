import "../css/page/login.css"

import { login } from "../actions/auth";
import { useDispatch } from "react-redux";
import { useState } from "react";
import axios from "axios";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const dispatch = useDispatch();

    const handleLogin = (e) => {
        axios.post("http://localhost:8000/api/auth", {}, {
            auth: {
                username: email,
                password: password

            }
        }
        ).then((response) => {
            console.log(response);
            dispatch(login(response.data));
        }
        ).catch((error) => {
            console.log(error);
        }
        );

    };

    return (
        <div className="login">
            <div className="loginWrapper">

                <form id="loginform" onSubmit={handleLogin}>
                    <div className="form-group p-2">
                        <label>Email address</label>
                        <input
                            type="email"
                            className="form-control"
                            id="EmailInput"
                            name="EmailInput"
                            aria-describedby="emailHelp"
                            placeholder="Enter email"
                            onChange={(event) => setEmail(event.target.value)}
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