import { useState } from "react";
import ClienteItem from "./ClienteItem";
import { Form, InputGroup } from "react-bootstrap";

// export default function ClienteLista(props) {
//   return (
//     <div className="mt-3">
//         {props.clientes.map((cliente) => (
//             <ClienteItem
//                 key={cliente.id}
//                 cliente={cliente}
//                 pegarCliente={props.pegarCliente}
//                 handleConfirmModal={props.handleConfirmModal}
//             />
//         ))}
//     </div>
//   )
// }
const clientes = [
  {
    id: 1,
    nome: "Cliente 1",
    endereco: "Rua dos Pássaros",
    telefone: "62911111111",
  },
  {
    id: 2,
    nome: "Cliente 2",
    endereco: "Rua do Bosque",
    telefone: "62922222222",
  },
  {
    id: 3,
    nome: "Cliente 3",
    endereco: "Rua das Flores",
    telefone: "62933333333",
  },
];

export default function ClienteLista() {
  const [termoBusca, setTermoBusca] = useState('');

  const inputTextHandler = (e) =>{
    setTermoBusca(e.target.value);
  }

  const clientesFiltrados = clientes.filter((cliente) => {
    return cliente.nome.toLocaleLowerCase().indexOf(termoBusca) !== -1 ||
    cliente.nome.toLocaleUpperCase().indexOf(termoBusca) !== -1 ||
    cliente.endereco.toLocaleLowerCase().indexOf(termoBusca) !== -1 ||
    cliente.telefone.toLocaleLowerCase().indexOf(termoBusca) !== -1;
  });

  return (
    <>
      <InputGroup className="mt-3 mb-3">
        <InputGroup.Text>
          Buscar
        </InputGroup.Text>
        <Form.Control
          placeholder="Buscar cliente por nome"
          onChange={inputTextHandler}
        />
      </InputGroup>

      <table className="table table-striped table-hover">
        <thead className="table-dark">
          <tr>
            <th scope="col">#</th>
            <th scope="col">Nome</th>
            <th scope="col">Endereco</th>
            <th scope="col">Telefone</th>
            <th scope="col">Opções</th>
          </tr>
        </thead>
        <tbody>
          {clientesFiltrados.map((cliente) => (
            <tr key={cliente.id}>
              <td>{cliente.id}</td>
              <td>{cliente.nome}</td>
              <td>{cliente.endereco}</td>
              <td>{cliente.telefone}</td>
              <td>
                <div>
                  <button className="btn btn-sm btn-outline-primary me-2">
                    <i className="bi bi-person-check me-2"></i>
                    Editar
                  </button>
                  <button className="btn btn-sm btn-outline-danger me-2">
                    <i className="bi bi-person-x me-2"></i>
                    Excluir
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
