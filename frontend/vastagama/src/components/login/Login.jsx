import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/atividade";
import { Button, Form } from "react-bootstrap";
import axios from "axios";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [ token, setToken ] = useState(localStorage.getItem('token') || '');
  const navigate = useNavigate();

  async function login(username, password) {
    try {
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
  
      if (!response.ok) {
        throw new Error("Erro ao fazer login");
      }
      
      const data = await response.json();
      localStorage.setItem("accessToken", data.accessToken);

      return data;
    } catch (error) {
      console.error("Erro no login:", error);
    }
  }

  return (
    <Form onSubmit={(e) => {
        e.preventDefault();
        login(username, password);
        navigate("/clientes");
    }}>
      <h2> Login </h2>
      <Form.Group className="mb-3" controlId="username">
        <Form.Label>Username</Form.Label>
        <Form.Control
          type="text"
          placeholder="username"
          onChange={(e) => setUsername(e.target.value)}
        />
      </Form.Group>

      <Form.Group className="mb-3" controlId="password">
        <Form.Label>Password</Form.Label>
        <Form.Control
          type="text"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />
      </Form.Group>

      <Button className="btn btn-success" type="submit">Entrar</Button>
    </Form>
  );
}
