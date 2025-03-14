import React, { useEffect, useState } from "react";

const clienteInicial = {
  id: 0,
  nome: '',
  endereco: {
    rua: '',
    bairro: '',
    complemento: '',
    cidade: '',
  },
  telefone: {
    numero: '',
  },
};

export default function ClienteForm(props) {
  const [cliente, setCliente] = useState(clienteAtual());

  const handleChangeInput = (e) => {
    const { name, value } = e.target;

    if(name.includes('.')){
      const [parent, child] = name.split('.');
        setCliente({...cliente,
          [parent]: {
            ...cliente[parent],
            [child]:value
          }
        });
    } else{
      setCliente({...cliente, [name]:value})
    }
  }

  useEffect(() => {
    if (props.clienteSelecionado.id !== 0) setCliente(props.clienteSelecionado);
    else setCliente(clienteInicial);
  }, [props.clienteSelecionado]);

  function clienteAtual() {
    if (
      props.clienteSelecionado.id !== 0 &&
      props.clienteSelecionado.id !== undefined
    ) {
      return props.clienteSelecionado;
    } else {
      return clienteInicial;
    }
  }

  const handleCancelar = (e) => {
    e.preventDefault();
    props.cancelar();

    setCliente(clienteAtual());
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (props.clienteSelecionado.id !== 0) {
      props.atualizarCliente(cliente);
    } else {
      props.adicionarCliente(cliente);
    }
    setCliente({
      ...clienteInicial,
      endereco: { ...clienteInicial.endereco },
      telefone: { ...clienteInicial.telefone },
    });
  };

  return (
    <>
      <form className="row g-3">
        <div className="col-md-12">
          <label className="form-label">Nome Completo:</label>
          <input
            className="form-control"
            key=""
            name="nome"
            id="nome"
            type="text"
            onChange={handleChangeInput}
            value={cliente.nome}
          />
        </div>

        <div className="col-md-6">
          <label className="form-label">Telefone:</label>
          <input
            className="form-control"
            key="telefone.numero"
            name="telefone.numero"
            id="telefone"
            type="text"
            onChange={handleChangeInput}
            value={cliente.telefone.numero}
          />
        </div>

        <div className="col-md-6">
          <label className="form-label">Rua:</label>
          <input
            className="form-control"
            key="endereco.rua"
            name="endereco.rua"
            id="rua"
            type="text"
            onChange={handleChangeInput}
            value={cliente.endereco.rua}
          />
        </div>
        <div className="col-md-6">
          <label className="form-label">Bairro:</label>
          <input
            className="form-control"
            key="endereco.bairro"
            name="endereco.bairro"
            id="bairro"
            type="text"
            onChange={handleChangeInput}
            value={cliente.endereco.bairro}
          />
        </div>
        <div className="col-md-6">
          <label className="form-label">Complemento:</label>
          <input
            className="form-control"
            key="endereco.complemento"
            name="endereco.complemento"
            id="complemento"
            type="text"
            onChange={handleChangeInput}
            value={cliente.endereco.complemento}
          />
        </div>
        <div className="col-md-6">
          <label className="form-label">Cidade:</label>
          <input
            className="form-control"
            key="endereco.cidade"
            name="endereco.cidade"
            id="cidade"
            type="text"
            onChange={handleChangeInput}
            value={cliente.endereco.cidade}
          />
        </div>
        <hr />

        <div className="d-flex justify-content-end col-12">
          {cliente.id === 0 ? (
            <button
              className=" btn btn-outline-success"
              type="submit"
              onClick={handleSubmit}
            >
              <i className="bi bi-plus "></i> Salvar
            </button>
          ) : (
            <div className="d-flex justify-content-end">
              <button
                className="btn btn-outline-success me-1"
                type="submit"
                onClick={handleSubmit}
              >
                Salvar
              </button>

              <button
                className="btn btn-outline-warning"
                onClick={handleCancelar}
              >
              Cancelar
              </button>
            </div>
          )}
        </div>
      </form>
    </>
  );
}
