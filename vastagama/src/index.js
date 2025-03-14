import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import "bootswatch/dist/cosmo/bootstrap.min.css";
import Menu from './components/Menu';
import { BrowserRouter as Router } from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <Router>
    <Menu></Menu>
      <div className="container">
        <App />
      </div>
    </Router>
  </React.StrictMode>
);
