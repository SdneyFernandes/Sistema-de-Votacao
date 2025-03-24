import styled from 'styled-components'

export const VoteContainer = styled.div`
  max-width: 600px;
  margin: 2rem auto;
  padding: 2rem;
  background: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
`

export const Title = styled.h2`
  text-align: center;
  margin-bottom: 1rem;
`

export const OptionsList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1rem;
`

export const Option = styled.label`
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
  background: #f9f9f9;
  transition: 0.3s;

  &:hover {
    background: #e5e5e5;
  }
`

export const SubmitButton = styled.button`
  background: #4f46e5;
  color: white;
  padding: 0.75rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 100%;
  font-size: 1rem;

  &:hover {
    background: #3730a3;
  }
`
