import { useEffect, useState, useCallback } from "react";
import { Button, Modal } from "react-bootstrap";
import ClienteForm from "./ClienteForm";
import ClienteLista from "./ClienteLista";
import TitlePage from "../../components/TitlePage";
import api from "../../api/atividade";
import { toast } from 'react-toastify';

const clienteInicial = {
  id: "",
  nome: "",
  codigoPublico:"",
  endereco: {
    rua: "",
    bairro: "",
    complemento: "",
    cidade: "",
  },
  telefones: [
    {
      numero: "",
    },
  ],
  ordemDeServicos: [
    {
      observacoes: "",
      itens: [
        {
          descricao: "",
          quantidade: 1,
          valorUnitario: 0.0,
        }
      ],
    },
  ],
};

export default function Cliente() {
  const [showClienteModal, setShowClienteModal] = useState(false);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [cliente, setCliente] = useState(clienteInicial);
  const [clientes, setClientes] = useState([]);
  const [clienteParaExcluir, setClienteParaExcluir] = useState(clienteInicial);
  const [modoEdicaoSimples, setModoEdicaoSimples] = useState(false);

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
    carregarClientes().catch((erro) => {
      console.error("Erro ao carregar clientes:", erro);
    });
  }, [carregarClientes]);


  const adicionarCliente = async (novoCliente) => {
    try {
      const response = await api.post("/geral", novoCliente);
      setClientes((prevClientes) => [...prevClientes, response.data]);
      handleClienteModal();

      console.log("\nFunção AdicionaCliente chamada!\n");
      if(response.status === 201){
        toast.success("Cliente cadastrado com sucesso!");
      }
      await carregarClientes();
    } catch (error) {
      const errorMessage = error.response?.data?.erro;
      toast.error("Erro ao cadastrar cliente!",errorMessage);      
    }
  };

  const atualizarCliente = async (codigoPublico ,dadosAtualizados) => {
    try {
      await api.patch(`/geral/${codigoPublico}`, dadosAtualizados);

      setClientes((prevClientes) =>
        prevClientes.map((c) => (c.codigoPublico === codigoPublico ? { ...c, ...dadosAtualizados } : c))
      );
      toast.success("Cliente editado com sucesso!");
      handleClienteModal();
      carregarClientes();
    } catch (error) {
      console.log("JSON enviado para requisição: ", dadosAtualizados);
      toast.error("Erro ao atualizar cliente!\n ", error);
      console.error(error.request.response.error);
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

  const editarCliente = (codigoPublico, edicaoSimples = true) => {
    const clienteEncontrado = clientes.find((c) => c.codigoPublico === codigoPublico);
    if (clienteEncontrado) {
      setCliente(clienteEncontrado);
      setModoEdicaoSimples(edicaoSimples);
      handleClienteModal();
    }
  };

  // Criar novo cliente
  const novoCliente = () => {
    setCliente(clienteInicial);
    setModoEdicaoSimples(false);
    handleClienteModal();
  };

  return (
    <>
      <TitlePage title="Clientes" novoCliente={novoCliente} />

      <ClienteLista clientes={clientes} pegarCliente={(codigoPublico) => editarCliente(codigoPublico, true)} handleConfirmModal={abrirConfirmacaoExclusao} />

      <Modal show={showClienteModal} onHide={handleClienteModal}>
        <Modal.Header closeButton>
          <Modal.Title>{cliente.id === "" ? "Novo Cliente" : `Editar Cliente ${cliente.id}`}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <ClienteForm
            clienteSelecionado={cliente}
            adicionarCliente={adicionarCliente}
            atualizarCliente={atualizarCliente}
            cancelar={handleClienteModal}
            modoEdicaoSimples={modoEdicaoSimples}
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
