import React from "react";
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';

export default function ClienteItem({ cliente, pegarCliente, handleConfirmModal }) {
  return (
      <div className="card mb-2 shadow-sm">
        <div className="card-body">
          <div className="d-flex justify-content-between">
            <h5 className="card-title">
              <span className="badge bg-secondary me-1">{cliente.id}</span> {cliente.nome}
            </h5>
          </div>
          <p className="card-text">Telefone: {cliente.telefones[0].numero || "Não informado"}</p>
          <p className="card-text">
            Endereço: {cliente.endereco ? `${cliente.endereco.rua}, ${cliente.endereco.bairro}, ${cliente.endereco.complemento}, ${cliente.endereco.cidade}` : "Não informado"}
          </p>
          <div className="d-flex justify-content-end pt-2 border-top">
            <button className="btn btn-outline-primary me-2" onClick={() => pegarCliente(cliente.id)}>
              <i className="bi bi-pencil"></i> Editar
            </button>
            <button className="btn btn-outline-danger me-2" onClick={() => handleConfirmModal(cliente.id)}>
              <i className="bi bi-trash"></i> Excluir
            </button>
            <DropdownButton id="dropdown-basic-button" title="...">
              <Dropdown.Item href="#/action-1">Ação 1</Dropdown.Item>
              <Dropdown.Item href="#/action-2">Ação 2</Dropdown.Item>
              <Dropdown.Item href="#/action-3">Ação 3</Dropdown.Item>
            </DropdownButton>
          </div>
        </div>
      </div>
  );
}
