import { useContext, useState } from "react";
import React from "react";
import {authContext} from "../../contexts/AuthContext";

function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const {auth, setAuth} = useContext(authContext)

    const handleSubmit = async (event) => {
        event.preventDefault()
        const formData = new FormData()
        formData.append('username', username)
        formData.append('password', password)
        await fetch('/login', {
            method: 'POST',
            body: formData
        }).then(() => setAuth(true))
    };

    return (
        <div>
            <h1>Login</h1>
            <form onSubmit={ handleSubmit }>
                <label htmlFor="username">Username:</label>
                <input
                    type="username"
                    id="username"
                    value={ username }
                    onChange={ (e) => setUsername(e.target.value) }
                />
                <br />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    id="password"
                    value={ password }
                    onChange={ (e) => setPassword(e.target.value) }
                />
                <br />
                <button type="submit">Login</button>
            </form>
        </div>
    );
}

export default Login;