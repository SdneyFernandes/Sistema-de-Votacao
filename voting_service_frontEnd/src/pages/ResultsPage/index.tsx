import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { voteApi } from '../../services/api'
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer
} from 'recharts'
import {
  ResultsContainer,
  Title,
  ChartContainer,
  NoVotesMessage
} from './styles'

interface Result {
  option: string
  count: number
}

const ResultsPage = () => {
  const { id } = useParams()
  const navigate = useNavigate()
  const [results, setResults] = useState<Result[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const fetchResults = async () => {
      try {
        const { data } = await voteApi.get(`/api/votes_session/${id}/results`)
        const formattedData = Object.entries(data).map(([option, count]) => ({
          option,
          count: Number(count)
        }))
        setResults(formattedData)
      } catch (error) {
        alert('Erro ao carregar resultados!')
        navigate('/dashboard')
      } finally {
        setLoading(false)
      }
    }
    fetchResults()
  }, [id, navigate])

  if (loading) return <p>Carregando...</p>

  return (
    <ResultsContainer>
      <Title>Resultados da Votação</Title>
      {results.length > 0 ? (
        <ChartContainer>
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={results}>
              <XAxis dataKey="option" />
              <YAxis allowDecimals={false} />
              <Tooltip />
              <Bar dataKey="count" fill="#4F46E5" />
            </BarChart>
          </ResponsiveContainer>
        </ChartContainer>
      ) : (
        <NoVotesMessage>Nenhum voto registrado ainda.</NoVotesMessage>
      )}
    </ResultsContainer>
  )
}

export default ResultsPage
