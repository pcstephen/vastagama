import "./App.css";
import Cliente from "./pages/clientes/Cliente";
import { Routes, Route, } from 'react-router-dom';

export default function App() {
  return (
    <>
      <Routes>
      <Route path="/clientes" element={<Cliente/>}> </Route>
      <Route path="/home" element={<Home/>}> </Route>
      <Route path="/pagcliente" element={<PagCliente/>}> </Route>
      </Routes>

    </>
  );
}

const PagCliente = () => (
  <div>
    <h1>Cliente</h1>
  </div>
);

const Home = () => (
  <div>
    <h1>Home</h1>
  </div>
);
