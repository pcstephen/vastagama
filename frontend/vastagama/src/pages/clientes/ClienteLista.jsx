import { useState } from "react";
import { Form, InputGroup } from "react-bootstrap";

export default function ClienteLista({ clientes, pegarCliente, handleConfirmModal }) {
  const [termoBusca, setTermoBusca] = useState("");

  const inputTextHandler = (e) => {
    setTermoBusca(e.target.value);
  };

  const clientesFiltrados = clientes.filter((cliente) =>
    Object.values(cliente).join(" ").toLowerCase().includes(termoBusca.toLowerCase())
  );

  function isBlank(str) {
    return !str || str.trim().length === 0;
  }

  return (
    <>
      <InputGroup className="mt-3 mb-3">
        <InputGroup.Text>Buscar</InputGroup.Text>
        <Form.Control
          placeholder="Buscar cliente por nome"
          onChange={inputTextHandler}
        />
      </InputGroup>

      <table className="table table-striped table-hover">
        <thead className="table-dark">
          <tr>
            <th>#</th>
            <th>Nome</th>
            <th>Endereço</th>
            <th>Telefone</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {clientesFiltrados.map((cliente) => (
            <tr key={cliente.id}>
              <td>{cliente.id}</td>
              <td>{cliente.nome}</td>
              <td>
                {cliente.endereco &&
                !isBlank(cliente.endereco.rua) &&
                !isBlank(cliente.endereco.bairro) &&
                !isBlank(cliente.endereco.cidade)
                  ? `${cliente.endereco.rua}, ${cliente.endereco.bairro}, ${cliente.endereco.cidade}`
                  : "Endereço não informado"}
              </td>
              <td>
                <button className="btn btn-sm btn-outline-primary me-2" onClick={() => pegarCliente(cliente.id)}>
                  <i className="bi bi-pencil"></i> Editar
                </button>
                <button className="btn btn-sm btn-outline-danger me-2" onClick={() => handleConfirmModal(cliente.id)}>
                  <i className="bi bi-trash"></i> Excluir
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
