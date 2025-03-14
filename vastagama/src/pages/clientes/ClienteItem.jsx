import React from "react";

export default function ClienteItem(props) {
  return (
    <div className="card mb-2 shadow-sm">
      <div className="card-body">
        <div className="d-flex justify-content-between">
          <h5 className="card-title">
            <span className="bagde text-bg-secondary me-1">
              {props.cliente.id}
            </span>
            - {props.cliente.nome}
          </h5>
        </div>
        <div>
          <p className="card-text">
            {"Telefone: " + props.cliente.telefone.numero}
          </p>
        </div>
        <p />
        <div>
          <p className="card-text">
            {"Endereço: " +
              props.cliente.endereco.rua +
              ", " +
              props.cliente.endereco.bairro +
              ", " +
              props.cliente.endereco.complemento +
              ", " +
              props.cliente.endereco.cidade}
          </p>
        </div>

        <div className="d-flex justify-content-end pt-2 m-0 border-top">
          <button
            className="btn btn-outline-primary me-2"
            onClick={() => props.pegarCliente(props.cliente.id)}
          >
            <i className="fas fa-pen me-2"></i>
            Editar
          </button>

          <button
            className="btn btn-outline-danger me-2"
            onClick={() => props.handleConfirmModal(props.cliente.id)}
          >
            <i className="fas fa-trash me-2"></i>
            Excluir
          </button>
        </div>
      </div>
    </div>
  );
}
