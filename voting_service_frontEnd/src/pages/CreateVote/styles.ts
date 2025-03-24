import styled from 'styled-components'

export const CreateVoteContainer = styled.div`
  max-width: 600px;
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

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`

export const Input = styled.input`
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  width: 100%;
`

export const OptionsContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`

export const OptionInput = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
`

export const AddOptionButton = styled.button`
  background: #4caf50;
  color: white;
  padding: 0.5rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background: #388e3c;
  }
`

export const SubmitButton = styled.button`
  background: #4f46e5;
  color: white;
  padding: 0.8rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;

  &:hover {
    background: #3730a3;
  }
`
