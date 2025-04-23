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
          <Stack direction="horizontal" gap="md-3">
            <h2 className="p-2">{cliente.nome}</h2>
            <div className="p-2 ms-auto"><Button></Button></div>
          </Stack>
        </CardTitle>
        <Card.Body>
          <p><strong>Endereço:</strong> {cliente.endereco?.rua}, {cliente.endereco?.bairro}, {cliente.endereco?.cidade} </p>
          <p><strong>Telefones:</strong></p>
        <ul>
          {cliente.telefones?.map(t => <li key={t.id}>{t.numero}</li>)}
        </ul>

        <p><strong>Ordens de Serviço:</strong></p>
        
        <ul>

          {cliente.ordemDeServicos?.map((ordem, idx) => (
            <div key={ordem.id} className="mb-4">
              <li><h5>Ordem {idx + 1} - {ordem.dataCadastro}</h5></li>
              <Table striped bordered hover size="sm">
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Item</th>
                    <th>Quantidade</th>
                    <th>Preço Unitário</th>
                    <th>Total</th>
                  </tr>
                </thead>
                <tbody>
                  {ordem.itens?.map((item, i) => (
                    <tr key={item.id}>
                      <td>{i + 1}</td>
                      <td>{item.descricao}</td>
                      <td>{item.quantidade}</td>
                      <td>R$ {item.valorUnitario.toFixed(2)}</td>
                      <td>{""}</td>
                    </tr>
                    
                  ))}
                </tbody>

                <tfoot>
                <tr>
                  <td colSpan="4" className="text-start"><strong>Total</strong></td>
                  <td><strong>R$ {ordem.total.toFixed(2)}</strong></td>
                </tr>
                </tfoot>

              </Table>
            </div>
          ))}
        </ul>

        </Card.Body>  
      </Card>
    
    </>
  );
}