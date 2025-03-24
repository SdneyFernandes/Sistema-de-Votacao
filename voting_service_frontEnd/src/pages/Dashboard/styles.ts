import styled from 'styled-components'

export const DashboardContainer = styled.div`
  max-width: 800px;
  margin: 2rem auto;
  padding: 2rem;
  background: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
`

export const Title = styled.h2`
  text-align: center;
  margin-bottom: 1.5rem;
`

export const VoteList = styled.ul`
  list-style: none;
  padding: 0;
`

export const VoteItem = styled.li`
  background: #f9f9f9;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`

export const Button = styled.button`
  background: #4f46e5;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background: #3730a3;
  }
`

export const AdminButton = styled(Button)`
  background: #d32f2f;

  &:hover {
    background: #b71c1c;
  }
`
