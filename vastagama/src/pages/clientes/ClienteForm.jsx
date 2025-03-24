import React, { useEffect, useState } from "react";

const clienteInicial = {
  id: 0,
  nome: '',
  endereco: { rua: '', bairro: '', complemento: '', cidade: '' },
  telefone: { numero: '' },
};

export default function ClienteForm({ clienteSelecionado, adicionarCliente, atualizarCliente, cancelar }) {
  const [cliente, setCliente] = useState(clienteSelecionado || clienteInicial);

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

  return (
    <form className="row g-3" onSubmit={handleSubmit}>
      <div className="col-md-12">
        <label className="form-label">Nome Completo:</label>
        <input className="form-control" name="nome" type="text" onChange={handleChangeInput} value={cliente.nome} />
      </div>

      <div className="col-md-6">
        <label className="form-label">Telefone:</label>
        <input className="form-control" name="telefone.numero" type="text" onChange={handleChangeInput} value={cliente.telefone.numero} />
      </div>

      {["rua", "bairro", "complemento", "cidade"].map((campo) => (
        <div className="col-md-6" key={campo}>
          <label className="form-label">{campo.charAt(0).toUpperCase() + campo.slice(1)}:</label>
          <input className="form-control" name={`endereco.${campo}`} type="text" onChange={handleChangeInput} value={cliente.endereco[campo]} />
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
