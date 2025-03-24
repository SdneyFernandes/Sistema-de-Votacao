import { SidebarWrapper, NavItem } from './styles'

const Sidebar = () => {
  return (
    <SidebarWrapper>
      <NavItem to="/">🏠 Início</NavItem>
      <NavItem to="/create-poll">📊 Criar Votação</NavItem>
      <NavItem to="/dashboard">📜 Minhas Votações</NavItem>
    </SidebarWrapper>
  )
}

export default Sidebar
