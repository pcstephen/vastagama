import { useEffect, useState, useCallback } from "react";
import { Button, Modal } from "react-bootstrap";
import ClienteForm from "./ClienteForm";
import ClienteLista from "./ClienteLista";
import TitlePage from "../../components/TitlePage";
import api from "../../api/atividade";

const clienteInicial = {
  id: 0,
  nome: "",
  endereco: { rua: "", bairro: "", complemento: "", cidade: "" },
  telefone: { numero: "" },
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

  // Exibe o modal de confirmação para deletar um cliente
  const abrirConfirmacaoExclusao = useCallback(
    (id) => {
      const clienteSelecionado = clientes.find((c) => c.id === id) || clienteInicial;
      setClienteParaExcluir(clienteSelecionado);
      setShowConfirmModal(true);
    },
    [clientes]
  );

  // Fecha o modal de confirmação
  const fecharConfirmacaoExclusao = () => setShowConfirmModal(false);

  // Busca clientes na API
  const carregarClientes = useCallback(async () => {
    try {
      const response = await api.get("clientes");
      setClientes(response.data);
    } catch (error) {
      console.error("Erro ao buscar clientes:", error);
    }
  }, []);

  useEffect(() => {
    carregarClientes();
  }, [carregarClientes]);


  const adicionarCliente = async (novoCliente) => {
    try {
      const response = await api.post("/geral", novoCliente);
      setClientes((prevClientes) => [...prevClientes, response.data]);
      handleClienteModal();
    } catch (error) {
      console.error("Erro ao adicionar cliente:", error);
    }
  };

  const atualizarCliente = async (clienteAtualizado) => {
    try {
      await api.patch(`/clientes/${clienteAtualizado.id}`, { nome: clienteAtualizado.nome });

      setClientes((prevClientes) =>
        prevClientes.map((c) => (c.id === clienteAtualizado.id ? { ...c, nome: clienteAtualizado.nome } : c))
      );

      handleClienteModal();
    } catch (error) {
      console.error("Erro ao atualizar cliente:", error);
    }
  };

  const deletarCliente = async (id) => {
    try {
      await api.delete(`clientes/${id}`);
      setClientes((prevClientes) => prevClientes.filter((cliente) => cliente.id !== id));
      fecharConfirmacaoExclusao();
    } catch (error) {
      console.error("Erro ao deletar cliente:", error);
    }
  };

  const editarCliente = (id) => {
    const clienteEncontrado = clientes.find((c) => c.id === id);
    if (clienteEncontrado) {
      setCliente(clienteEncontrado);
      handleClienteModal();
    }
  };

  // Criar novo cliente
  const novoCliente = () => {
    setCliente(clienteInicial);
    handleClienteModal();
  };

  return (
    <>
      <TitlePage title="Clientes" novoCliente={novoCliente} />

      <ClienteLista clientes={clientes} pegarCliente={editarCliente} handleConfirmModal={abrirConfirmacaoExclusao} />

      <Modal show={showClienteModal} onHide={handleClienteModal}>
        <Modal.Header closeButton>
          <Modal.Title>{cliente.id === 0 ? "Novo Cliente" : `Editar Cliente ${cliente.id}`}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <ClienteForm
            clienteSelecionado={cliente}
            adicionarCliente={adicionarCliente}
            atualizarCliente={atualizarCliente}
            cancelar={handleClienteModal}
          />
        </Modal.Body>
      </Modal>

      <Modal show={showConfirmModal} onHide={fecharConfirmacaoExclusao}>
        <Modal.Header closeButton>
          <Modal.Title>
            Deseja excluir Cliente {clienteParaExcluir.id} - {clienteParaExcluir.nome}?
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>Essa ação não pode ser desfeita!</Modal.Body>
        <Modal.Footer>
          <Button variant="success" onClick={() => deletarCliente(clienteParaExcluir.id)}>
            <i className="bi bi-check fs-5"></i> Sim
          </Button>
          <Button variant="danger" onClick={fecharConfirmacaoExclusao}>
            <i className="bi bi-x fs-5"></i> Não
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}
