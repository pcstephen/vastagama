import React from "react";

export default function ClienteItem({ cliente, pegarCliente, handleConfirmModal }) {
  return (
    <div className="card mb-2 shadow-sm">
      <div className="card-body">
        <div className="d-flex justify-content-between">
          <h5 className="card-title">
            <span className="badge bg-secondary me-1">{cliente.id}</span> {cliente.nome}
          </h5>
        </div>
        <p className="card-text">Telefone: {cliente.telefone?.numero || "Não informado"}</p>
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
        </div>
      </div>
    </div>
  );
}
