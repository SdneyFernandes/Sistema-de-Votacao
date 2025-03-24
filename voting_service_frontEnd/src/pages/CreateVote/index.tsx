import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { voteApi } from '../../services/api'
import { getUserRole } from '../../utils/auth'
import {
  CreateVoteContainer,
  Title,
  Form,
  Input,
  OptionsContainer,
  OptionInput,
  AddOptionButton,
  SubmitButton
} from './styles'

const CreateVote = () => {
  const navigate = useNavigate()
  const [title, setTitle] = useState('')
  const [options, setOptions] = useState([''])
  const [startAt, setStartAt] = useState('')
  const [endAt, setEndAt] = useState('')

  if (getUserRole() !== 'ADMIN') {
    return <h2>Acesso Negado. Apenas Admin pode criar votações.</h2>
  }

  const addOption = () => setOptions([...options, ''])

  const removeOption = (index: number) => {
    setOptions(options.filter((_, i) => i !== index))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      await voteApi.post('/api/votes_session/create', {
        title,
        options,
        startAt,
        endAt
      })
      alert('Votação criada com sucesso!')
      navigate('/dashboard')
    } catch (error) {
      alert('Erro ao criar votação!')
      console.error(error)
    }
  }

  return (
    <CreateVoteContainer>
      <Title>Criar Nova Votação</Title>
      <Form onSubmit={handleSubmit}>
        <Input
          type="text"
          placeholder="Título da Votação"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />

        <OptionsContainer>
          <label>Opções de Voto:</label>
          {options.map((option, index) => (
            <OptionInput key={index}>
              <Input
                type="text"
                value={option}
                onChange={(e) =>
                  setOptions(
                    options.map((opt, i) =>
                      i === index ? e.target.value : opt
                    )
                  )
                }
              />
              <button type="button" onClick={() => removeOption(index)}>
                ❌
              </button>
            </OptionInput>
          ))}
          <AddOptionButton type="button" onClick={addOption}>
            ➕ Adicionar Opção
          </AddOptionButton>
        </OptionsContainer>

        <Input
          type="datetime-local"
          value={startAt}
          onChange={(e) => setStartAt(e.target.value)}
        />
        <Input
          type="datetime-local"
          value={endAt}
          onChange={(e) => setEndAt(e.target.value)}
        />

        <SubmitButton type="submit">Criar Votação</SubmitButton>
      </Form>
    </CreateVoteContainer>
  )
}

export default CreateVote
