import React, { useEffect, useState } from "react";

  const itemInicial = {
    id:0,
    descricao: "",
    quantidade: 1,
    valorUnitario: 0.0,
  };

  const clienteInicial = {
    id: 0,
    nome: "",
    endereco: {
      rua: "",
      bairro: "",
      complemento: "",
      cidade: "",
    },
    telefones: [{ id:0, numero: "" }],
    ordemDeServicos: [
      {
        id: 0,
        observacoes: "",
        itens: [
          {
            id:0,
            descricao: "",
            quantidade: 1,
            valorUnitario: 0.0,
          }
        ],
      }
    ],
  };

  export default function ClienteForm({ clienteSelecionado, adicionarCliente, atualizarCliente, cancelar }) {
    const [cliente, setCliente] = useState(clienteSelecionado || clienteInicial);
    const [itens, setItens] = useState();

    useEffect(() => {
      setCliente(clienteSelecionado || clienteInicial);
    }, [clienteSelecionado]);

    const handleChangeInput = (e) => {
      const { name, value } = e.target;

      if (name.includes('.')) {
        const [parent, child] = name.split('.');
        setCliente({ ...cliente, [parent]: { ...cliente[parent], [child]: value } });
      } else {
        setCliente({ ...cliente, [name]: value });
      }
    };

    const handleSubmit = (e) => {
      e.preventDefault();
      cliente.id !== 0 ? atualizarCliente(cliente) : adicionarCliente(cliente);
      setCliente(clienteInicial);
    };

    const adicionarItem = () => {
      console.log("Antes: " + clienteInicial.ordemDeServicos[0].itens.length)

      setCliente((prevCliente)=>{
        const novaOrdemDeServicos = prevCliente.ordemDeServicos.map((ordem , index) => {
          if(index === 0){
            return {
              ...ordem, itens: [...ordem.itens, {...itemInicial}]
            };
          }
          return ordem;
        })
        console.log("Depois: " + novaOrdemDeServicos.length)
        console.log("Depois itens: " + novaOrdemDeServicos[0].itens.length)
        return{...prevCliente, ordemDeServicos: novaOrdemDeServicos}
      },[]);

    };

    const removerItem = (id) =>{
      setItens(itens.filter((item) => item.id =! id));
    }


    return (
      <form className="row g-3" onSubmit={handleSubmit}>
        <div className="col-md-12">
          <label className="form-label">Nome Completo:</label>
          <input className="form-control" name="nome" type="text" onChange={handleChangeInput} value={cliente.nome} />
        </div>

        <div className="col-md-6">
          <label className="form-label">Telefone:</label>
          <input className="form-control" name="telefone.numero" type="text" onChange={handleChangeInput} value={cliente.telefones[0].numero} />
        </div>

        {/*<div className="col-md-6">*/}
        {/*  <label className="form-label">CPF/CNPJ:</label>*/}
        {/*  <input className="form-control" name="cpfcnpj" type="text" onChange={handleChangeInput} value={cliente.telefones[0].numero} />*/}
        {/*</div>*/}

        {["rua", "bairro", "complemento", "cidade"].map((campo) => (
          <div className="col-md-6" key={campo}>
            <label className="form-label">{campo.charAt(0).toUpperCase() + campo.slice(1)}:</label>
            <input className="form-control" name={`endereco.${campo}`} type="text" onChange={handleChangeInput} value={cliente.endereco[campo]} />
          </div>
        ))}

        <hr/>
        <div className="d-flex justify-content-between align-itens-center mb-3">

          <h5>Ordem de Serviço</h5>
          <button className="btn btn-primary" type="button" onClick={adicionarItem}>
            <i className="bi bi-plus-lg"></i> Adicionar item
          </button>

        </div>

        {cliente.ordemDeServicos[0].itens.map((item, index) => (
            <div key={index} className="border rounded p-3 mb-3">
              <div><strong>Item {index + 1}</strong></div>

              <div className="mb-3">
                <label className="form-label">Descrição</label>
                <textarea
                    className="form-control"
                    value={item.descricao}
                    onChange={(e) => {
                      const novaLista = [...cliente.ordemDeServicos];
                      novaLista[0].itens[index].descricao = e.target.value;
                      setCliente({ ...cliente, ordemDeServicos: novaLista });
                    }}
                />
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label>Quantidade</label>
                  <input
                      type="number"
                      className="form-control"
                      value={item.quantidade}
                      onChange={(e) => {
                        const novaLista = [...cliente.ordemDeServicos];
                        novaLista[0].itens[index].quantidade = parseInt(e.target.value);
                        setCliente({ ...cliente, ordemDeServicos: novaLista });
                      }}
                  />
                </div>

                <div className="col-md-6 mb-3">
                  <label> Valor Unitário</label>
                  <input
                      type="number"
                      className="form-control"
                      value={item.valorUnitario}
                      onChange={(e) => {
                        const novaLista = [...cliente.ordemDeServicos];
                        novaLista[0].itens[index].valorUnitario = parseFloat(e.target.value);
                        setCliente({ ...cliente, ordemDeServicos: novaLista });
                      }}
                  />
                </div>
              </div>
            </div>
        ))}

        <div className="d-flex justify-content-end col-12">
          <button className="btn btn-outline-success me-2" type="submit">
            <i className="bi bi-check-circle"></i> Salvar
          </button>
          <button className="btn btn-outline-warning" type="button" onClick={cancelar}>
            <i className="bi bi-x-circle"></i> Cancelar
          </button>

        </div>
      </form>
    );
  }
