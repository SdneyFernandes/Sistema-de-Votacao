import styled from 'styled-components'

export const HeaderWrapper = styled.header`
  width: 100%;
  background: ${({ theme }) => theme.colors.white};
  padding: 15px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
`

export const Logo = styled.h1`
  font-size: 20px;
  color: ${({ theme }) => theme.colors.primary};
`

export const LogoutButton = styled.button`
  background: ${({ theme }) => theme.colors.primary};
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  font-size: 14px;
  &:hover {
    background: #27ae60;
  }
`
