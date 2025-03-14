import { useEffect, useState } from "react";
import ClienteForm from "./ClienteForm";
import ClienteLista from "./ClienteLista";
import { Button, Modal, } from "react-bootstrap";
import TitlePage from "../../components/TitlePage";
import api  from '../../api/atividade';

const clienteInicial = {
  id: 0,
  nome: "",
  endereco: {
    rua: "",
    bairro: "",
    complemento: "",
    cidade: "",
  },
  telefone: {
    numero: "",
  },
};

export default function Cliente() {
  const [showClienteModal, setShowClienteModal] = useState(false);
  const [showConfirmModal, setShowConfirmModal] = useState(false);


  const [cliente, setCliente] = useState(clienteInicial);
  const [clientes, setClientes] = useState([]);
  const [clienteParaExcluir, setClienteParaExcluir] = useState(clienteInicial);

  const handleClienteModal = () => {
    setShowClienteModal(!showClienteModal);
    
    if (showClienteModal) {
      setCliente(clienteInicial);
    }
  };

  const handleConfirmModal = (id) => {
    const clienteSelecionado = clientes.find((c) => c.id === id) || clienteInicial;
    setClienteParaExcluir(clienteSelecionado);
    setShowConfirmModal(true);
  }

  const handleCloseConfirmModal = () => {
    setShowConfirmModal(false);
  };

  const pegaClientes = async () => {
    const response = await api.get("clientes");
    return response.data;
  };

  useEffect(() => {
    const getClientes = async () => {
      const todosClientes = await pegaClientes();
      if (todosClientes) setClientes(todosClientes);
    };
    getClientes();
  }, []);

  const adicionarCliente = async (cliente) => {
    const response = await api.post("/geral", cliente);
    setClientes([...clientes, response.data]);
    setCliente(clienteInicial);
    console.log(clienteInicial);
    handleClienteModal();
  };

  const deletarCliente = async (id) => {
    if (await api.delete(`clientes/${id}`)) {
      const clientesFiltrados = clientes.filter((cliente) => cliente.id !== id);
      setClientes([...clientesFiltrados]);
    }
    handleCloseConfirmModal();
  };

  function pegarCliente(id) {
    const clienteEncontrado = clientes.find((cliente) => cliente.id === id);
    setCliente(clienteEncontrado);

    handleClienteModal();
  }

  function cancelar() {
    setCliente(clienteInicial);
    handleClienteModal();
  }

  const atualizarCliente = async (cliente) => {
    try {
      const response = await api.patch(`/clientes/${cliente.id}`, {
        nome: cliente.nome,
      });

      setClientes(
        clientes.map((clienteSelecionado) =>
          clienteSelecionado.id === cliente.id
            ? { ...clienteSelecionado, nome: cliente.nome }
            : clienteSelecionado
        )
      );

      setCliente({
        id: 0,
        nome: "",
        endereco: {
          rua: "",
          bairro: "",
          complemento: "",
          cidade: "",
        },
        telefone: {
          numero: "",
        },
      });
      handleClienteModal();
    } catch (error) {
      console.log("erro ao tentar atualizar cliente: ", error);
    }
  };

  const novoCliente = () => {
    setCliente({
      id: 0,
      nome: "",
      endereco: {
        rua: "",
        bairro: "",
        complemento: "",
        cidade: "",
      },
      telefone: {
        numero: "",
      },
    });
    handleClienteModal();
  };

  return (
    <>
      <TitlePage
        cliente={cliente}
        novoCliente={novoCliente}
      />

      <ClienteLista
        clientes={clientes}
        pegarCliente={pegarCliente}
        handleConfirmModal={handleConfirmModal}
      />

      <Modal show={showClienteModal} onHide={handleClienteModal}>
        <Modal.Header closeButton>
          <Modal.Title>
            {cliente.id === 0 ? "Novo Cliente" : "Cliente" + cliente.id}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <ClienteForm
            clienteSelecionado={cliente}
            adicionarCliente={adicionarCliente}
            atualizarCliente={atualizarCliente}
            cancelar={cancelar}
            clientes={clientes}
          />
        </Modal.Body>
      </Modal>

      <Modal show={showConfirmModal} onHide={handleCloseConfirmModal}>
        <Modal.Header closeButton>
          <Modal.Title>
            <h5>Deseja excluir {"Cliente " + clienteParaExcluir.id + " - " + clienteParaExcluir.nome}? </h5>
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Essa ação não pode ser desfeita!
        </Modal.Body>
        <Modal.Footer>
        <Button className="btn btn-success"
          onClick={()=> deletarCliente(clienteParaExcluir.id)}
        >
        <i className="bi bi-check fs-5"></i>
            Sim
          </Button>
          <Button className="btn btn-danger"
            onClick={handleCloseConfirmModal}  
          >
          <i className="bi bi-x fs-5"></i>
            Não
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}
