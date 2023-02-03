import "../css/page/login.css"

import { useState } from "react";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
    };

    return (
        <div className="login">
            <div className="loginWrapper">

                <form id="loginform" onSubmit={handleSubmit}>
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
        </div>
    );
}

export default Login;