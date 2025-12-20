import { Link, useNavigate } from "react-router-dom";
import "../style/AuthFormPages.css";
import { useState } from "react";
import { useAuth } from "../util/AuthProvider";
import { useApi } from "../hooks/useApi";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { setToken } = useAuth();
  const navigate = useNavigate();
  const {
    data: message,
    post,
    error,
  } = useApi<string>(`${import.meta.env.VITE_API_URL}auth/generateToken`);

  const login = async (element: React.FormEvent<HTMLFormElement>) => {
    element.preventDefault();
    console.log("email", email);
    console.log("password", password);

    const body = {
      email: email,
      password: password,
    };

    const res = await post("", body)

    if (res?.data) {
    setToken(res.data); // update context
    console.log(res.message)
    navigate("/"); // redirect to home
    }

  };

  return (
    <div id="container">
      <div id="login-box">
        <div></div>
        <h1>LOGIN</h1>
        {error && <p className="error">{error}</p>}
        <form method="post" id="form" onSubmit={login}>
          <label>Email</label>
          <input
            type="text"
            className="input-field"
            onChange={(e) => setEmail(e.target.value)}
          ></input>
          <label>Password</label>
          <input
            type="password"
            className="input-field"
            onChange={(e) => setPassword(e.target.value)}
          ></input>
          <input type="submit" className="btn"></input>
          <Link to="/register-user">Register as user</Link>
          <Link to="/register-restaurant">Register as restaurant</Link>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
