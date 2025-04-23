import "./App.css";
import Cliente from "./pages/clientes/Cliente";
import { Routes, Route, } from 'react-router-dom';
import ClienteDetalhes from "./pages/clientes/ClienteDetalhes";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Home = () => (
  <div>
    <h1>Home</h1>
  </div>
);

export default function App() {
  return (
    <>
      <Routes>
      <Route path="/clientes" element={<Cliente/>}> </Route>
      <Route path="/home" element={<Home/>}> </Route>
      <Route path="clientes/:codPublico" element={<ClienteDetalhes/>}> </Route>
      </Routes>
        <ToastContainer position="top-right" autoclose={5000}/>
    </>
  );
}

