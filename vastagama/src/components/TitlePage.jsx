import React from "react";

import { Button } from "react-bootstrap";

export default function TitlePage(props) {
  return (
    <div className="d-flex justify-content-between align-items-end mt-2 pb-3 border-bottom border-1">
      <h1 className="m-0 p-0">
        {props.cliente.id === 0 ? "Novo Cliente" : "Cliente" + props.cliente.id}
      </h1>
      <Button variant="outline-secondary" onClick={props.novoCliente}>
        <i className="bi bi-plus fs-4" />
      </Button>
    </div>
  );
}
