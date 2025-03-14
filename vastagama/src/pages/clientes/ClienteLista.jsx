import React from "react"
import ClienteItem from "./ClienteItem"
export default function ClienteLista(props) {
  return (
    <div className="mt-3">
        {props.clientes.map((cliente) => (
            <ClienteItem
                key={cliente.id}
                cliente={cliente}
                pegarCliente={props.pegarCliente}
                handleConfirmModal={props.handleConfirmModal}
            />
        ))}
    </div>
  )
}
