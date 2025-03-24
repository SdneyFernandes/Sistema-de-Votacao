import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { voteApi } from '../../services/api'
import { getUserId } from '../../utils/auth'
import {
  VoteContainer,
  Title,
  OptionsList,
  Option,
  SubmitButton
} from './styles'

interface VoteSession {
  id: number
  title: string
  options: string[]
  startAt: string
  endAt: string
}

const VotePage = () => {
  const { id } = useParams()
  const navigate = useNavigate()
  const [voteSession, setVoteSession] = useState<VoteSession | null>(null)
  const [selectedOption, setSelectedOption] = useState('')

  useEffect(() => {
    const fetchVoteSession = async () => {
      try {
        const { data } = await voteApi.get(`/api/votes_session/${id}`)
        setVoteSession(data)
      } catch (error) {
        alert('Erro ao carregar votação!')
        navigate('/dashboard')
      }
    }
    fetchVoteSession()
  }, [id, navigate])

  const handleVote = async () => {
    if (!selectedOption) {
      alert('Selecione uma opção!')
      return
    }

    try {
      await voteApi.post(`/api/votes/${id}/cast`, {
        userId: getUserId(),
        option: selectedOption
      })
      alert('Voto registrado com sucesso!')
      navigate('/dashboard')
    } catch (error) {
      alert('Erro ao votar!')
    }
  }

  if (!voteSession) return <p>Carregando...</p>

  return (
    <VoteContainer>
      <Title>{voteSession.title}</Title>
      <OptionsList>
        {voteSession.options.map((option) => (
          <Option key={option}>
            <input
              type="radio"
              name="vote"
              value={option}
              onChange={() => setSelectedOption(option)}
            />
            {option}
          </Option>
        ))}
      </OptionsList>
      <SubmitButton onClick={handleVote}>Confirmar Voto</SubmitButton>
    </VoteContainer>
  )
}

export default VotePage
