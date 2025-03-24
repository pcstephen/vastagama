import "./App.css";
import Cliente from "./pages/clientes/Cliente";
import { Routes, Route, } from 'react-router-dom';
import ClienteDetalhes from "./pages/clientes/ClienteDetalhes";

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
      <Route path="/clientes/detalhe" element={<ClienteDetalhes/>}> </Route>
      </Routes>
    </>
  );
}

