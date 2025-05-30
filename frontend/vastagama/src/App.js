import "./App.css";
import Cliente from "./pages/clientes/Cliente";
import { Routes, Route, Router, } from 'react-router-dom';
import ClienteDetalhes from "./pages/clientes/ClienteDetalhes";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Login from "./components/login/Login";

export default function App() {
  return (
    <>
      <Routes>
      <Route path="/clientes" element={<Cliente/>}> </Route>
      <Route path="clientes/:codPublico" element={<ClienteDetalhes/>}> </Route>
      </Routes>
        <ToastContainer position="top-right" autoclose={5000}/>
    </>
  );
}
