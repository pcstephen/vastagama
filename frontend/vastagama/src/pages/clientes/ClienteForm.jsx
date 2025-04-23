import React, {useEffect, useState} from "react";

const itemInicial = {
    descricao: "",
    quantidade: 1,
    valorUnitario: 0.0,
};

const telefoneInicial = {
  id:"",numero: ""
}

const clienteInicial = {
  id: "",
  codigoPublico:"",
  nome: "",
  endereco: {
    rua: "",
    bairro: "",
    complemento: "",
    cidade: "",
  },
  telefones: [{ id:"", numero: "" }],
  ordemDeServicos: [{
    observacoes: "",
    itens: [
        {
          descricao: "",
          quantidade: 1,
          valorUnitario: 0.0,
        }],
      }
    ],
};

export default function ClienteForm({ clienteSelecionado, adicionarCliente, atualizarCliente, cancelar }) {
  const [cliente, setCliente] = useState({...clienteInicial, ...clienteSelecionado,});
  const [itens, setItens] = useState();

  useEffect(() => {
    setCliente({...clienteInicial, ...clienteSelecionado});
    }, [clienteSelecionado]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const campos = name.split(".");

    console.log("name: ", name)
    console.log("campos: ", campos)

    setCliente((prevCliente) => {
      const novoCliente = structuredClone(prevCliente);

      let atual = novoCliente;
      for (let i = 0; i < campos.length - 1; i++) {
        const chave = isNaN(campos[i]) ? campos[i] : parseInt(campos[i]);
        atual = atual[chave];
      }

      const ultimaChave = campos[campos.length - 1];
      atual[ultimaChave] = value;

      return novoCliente;
    });
  };

    const handleSubmit = (e) => {
      e.preventDefault();
      cliente.id ? atualizarCliente(cliente) : adicionarCliente(cliente);
      setCliente(clienteInicial);
    };

    const adicionarItem = () => {

      setCliente((prevCliente)=>{
        let novaOrdemDeServicos = prevCliente.ordemDeServicos ?? [];
         novaOrdemDeServicos = prevCliente.ordemDeServicos.map((ordem , index) => {
          if(index === 0){
            return {
              ...ordem, itens: [...ordem.itens, {...itemInicial}]
            };
          }
          return ordem;
        })
        return{...prevCliente, ordemDeServicos: novaOrdemDeServicos}
      },[]);

    };

    const removerItem = (id) =>{
      setItens(itens.filter((item) => item.id =! id));
    }

    const adicionarTelefone = () => {
      setCliente ((prevCliente) => {
        const novaListaTelefones = [...prevCliente.telefones, telefoneInicial];

        return { ...prevCliente, telefones: novaListaTelefones}
      })
      console.log("Corpo do cliente com telefone adicionado: ", JSON.stringify(cliente, null,2));
    }


    return (
      <form className="row g-6 form-bg" onSubmit={handleSubmit}>
        <div className="col-md-12 mb-3">
          <label className="form-label mb-1"><strong>Nome Completo:</strong></label>
          <input className="form-control" name="nome" type="text" onChange={handleChange} value={cliente.nome} />
        </div>

        <hr/>
        <div className="d-flex justify-content-between align-itens-center mb-3">
          <h5>Telefones</h5>

          <button className="btn btn-primary" type="button" onClick={adicionarTelefone}>
            <i className="bi bi-plus-lg"></i>
          </button>
        </div>

        {Array.isArray(cliente?.telefones) && cliente.telefones.map((telefone, index) => (
            <div className="col-md-12 mt-1" key={index}>
              <label className="form-label mb-1"><strong>{`Telefone ${index+1}`}:</strong></label>
              <input className="form-control" name={`telefones.${index}.numero`} type="text"
                     onChange={(e) => {handleChange(e)}}
                     value={telefone.numero}
              />
            </div>
        ))}

        {/*<div className="col-md-6">*/}
        {/*  <label className="form-label">CPF/CNPJ:</label>*/}
        {/*  <input className="form-control" name="cpfcnpj" type="text" onChange={handleChangeInput} value={cliente.telefones[0].numero} />*/}
        {/*</div>*/}


          <h5>Endereço</h5>

          {["rua", "bairro", "complemento", "cidade"].map((campo) => (
              <div className="col-md-6 mt-1" key={campo}>
                <label className="form-label mb-1">{campo.charAt(0).toUpperCase() + campo.slice(1)}:</label>
                <input className="form-control" name={`endereco.${campo}`} type="text"
                       onChange={(e)=>{handleChange(e)}}
                       value={cliente.endereco.campo}/>
              </div>
          ))}

        <hr className="mt-3"/>
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
                <label className="mt-2">Descrição</label>
                <textarea
                    className="form-control"
                    name={`ordemDeServicos[0].itens[${index}].descricao`}
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
                      name={`ordemDeServicos[0].itens[${index}].quantidade`}
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
                      name={`ordemDeServicos[0].itens[${index}].valorUnitario`}
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
            <i className="bi bi-check-circle"></i> {((cliente.id !== null) && (cliente.id !== undefined) &&(cliente.id !== "")) ? "Concluir" : "Salvar"}
          </button>
          <button className="btn btn-outline-warning" type="button" onClick={cancelar}>
            <i className="bi bi-x-circle"></i> Cancelar
          </button>

        </div>
      </form>
    );
  }
