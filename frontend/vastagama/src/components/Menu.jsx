import { Nav, Navbar, Container, NavDropdown } from "react-bootstrap";
import { NavLink } from "react-router-dom";

export default function Menu() {
  return (
    <Navbar className="navbar navbar-expand-lg navbar-dark bg-dark">
      <Container>
        <Navbar.Brand href="#home">Vastagama</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={NavLink} to="/clientes">
              Clientes
            </Nav.Link>
          </Nav>

          <Nav>
            <NavDropdown align="end" title="Stephen" id="basic-nav-dropdown">
              <NavDropdown.Item href="#action/3.1">Perfil</NavDropdown.Item>
              <NavDropdown.Item href="#action/3.2">
                Configurações
              </NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#action/3.3">Sair</NavDropdown.Item>
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
