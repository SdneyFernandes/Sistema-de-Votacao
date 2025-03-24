import styled from 'styled-components'
import { Link } from 'react-router-dom'

export const SidebarWrapper = styled.aside`
  width: 250px;
  height: 100vh;
  background: ${({ theme }) => theme.colors.secondary};
  display: flex;
  flex-direction: column;
  padding: 20px;
  color: white;
`

export const NavItem = styled(Link)`
  padding: 15px 10px;
  color: white;
  text-decoration: none;
  font-size: 18px;
  margin-bottom: 10px;
  &:hover {
    background: rgba(255, 255, 255, 0.1);
  }
`
