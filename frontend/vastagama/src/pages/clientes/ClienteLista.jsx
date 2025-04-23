import React, { useState } from "react";
import { Form, InputGroup } from "react-bootstrap";
import{useNavigate} from "react-router-dom";

export default function ClienteLista({ clientes, pegarCliente, handleConfirmModal }) {
  const [termoBusca, setTermoBusca] = useState("");

  const navigate = useNavigate();

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
          <InputGroup.Text>
            <i className="bi bi-search"></i>
          </InputGroup.Text>
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
            {clientesFiltrados.map((cliente, index) => (
              <tr key={cliente.id}>
                <td>{index + 1}</td>
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
                  {cliente.telefones.length > 0 ? cliente.telefones.map((telefone, index) => (
                      <span key={telefone.id}>
                        {telefone.numero} {index < cliente.telefones.length - 1 ? " / " : ""}
                      </span>
                  )) : "Telefone não informado!"
                  }
                </td>
                <td>
                  <button className="btn btn-sm btn-outline-primary me-2" onClick={() => pegarCliente(cliente.id)}>
                    <i className="bi bi-pencil"></i> Editar
                  </button>
                  <button className="btn btn-sm btn-outline-danger me-2" onClick={() => handleConfirmModal(cliente.id)}>
                    <i className="bi bi-trash"></i> Excluir
                  </button>
                  <button className="btn btn-sm btn-success me-2" onClick={ () => navigate(`${cliente.codigoPublico}`)}>
                    <i className="bi bi-eye"></i> Ver Detalhes
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

      </>
  );
}
