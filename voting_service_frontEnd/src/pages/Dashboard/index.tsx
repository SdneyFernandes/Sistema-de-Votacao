import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { voteApi } from '../../services/api'
import { getUserRole } from '../../utils/auth'
import {
  DashboardContainer,
  Title,
  VoteList,
  VoteItem,
  Button,
  AdminButton
} from './styles'

interface VoteSession {
  id: number
  title: string
  startAt: string
  endAt: string
}

const Dashboard = () => {
  const navigate = useNavigate()
  const [votes, setVotes] = useState<VoteSession[]>([])

  useEffect(() => {
    const fetchVotes = async () => {
      try {
        const { data } = await voteApi.get('/api/votes_session')
        setVotes(data)
      } catch (error) {
        alert('Erro ao carregar votações!')
      }
    }
    fetchVotes()
  }, [])

  const isVoteOpen = (startAt: string, endAt: string) => {
    const now = new Date()
    return now >= new Date(startAt) && now <= new Date(endAt)
  }

  return (
    <DashboardContainer>
      <Title>Votações Disponíveis</Title>
      <VoteList>
        {votes.map((vote) => (
          <VoteItem key={vote.id}>
            <span>{vote.title}</span>
            {isVoteOpen(vote.startAt, vote.endAt) ? (
              <Button onClick={() => navigate(`/vote/${vote.id}`)}>
                Votar
              </Button>
            ) : (
              <Button onClick={() => navigate(`/results/${vote.id}`)}>
                Ver Resultados
              </Button>
            )}
          </VoteItem>
        ))}
      </VoteList>

      {getUserRole() === 'ADMIN' && (
        <AdminButton onClick={() => navigate('/create-vote')}>
          Criar Nova Votação
        </AdminButton>
      )}
    </DashboardContainer>
  )
}

export default Dashboard
