import React, { useState, useEffect } from "react";
import api from "../../api/atividade"; 
import { useParams } from "react-router-dom";
import Spinner from "react-bootstrap/Spinner";
import { Button, Card, CardTitle, Row, Stack, Table } from "react-bootstrap";

export default function ClienteDetalhes() {
  const { codPublico } = useParams();
  const [ cliente, setCliente ] = useState();
  const [carregando, setCarregando] = useState(true);


  const carregarCliente = async (codigoPublico) =>{
    console.log("Entrou");
    try {
      console.log("Está no try");
      const response = await api.get(`clientes/${codigoPublico}`);
      console.log(response.data);
      setCliente(response.data);
    } catch (error) {
      console.error("Erro ao carregar cliente: " , error);
    } finally{
      setCarregando(false);
    }
  }

  useEffect(() => {
    carregarCliente(codPublico);

  }, [codPublico]);
  
  if (carregando) {
    return (
      <div className="text-center mt-4">
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Carregando...</span>
        </Spinner>
      </div>
    );
  }
  
  return (
    <>
      
      <Card className="mt-4 px-3 py-3 shadow-sm">
        <CardTitle>
          <Stack direction="horizontal" className="align-items-center">
            <h2 className="mb-0">{cliente.nome}</h2>
            <div className="ms-auto d-flex gap-2">
              <Button variant="outline-primary" size="sm" >
                <i className="bi bi-pencil-square"></i> Editar Cliente
              </Button>
              <Button variant="primary" size="sm" >
                <i className="bi bi-plus-circle"></i> Novo Serviço
              </Button>
            </div>
          </Stack>
        </CardTitle>
        <Card.Body>
        <hr />
        <p><strong>Endereço:</strong> {cliente.endereco?.rua}, {cliente.endereco?.bairro}, {cliente.endereco?.cidade}</p>

        <p><strong>Telefones:</strong></p>
        <ul>
          {cliente.telefones?.map(t => <li key={t.id}>{t.numero}</li>)}
        </ul>

        <h4 className="mt-4">Ordens de Serviço</h4>

        {cliente.ordemDeServicos?.map((ordem, idx) => (
          <Card className="mb-4" key={ordem.id}>
            <Card.Body>
              <Stack direction="horizontal" className="mb-3">
                <h5 className="mb-0">Ordem {idx + 1} - {ordem.dataCadastro}</h5>
                <Button size="sm" variant="outline-secondary" className="ms-auto" >
                  <i className="bi bi-pencil"></i> Editar Ordem
                </Button>
              </Stack>

              <Table striped bordered hover size="sm">
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Item</th>
                    <th>Quantidade</th>
                    <th>Preço Unitário</th>
                  </tr>
                </thead>
                <tbody>
                  {ordem.itens?.map((item, i) => (
                    <tr key={item.id}>
                      <td>{i + 1}</td>
                      <td>{item.descricao}</td>
                      <td>{item.quantidade}</td>
                      <td>R$ {item.valorUnitario.toFixed(2)}</td>
                    </tr>
                  ))}
                </tbody>
                <tfoot>
                  <tr>
                    <td colSpan="3" className="text-end"><strong>Total</strong></td>
                    <td><strong>R$ {ordem.total.toFixed(2)}</strong></td>
                  </tr>
                </tfoot>
              </Table>
            </Card.Body>
          </Card>
        ))}

      </Card.Body>  
    </Card>
    
    </>
  );
}